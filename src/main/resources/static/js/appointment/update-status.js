$().ready(function() {
	modalEvents();
	buttonEvents();
	
	function modalEvents() {
		$("#updateStatus").on("show.bs.modal", function(ev) {		
			$("#hiddenIdUS").val($(ev.relatedTarget).data("id"));			
			$("#selectStatusUS").val($(ev.relatedTarget).data("status"));
			$("#selectSlotUS").val($(ev.relatedTarget).data("slot"));			
			
			if ($("#hiddenAuthorizeRole").val() === "ROLE_STAFF") {
				$("#selectStatusUS option[value='FULFILLED']").addClass("d-none");
				
				if ($(ev.relatedTarget).data("join-waitlist") === false) {
					$("#selectStatusUS option[value='WAITLIST']").addClass("d-none");
				}
			} else if ($("#hiddenAuthorizeRole").val() === "ROLE_DOCTOR") {
				$("#selectStatusUS option[value='PENDING']").remove();
				$("#selectStatusUS option[value='BOOKED']").remove();
				$("#selectStatusUS option[value='CANCELLED']").remove();
				$("#selectStatusUS option[value='WAITLIST']").remove();
				$("#divSlotUS").addClass("d-none");
			} 
		});
		
		$("#updateStatus").on("hidden.bs.modal", function(ev) {
			$("#divFeedbackUS").html("").removeClass("p-2");
			$("#hiddenIdUS").val("");
			$("#selectStatusUS").val("");
			$("#divSlotUS").removeClass("d-none");
			$("#selectSlotUS").val(0);
			$("#selectSlotUSFeedback").html("").removeAttr("style");
			$("#textNotesUS").val("").removeAttr("style");
			$("#textNotesUSFeedback").html("").removeAttr("style");
			$("#checkConfirmInformationUS").removeAttr("style");
			$("#checkConfirmInformationUSFeedback").html("").removeAttr("style");
			$("#buttonCancelUS").prop("disabled", false);
			$("#buttonSaveUS").prop("disabled", false);

			$("#selectStatusUS option").each(function() {
				$(this).removeClass("d-none");
			});
		});
	}
	
	function buttonEvents() {
		$("#buttonSaveUS").click(function(ev) {
			$("#buttonCancelUS").prop("disabled", true);
			$("#buttonSaveUS").prop("disabled", true);
					
			const appointmentId = $("#hiddenIdUS").val();
			const result = fieldsValidation();
 
			if (typeof(result) === 'object') {
				$.ajax({
				  	url: `/appointment/update/status/${appointmentId}`,
				  	method: "POST",
				  	headers: {
						"Content-Type": "application/json",
						"X-CSRF-TOKEN": $("input[name=_csrf]").val()
					},
				  	data: JSON.stringify(result)
				}).done(function(data) {
					$("#divFeedbackUS").html(`<div class="p-3 text-primary-emphasis border border-success-subtle bg-success-subtle">Appointment has been updated successfully.</div>`).addClass("p-2");
					
					setTimeout(function() {
						// The calendar object defined in /js/full-calendar.js will execute the same event source
						// to update the data loaded in calendar
						window.appointmentCalendar.refetchEvents();
						
						$("#buttonCancelUS").prop("disabled", false);
						$("#buttonSaveUS").prop("disabled", false);
						$("#buttonCancelUS").click();
					}, 3000);
					
				}).fail(function(err) {
					$("#buttonCancelUS").prop("disabled", false);
					$("#buttonSaveUS").prop("disabled", false);
					
					$("#divFeedbackUS").html(`<div class="p-3 text-primary-emphasis border border-danger-subtle bg-danger-subtle">An error has occured, plase try again later.</div>`).addClass("p-2");
					
					setTimeout(function() {
						$("#divFeedbackUS").html("").removeClass("p-2");
					}, 5000);
				});
			} else {
				$("#buttonCancelUS").prop("disabled", false);
				$("#buttonSaveUS").prop("disabled", false);
			}
		});
	}
	
	/* Returns Object, otherwise false on errors */
	function fieldsValidation() {
		let hasError = false;
		const fields = { status: $("#selectStatusUS").find(":selected").val() };
		
		const status = $("#selectStatusUS");
		const slot = $('#selectSlotUS');
		const notes = $('#textNotesUS');
		const confirmInformation = $('#checkConfirmInformationUS');
		
		let feedback = $("#selectSlotUSFeedback");
		if (status.find(":selected").val() != "PENDING" && parseInt(slot.find(":selected").val()) === 0) {
			feedback.html("Please select slot no.").attr("style", "display: block");
			slot.attr("style", "border-color: #dc3545 !important");
			hasError = true;
		} else if (status.find(":selected").val() === "PENDING" && parseInt(slot.find(":selected").val()) > 0) {
			feedback.html("Please select slot no. 0").attr("style", "display: block");
			slot.attr("style", "border-color: #dc3545 !important");
			hasError = true;
		} else {
			feedback.html("").removeAttr("style");
			slot.removeAttr("style");
			fields['slot'] = slot.find(":selected").val();
		}
		
		feedback = $("#textNotesUSFeedback");
		if (notes.val().trim().length === 0 ) {
			feedback.html("Please enter your notes").attr("style", "display: block");
			notes.attr("style", "border-color: #dc3545 !important");
			hasError = true;
		} else {
			feedback.html("").removeAttr("style");
			notes.removeAttr("style");
			fields['notes'] = notes.val().trim();
		}
	
		feedback = $("#checkConfirmInformationUSFeedback");
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