$().ready(function() {
	fieldsEvents();
	buttonEvents();
	
	function fieldsEvents() {
		// Department
		$("#selectAppointmentDepartment").change(function(ev) {
			$.ajax({
			  	url: "/appointment/fetchDoctorsByDepartment",
			  	method: "POST",
			  	data: {
					_csrf : $("input[name=_csrf]").val(),
					doctorDepartment : ev.target.value.toUpperCase().replace(" ", "_")
				}
			}).done(function(data) {
				$("#selectAppointmentDoctor").html("<option selected disabled value=''>...</option>").val("");
			
			  	for (row of data) {
					$("#selectAppointmentDoctor").append($("<option></option>").attr("value", row.id).text(`${row.firstName} ${row.lastName}`));
				}
			});
		});
	}
	
	function buttonEvents() {
		$("#buttonLoadAppointments").click(function(ev) {
			$("#buttonLoadAppointments").prop("disabled", true);

			const result = fieldsValidation();
 
			if (typeof(result) === 'object') {
				window.appointmentCalendar.destroy();
				window.initAppointmentCalendar(result.doctorId);
			}
			
			$("#buttonLoadAppointments").prop("disabled", false);
		});
	}
	
	/* Returns Object, otherwise false on errors */
	function fieldsValidation() {
		let hasError = false;
		const fields = {};
		
		const department = $("#selectAppointmentDepartment");
		const doctor = $("#selectAppointmentDoctor");
		
		let feedback = $("#selectAppointmentDepartmentFeedback");
		if (department.find(":selected").val() === "") {
			feedback.html("Please select department.").attr("style", "display: block");
			department.attr("style", "border-color: #dc3545 !important");
			hasError = true;
		} else {
			feedback.html("").removeAttr("style");
			department.removeAttr("style");
		}
		
		feedback = $("#selectAppointmentDoctorFeedback");
		if (doctor.find(":selected").val() === "") {
			feedback.html("Please select doctor.").attr("style", "display: block");
			doctor.attr("style", "border-color: #dc3545 !important");
			hasError = true;
		} else {
			feedback.html("").removeAttr("style");
			doctor.removeAttr("style");
			fields['doctorId'] = doctor.find(":selected").val();
		}
		
		 
		if (hasError) {
			return hasError;
		} else {
			return fields;	
		}
	}
});