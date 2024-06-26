$().ready(function() {
	modalEvents();
	buttonEvents();
	
	function modalEvents() {
		$("#cancelAppointment").on("show.bs.modal", function(ev) {
			const btoaData = $(ev.relatedTarget).data("btoa-data");
			const row = JSON.parse(window.atob(btoaData));
			
			const id = row.id;
			const title = row.description;
			const clinic = `${row.location.name} - ${row.location.address}`;
			const rawDepartment = row.doctor.department;
			const doctor = `Dr. ${row.doctor.firstName} ${row.doctor.lastName}`;
			const rawDatetime = new Date(row.datetime);
			const reason = row.reason;
			const joinWaitlist = row.joinWaitlist ? "Yes, I would like to join the waitlist." : "No, I do not wish to join the waitlist";
			
			const departmentParts = rawDepartment.toLowerCase().split("_");
			let department = "";
			if (departmentParts.length === 1) {
				department = departmentParts[0].charAt(0).toUpperCase() + departmentParts[0].slice(1);
			} else if (departmentParts.length === 2) {
				department = `${departmentParts[0].charAt(0).toUpperCase() + departmentParts[0].slice(1)} ${departmentParts[1].charAt(0).toUpperCase() + departmentParts[1].slice(1)}`;
			}
			
			const datetime = `${rawDatetime.toLocaleString().replace(",", "").replace(/(\:\d{2} AM)/, " AM").replace(/(\:\d{2} PM)/, " PM")}`;
			
			$("#hiddenIdCA").val(id);			
			$("#textTitleCA").val(title);
			$("#textClinicCA").val(clinic);
			$("#textDepartmentCA").val(department);
			$("#textDoctorCA").val(doctor);
			$("#textDatetimeCA").val(datetime);
			$("#textReasonCA").val(reason);
			$("#textJoinWaitlistCA").val(joinWaitlist);
		});
		
		$("#cancelAppointment").on("hidden.bs.modal", function(ev) {
			$("#divFeedbackCA").html("").removeClass("p-2");
			$("#hiddenIdCA").val("");			
			$("#textTitleCA").val("");
			$("#textClinicCA").val("");
			$("#textDepartmentCA").val("");
			$("#textDoctorCA").val("");
			$("#textDatetimeCA").val("");
			$("#textReasonCA").val("");
			$("#textJoinWaitlistCA").val("");
			$("#textNotesCA").val("");
			$('#checkConfirmInformationCA').prop("checked", false);
			$("#buttonCancelCA").prop("disabled", false);
			$("#buttonSaveCA").prop("disabled", false);
		});
	}
	
	function buttonEvents() {
		$("#buttonSaveCA").click(function(ev) {
			$("#buttonCancelCA").prop("disabled", true);
			$("#buttonSaveCA").prop("disabled", true);
					
			const appointmentId = $("#hiddenIdCA").val();
			const result = fieldsValidation();
 
			if (typeof(result) === 'object') {
				$.ajax({
				  	url: `/book-appointment/cancel/appointment/${appointmentId}`,
				  	method: "POST",
				  	headers: {
						"Content-Type": "application/json",
						"X-CSRF-TOKEN": $("input[name=_csrf]").val()
					},
				  	data: JSON.stringify(result)
				}).done(function(data) {
					$("#divFeedbackCA").html(`<div class="p-3 text-primary-emphasis border border-success-subtle bg-success-subtle">Your appointment has been cancelled successfully.</div>`).addClass("p-2");
					
					setTimeout(function() {
						// We destroy first then re-initialize the data table
						// from /js/book-appointment/data-table.js file
						window.tableAppointmentList.destroy();
						window.initDataTableAppointmentList();
						
						$("#buttonCancelCA").prop("disabled", false);
						$("#buttonSaveCA").prop("disabled", false);
						$("#buttonCancelCA").click();
					}, 3000);
					
				}).fail(function(err) {
					$("#buttonCancelCA").prop("disabled", false);
					$("#buttonSaveCA").prop("disabled", false);
					
					$("#divFeedbackCA").html(`<div class="p-3 text-primary-emphasis border border-danger-subtle bg-danger-subtle">An error has occured, plase try again later.</div>`).addClass("p-2");
					
					setTimeout(function() {
						$("#divFeedbackCA").html("").removeClass("p-2");
					}, 5000);
				})
			} else {
				$("#buttonCancelCA").prop("disabled", false);
				$("#buttonSaveCA").prop("disabled", false);
			}
		});
	}
	
	/* Returns Object, otherwise false on errors */
	function fieldsValidation() {
		let hasError = false;
		const fields = {};
		
		const notes = $('#textNotesCA');
		const confirmInformation = $('#checkConfirmInformationCA');
		
		let feedback = $("#textNotesCAFeedback");
		if (notes.val().trim().length === 0 ) {
			feedback.html("Please enter your notes").attr("style", "display: block");
			notes.attr("style", "border-color: #dc3545 !important");
			hasError = true;
		} else {
			feedback.html("").removeAttr("style");
			notes.removeAttr("style");
			fields['notes'] = notes.val().trim();
		}
	
		feedback = $("#checkConfirmInformationCAFeedback");
		if (confirmInformation.is(":checked") === false ) {
			feedback.html("Please confirm information you provided").attr("style", "display: block");
			confirmInformation.attr("style", "border-color: #dc3545 !important");
			hasError = true;
		} else {
			feedback.html("").removeAttr("style");
			confirmInformation.removeAttr("style");
		}
		
		if (hasError) {
			return hasError;
		} else {
			return fields;	
		}
	}
});