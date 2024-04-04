$().ready(function() {	
	initDateTimePicker();
	initToolTip();
	modalEvents();
	fieldsEvents();
	buttonEvents();
	
	function initDateTimePicker() {
		window.datePicker = new tempusDominus.TempusDominus(document.getElementById("datetimepicker1"), {
			allowInputToggle: true,
			display: { components: { clock: false } },
			localization: { format: 'yyyy-MM-dd' },
  			useCurrent: false
		});
	}
	
	function initToolTip() {
		const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]')
		const tooltipList = [...tooltipTriggerList].map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl))	
	}
	
	function modalEvents() {
		$("#createAppointment").on("hide.bs.modal", function(ev) {
			$("#divFeedback").html("").removeClass("p-2");
			$("#textTitle").val("");
			$("#selectClinic").val("");
			$("#selectDepartment").val("").prop("disabled", true);
			$("#selectDoctor").val("").prop("disabled", true);
			$("#textDate").val("").prop("disabled", true);
			$("#selectTime").val("").prop("disabled", true);
			$("#textReason").val("");
			$('#checkJoinWaitlist').prop("checked", false);
			$('#checkConfirmInformation').prop("checked", false);
			$("#buttonCancel").prop("disabled", false);
			$("#buttonSave").prop("disabled", false);
		});
	}
	
	function fieldsEvents() {
		// Clinic	
		$("#selectClinic").change(function(ev) {			
			$("#selectDepartment").val("").prop("disabled", false);
			$("#selectDoctor").val("").prop("disabled", true);
			$("#textDate").val("").prop("disabled", true);
			$("#selectTime").val("").prop("disabled", true);
		});
		
		// Department
		$("#selectDepartment").change(function(ev) {
			$.ajax({
			  	url: "/book-appointment/fetchDoctorsByDepartmentAndLocation",
			  	method: "POST",
			  	data: {
					_csrf : $("input[name=_csrf]").val(),
					doctorDepartment : ev.target.value.toUpperCase().replace(" ", "_"),
					locationId: $('#selectClinic').find(":selected").val()
				}
			}).done(function(data) {
				$("#selectDoctor").html("<option selected disabled value=''>...</option>").val("").prop("disabled", false);
				$("#textDate").val("").prop("disabled", true);
				$("#selectTime").val("").prop("disabled", true);
			
			  	for (row of data) {
					$("#selectDoctor").append($("<option></option>").attr("value", row.id).text(`${row.firstName} ${row.lastName}`));
				}
			});
		});
		
		// Doctor		
		$("#selectDoctor").change(function(ev) {
			$.ajax({
			  	url: "/book-appointment/fetchDoctorSchedulesByDoctorAndLocation",
			  	method: "POST",
			  	data: {
					_csrf : $("input[name=_csrf]").val(),
					doctorId : ev.target.value,
					locationId: $('#selectClinic').find(":selected").val()
				}
			}).done(function(data) {
				const dow = { "SUN": 0, "MON": 1, "TUE": 2, "WED": 3, "THU": 4, "FRI": 5, "SAT": 6 };
				const daysOfWeekDisabled = [0, 1, 2, 3, 4, 5, 6];
				
				for (row of data) {
					// Remove in daysOfWeekDisabled if in schedule
					daysOfWeekDisabled.splice(daysOfWeekDisabled.indexOf(dow[row.dayOfWeek]), 1);
					// We'll use this later to populate in selectTime element
					startEndTimes[row.dayOfWeek] = { start: row.startTime, end: row.endTime};
				}
				
				window.datePicker.updateOptions({ restrictions: { minDate: new Date(), daysOfWeekDisabled } });
				
				$("#textDate").val("").prop("disabled", false);
			});
		});
		
		// Date
		let startEndTimes = {};
		window.datePicker.subscribe(tempusDominus.Namespace.events.change, (ev) => {
			const dow = { 0: "SUN", 1 : "MON", 2 : "TUE", 3: "WED", 4: "THU", 5: "FRI", 6: "SAT" };
			
		    const start = startEndTimes[dow[ev.date.getDay()]].start;
		    const end = startEndTimes[dow[ev.date.getDay()]].end;
		    
		    $("#selectTime").html("<option selected disabled value=''>...</option>").val("").prop("disabled", false);
		    
		    for (i = parseInt(start.split(":")[0]); i <= parseInt(end.split(":")[0]); i++) {
				const hour = i.toLocaleString('en-US', {minimumIntegerDigits: 2, useGrouping:false});
				if (i === parseInt(start.split(":")[0]) && 30 === parseInt(start.split(":")[1])) {
					$("#selectTime").append($("<option></option>").attr("value", `${hour}:30 ${i < 12 ? 'AM' : 'PM'}`).text(`${hour}:30 ${i < 12 ? 'AM' : 'PM'}`));
				} else {
					if (i === parseInt(end.split(":")[0]) && 30 === parseInt(end.split(":")[1])) {
						$("#selectTime").append($("<option></option>").attr("value", `${hour}:30 ${i < 12 ? 'AM' : 'PM'}`).text(`${hour}:00 ${i < 12 ? 'AM' : 'PM'}`));
					} else if (i < parseInt(end.split(":")[0])) {							
						$("#selectTime").append($("<option></option>").attr("value", `${hour}:30 ${i < 12 ? 'AM' : 'PM'}`).text(`${hour}:00 ${i < 12 ? 'AM' : 'PM'}`));
						$("#selectTime").append($("<option></option>").attr("value", `${hour}:30 ${i < 12 ? 'AM' : 'PM'}`).text(`${hour}:30 ${i < 12 ? 'AM' : 'PM'}`));
					}
				}
			}
		});
	}
	
	function buttonEvents() {
		$("#buttonSave").click(function(ev) {
			$("#buttonCancel").prop("disabled", true);
			$("#buttonSave").prop("disabled", true);
					
			const result = fieldsValidation();
 
			if (typeof(result) === 'object') {
				$.ajax({
				  	url: `/book-appointment/create/appointment/${result.doctorId}/${result.locationId}`,
				  	method: "POST",
				  	headers: {
						"Content-Type": "application/json",
						"X-CSRF-TOKEN": $("input[name=_csrf]").val()
					},
				  	data: JSON.stringify(result)
				}).done(function(data) {
					$("#divFeedback").html(`<div class="p-3 text-primary-emphasis border border-success-subtle bg-success-subtle">Your appointment has been created successfully.</div>`).addClass("p-2");
					
					setTimeout(function() {
						// We destroy first then re-initialize the data table
						// from /js/data-table.js file
						window.tableAppointmentList.destroy();
						window.initDataTableAppointmentList();
						
						$("#buttonCancel").prop("disabled", false);
						$("#buttonSave").prop("disabled", false);
						$("#buttonCancel").click();
					}, 3000);
					
				}).fail(function(err) {
					$("#buttonCancel").prop("disabled", false);
					$("#buttonSave").prop("disabled", false);
					
					$("#divFeedback").html(`<div class="p-3 text-primary-emphasis border border-danger-subtle bg-danger-subtle">An error has occured, plase try again later.</div>`).addClass("p-2");
					
					setTimeout(function() {
						$("#divFeedback").html("").removeClass("p-2");
					}, 5000);
				})
			} else {
				$("#buttonCancel").prop("disabled", false);
				$("#buttonSave").prop("disabled", false);
			}
		});
	}
	
	/* Returns Object, otherwise false on errors */
	function fieldsValidation() {
		let hasError = false;
		const fields = { joinWaitlist: $('#checkJoinWaitlist').is(":checked") };
		
		const title = $("#textTitle");
		const clinic = $('#selectClinic');
		const department = $('#selectDepartment');
		const doctor = $('#selectDoctor');
		const date = $('#textDate');
		const time = $('#selectTime');
		const reason = $('#textReason');
		const confirmInformation = $('#checkConfirmInformation');
		
		let feedback = $("#textTitleFeedback");
		if (title.val().trim().length === 0) {
			feedback.html("Please enter a title").attr("style", "display: block");
			title.attr("style", "border-color: #dc3545 !important");
			hasError = true;
		} else {
			feedback.html("").removeAttr("style");
			title.removeAttr("style");
			fields['description'] = title.val().trim();
		}
		
		feedback = $("#selectClinicFeedback");
		if (clinic.find(":selected").val() === "") {
			feedback.html("Please choose a clinic").attr("style", "display: block");
			clinic.attr("style", "border-color: #dc3545 !important");
			hasError = true;
		} else {
			feedback.html("").removeAttr("style");
			clinic.removeAttr("style");
			fields['locationId'] = clinic.find(":selected").val();
		}
		
		if (department.prop('disabled') === false) {
			feedback = $("#selectDepartmentFeedback");
			if (department.find(":selected").val() === "") {
				feedback.html("Please choose a department").attr("style", "display: block");
				department.attr("style", "border-color: #dc3545 !important");
				hasError = true;
			} else {
				feedback.html("").removeAttr("style");
				department.removeAttr("style");
			} 
		}
		
		if (doctor.prop('disabled') === false) {
			feedback = $("#selectDoctorFeedback");
			if (doctor.find(":selected").val() === "") {
				feedback.html("Please choose a doctor").attr("style", "display: block");
				doctor.attr("style", "border-color: #dc3545 !important");
				hasError = true;
			} else {
				feedback.html("").removeAttr("style");
				doctor.removeAttr("style");
				fields['doctorId'] = doctor.find(":selected").val();
			} 
		}
		
		if (date.prop('disabled') === false) {
			feedback = $("#textDateFeedback");
			if (date.val().trim().length === 0) {
				feedback.html("Please choose a date").attr("style", "display: block");
				date.attr("style", "border-color: #dc3545 !important");
				hasError = true;
			} else {
				feedback.html("").removeAttr("style");
				date.removeAttr("style");
			}
		}
		
		if (time.prop('disabled') === false) {
			feedback = $("#selectTimeFeedback");
			if (time.find(":selected").val() === "") {
				feedback.html("Please choose a time").attr("style", "display: block");
				time.attr("style", "border-color: #dc3545 !important");
				hasError = true;
			} else {
				feedback.html("").removeAttr("style");
				time.removeAttr("style");
			} 
		}
		
		if (date.val().trim().length != 0 && time.find(":selected").val() != "") {
			const datetime = new Date(`${date.val().trim()} ${time.find(":selected").val()}`);
			fields['datetime'] = datetime.toISOString();
		}
		
		feedback = $("#textReasonFeedback");
		if (reason.val().trim().length === 0 ) {
			feedback.html("Please enter your reason").attr("style", "display: block");
			reason.attr("style", "border-color: #dc3545 !important");
			hasError = true;
		} else {
			feedback.html("").removeAttr("style");
			reason.removeAttr("style");
			fields['reason'] = reason.val().trim();
		}
	
		feedback = $("#checkConfirmInformationFeedback");
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
