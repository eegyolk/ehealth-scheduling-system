$().ready(function() {
	buttonEvents();
	
	function buttonEvents() {
		$("#buttonLoadAppointments").click(function(ev) {
			$("#buttonLoadAppointments").prop("disabled", true);

			const result = fieldsValidation();
 
			if (typeof(result) === 'object') {
				window.appointmentCalendar.destroy();
				window.initAppointmentCalendar(result.locationId);
			}
			
			$("#buttonLoadAppointments").prop("disabled", false);
		});
	}
	
	/* Returns Object, otherwise false on errors */
	function fieldsValidation() {
		let hasError = false;
		const fields = {};
		
		const clinic = $("#selectAppointmentClinic");
		
		let feedback = $("#selectAppointmentClinicFeedback");
		if (clinic.find(":selected").val() === "") {
			feedback.html("Please select clinic.").attr("style", "display: block");
			clinic.attr("style", "border-color: #dc3545 !important");
			hasError = true;
		} else {
			feedback.html("").removeAttr("style");
			clinic.removeAttr("style");
			fields['locationId'] = clinic.find(":selected").val();
		}
		
		 
		if (hasError) {
			return hasError;
		} else {
			return fields;	
		}
	}
});