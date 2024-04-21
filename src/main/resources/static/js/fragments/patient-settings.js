$().ready(function() {
	let currentFirstName;
	let currentLastName;
	let currentEmail;
	let currentPhone;
	let currentGender;
	let currentBirthDate;
	let currentAddress;
	let doctorSchedules = [];
	
	modalEvents();
	initToolTip();
	fieldsEvents();
	buttonEvents();
	
	function modalEvents() {
		$("#patientSettings").on("shown.bs.modal", function(ev) {
			if (parseInt($("#hiddenSettingsPreferredLocation").val()) > 0) {
				$("#selectSettingsClinic").val($("#hiddenSettingsPreferredLocation").val()).prop("disabled", false).change();
				
				if ($("#hiddenSettingsDepartment").val() !== "") {
					$("#selectSettingsDepartment").val($("#hiddenSettingsDepartment").val()).prop("disabled", false).change();
					
					if (parseInt($("#hiddenSettingsPreferredDoctor").val()) > 0) {
						$("#selectSettingsDoctor").val($("#hiddenSettingsPreferredDoctor").val()).prop("disabled", false).change();
						
						if ($("#hiddenSettingsPreferredDOW").val() !== "") {
							$("#selectSettingsDOW").val($("#hiddenSettingsPreferredDOW").val()).prop("disabled", false);
						
							if ($("#hiddenSettingsPreferredTime").val() !== "") {	
								$("#selectSettingsTime").val($("#hiddenSettingsPreferredTime").val()).prop("disabled", false);
							}
							
							$("#selectSettingsDOW").change();
						}
					}
				}
			}
			
			/*currentFirstName = $("#textFirstName").val();
			currentLastName = $("#textLastName").val();
			currentEmail = $("#textEmail").val();
			currentPhone = $("#textPhone").val();
			currentGender = $("#selectGender").find(":selected").val();
			currentBirthDate = $("#textBirthDate").val();
			currentAddress = $("#textAddress").val();*/
		});
		
		$("#patientSettings").on("hidden.bs.modal", function(ev) {
			/*$("#divProfileFeedback").html("").removeClass("p-2");
			$("#textFirstName").val(currentFirstName).removeAttr("style");
			$("#textFirstNameFeedback").html("").removeAttr("style");
			$("#textLastName").val(currentLastName).removeAttr("style");
			$("#textLastNameFeedback").html("").removeAttr("style");
			$("#textEmail").val(currentEmail).removeAttr("style");
			$("#textEmailFeedback").html("").removeAttr("style");
			$("#textPhone").val(currentPhone).removeAttr("style");
			$("#textPhoneFeedback").html("").removeAttr("style");
			$("#selectGender").val(currentGender).removeAttr("style");
			$("#selectGenderFeedback").html("").removeAttr("style");
			$("#textBirthDate").val(currentBirthDate).removeAttr("style");
			$("#textBirthDateFeedback").html("").removeAttr("style");
			$("#textAddress").val(currentAddress).removeAttr("style");
			$("#textAddressFeedback").html("").removeAttr("style");*/
			$("#buttonCancelProfile").prop("disabled", false);
			$("#buttonSaveProfile").prop("disabled", false);
		});
	}
	
	function initToolTip() {
		const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]')
		const tooltipList = [...tooltipTriggerList].map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl))	
	}
	
	function fieldsEvents() {
		// Clinic	
		$("#selectSettingsClinic").change(function(ev) {			
			//$("#selectSettingsDepartment").val("").prop("disabled", false);
			//$("#selectSettingsDoctor").val("").prop("disabled", true);
			//$("#selectSettingsDOW").val("").prop("disabled", true);
			//$("#selectSettingsTime").val("").prop("disabled", true);
		});
		
		// Department
		$("#selectSettingsDepartment").change(function(ev) {
			$.ajax({
			  	url: "/book-appointment/fetchDoctorsByDepartmentAndLocation",
			  	method: "POST",
			  	data: {
					_csrf : $("input[name=_csrf]").val(),
					doctorDepartment : $("#hiddenSettingsDepartment").val() !== "" ? $("#hiddenSettingsDepartment").val() : ev.target.value.toUpperCase().replace(" ", "_"),
					locationId: parseInt($("#hiddenSettingsPreferredLocation").val()) > 0 ? $("#hiddenSettingsPreferredLocation").val() : $('#selectSettingsClinic').find(":selected").val()
				}
			}).done(function(data) {
				//$("#selectSettingsDoctor").html("<option selected disabled value=''>...</option>").val("").prop("disabled", false);
				//$("#selectSettingsDOW").val("").prop("disabled", true);
				//$("#selectSettingsTime").val("").prop("disabled", true);
			
			  	for (row of data) {
					$("#selectSettingsDoctor").append($("<option></option>").attr("value", row.id).text(`${row.firstName} ${row.lastName}`));
				}
			});
		});
		
		// Doctor		
		$("#selectSettingsDoctor").change(function(ev) {
			$.ajax({
			  	url: "/book-appointment/fetchDoctorSchedulesByDoctorAndLocation",
			  	method: "POST",
			  	data: {
					_csrf : $("input[name=_csrf]").val(),
					doctorId : parseInt($("#hiddenSettingsPreferredDoctor").val()) > 0 ? $("#hiddenSettingsPreferredDoctor").val() : ev.target.value,
					locationId: parseInt($("#hiddenSettingsPreferredLocation").val()) > 0 ? $("#hiddenSettingsPreferredLocation").val() : $('#selectSettingsClinic').find(":selected").val()
				}
			}).done(function(data) {
				//$("#selectSettingsDOW").html("<option selected disabled value=''>...</option>").val("").prop("disabled", false);
				//$("#selectSettingsTime").val("").prop("disabled", true);
			
			  	for (row of data) {
					doctorSchedules.push(row);
					$("#selectSettingsDOW").append($("<option></option>").attr("value", row.dayOfWeek).text(`${row.dayOfWeek}`));
				}
				
				console.log(doctorSchedules);
			});
		});
		
		// Day of Week
		$("#selectSettingsDOW").change(function(ev) {
			const dayOfWeek = $("#hiddenSettingsPreferredDOW").val() !== "" ? $("#hiddenSettingsPreferredDOW").val(): $('#selectSettingsDOW').find(":selected").val();
			const dowSched = doctorSchedules.find(e => e.dayOfWeek === dayOfWeek);
			
			const start = dowSched.startTime;
		    const end = dowSched.endTime;
		    
		    //$("#selectSettingsTime").html("<option selected disabled value=''>...</option>").val("").prop("disabled", false);
		    
		    for (i = parseInt(start.split(":")[0]); i <= parseInt(end.split(":")[0]); i++) {
				const hour = i.toLocaleString('en-US', {minimumIntegerDigits: 2, useGrouping:false});
				if (i === parseInt(start.split(":")[0]) && 30 === parseInt(start.split(":")[1])) {
					$("#selectSettingsTime").append($("<option></option>").attr("value", `${hour}:30 ${i < 12 ? 'AM' : 'PM'}`).text(`${hour}:30 ${i < 12 ? 'AM' : 'PM'}`));
				} else {
					if (i === parseInt(end.split(":")[0]) && 30 === parseInt(end.split(":")[1])) {
						$("#selectSettingsTime").append($("<option></option>").attr("value", `${hour}:00 ${i < 12 ? 'AM' : 'PM'}`).text(`${hour}:00 ${i < 12 ? 'AM' : 'PM'}`));
					} else if (i < parseInt(end.split(":")[0])) {							
						$("#selectSettingsTime").append($("<option></option>").attr("value", `${hour}:00 ${i < 12 ? 'AM' : 'PM'}`).text(`${hour}:00 ${i < 12 ? 'AM' : 'PM'}`));
						$("#selectSettingsTime").append($("<option></option>").attr("value", `${hour}:30 ${i < 12 ? 'AM' : 'PM'}`).text(`${hour}:30 ${i < 12 ? 'AM' : 'PM'}`));
					}
				}
			}
		});
	}
	
	function buttonEvents() {
		$("#buttonSaveSettings").click(function(ev) {
			$("#buttonCancelSettings").prop("disabled", true);
			$("#buttonSaveSettings").prop("disabled", true);
					
			const result = fieldsValidation();
 
			if (typeof(result) === 'object') {
				$.ajax({
				  	url: `/user/update/patient-setting`,
				  	method: "POST",
				  	headers: {
						"Content-Type": "application/json",
						"X-CSRF-TOKEN": $("input[name=_csrf]").val()
					},
				  	data: JSON.stringify(result)
				}).done(function(data) {
					$("#divSettingsFeedback").html(`<div class="p-3 text-primary-emphasis border border-success-subtle bg-success-subtle">Your settings has been updated successfully.</div>`).addClass("p-2");
					
					 
			
					setTimeout(function() {						
						$("#buttonCancelSettings").prop("disabled", false);
						$("#buttonSaveSettings").prop("disabled", false);
						$("#buttonCancelSettings").click();
					}, 3000);
					
				}).fail(function(err) {
					$("#buttonCancelSettings").prop("disabled", false);
					$("#buttonSaveSettings").prop("disabled", false);
					
					$("#divSettingsFeedback").html(`<div class="p-3 text-primary-emphasis border border-danger-subtle bg-danger-subtle">An error has occured, please try again.</div>`).addClass("p-2");
					
					setTimeout(function() {
						$("#divSettingsFeedback").html("").removeClass("p-2");
					}, 5000);
				});
			} else {
				$("#buttonCancelSettings").prop("disabled", false);
				$("#buttonSaveSettings").prop("disabled", false);
			}
		});
	}
	
	/* Returns Object, otherwise false on errors */
	function fieldsValidation() {
		let hasError = false;
		const fields = { preferredLocation: {}, preferredDoctor: {} };
		
		const clinic = $('#selectSettingsClinic');
		const department = $('#selectSettingsDepartment');
		const doctor = $('#selectSettingsDoctor');
		const dow = $('#selectSettingsDOW');
		const time = $('#selectSettingsTime');
		
		
		let feedback = $("#selectSettingsClinicFeedback");
		if (clinic.find(":selected").val() === "") {
			feedback.html("Please choose a preferred clinic").attr("style", "display: block");
			clinic.attr("style", "border-color: #dc3545 !important");
			hasError = true;
		} else {
			feedback.html("").removeAttr("style");
			clinic.removeAttr("style");
			fields['preferredLocation']['id'] = parseInt(clinic.find(":selected").val());
		}
		
		if (department.prop('disabled') === false) {
			feedback = $("#selectSettingsDepartmentFeedback");
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
			feedback = $("#selectSettingsDoctorFeedback");
			if (doctor.find(":selected").val() === "") {
				feedback.html("Please choose a preferred doctor").attr("style", "display: block");
				doctor.attr("style", "border-color: #dc3545 !important");
				hasError = true;
			} else {
				feedback.html("").removeAttr("style");
				doctor.removeAttr("style");
				fields['preferredDoctor']['id'] = parseInt(doctor.find(":selected").val());
			} 
		}
		
		if (dow.prop('disabled') === false) {
			feedback = $("#selectSettingsDOWFeedback");
			if (time.find(":selected").val() === "") {
				feedback.html("Please choose a preferred day of week.").attr("style", "display: block");
				dow.attr("style", "border-color: #dc3545 !important");
				hasError = true;
			} else {
				feedback.html("").removeAttr("style");
				dow.removeAttr("style");
				fields['preferredDayOfWeek'] = dow.find(":selected").val();
			} 
		}
		
		if (time.prop('disabled') === false) {
			feedback = $("#selectSettingsTimeFeedback");
			if (time.find(":selected").val() === "") {
				feedback.html("Please choose a preferred time").attr("style", "display: block");
				time.attr("style", "border-color: #dc3545 !important");
				hasError = true;
			} else {
				feedback.html("").removeAttr("style");
				time.removeAttr("style");
				fields['preferredTime'] = time.find(":selected").val();
			} 
		}
		
		if (hasError) {
			return hasError;
		} else {
			return fields;	
		}
	}
});