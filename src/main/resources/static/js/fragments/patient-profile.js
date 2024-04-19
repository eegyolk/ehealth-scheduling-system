$().ready(function() {
	modalEvents();
	initDateTimePicker();
	buttonEvents();
	
	function modalEvents() {
		let currentFirstName;
		let currentLastName;
		let currentEmail;
		let currentPhone;
		let currentGender;
		let currentBirthDate;
		let currentAddress;
			
		$("#patientProfile").on("shown.bs.modal", function(ev) {
			currentFirstName = $("#textFirstName").val();
			currentLastName = $("#textLastName").val();
			currentEmail = $("#textEmail").val();
			currentPhone = $("#textPhone").val();
			currentGender = $("#selectGender").find(":selected").val();
			currentBirthDate = $("#textBirthDate").val();
			currentAddress = $("#textAddress").val();
		});
		
		$("#patientProfile").on("hidden.bs.modal", function(ev) {
			console.log(currentFirstName);
			$("#divProfileFeedback").html("").removeClass("p-2");
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
			$("#textAddressFeedback").html("").removeAttr("style");
			$("#buttonCancelProfile").prop("disabled", false);
			$("#buttonSaveProfile").prop("disabled", false);
		});
	}
	
	function initDateTimePicker() {
		window.datePicker = new tempusDominus.TempusDominus(document.getElementById("datetimepicker1"), {
			allowInputToggle: true,
			display: { components: { clock: false } },
			localization: { format: 'yyyy-MM-dd' },
  			useCurrent: false
		});
	}
	
	function buttonEvents() {
		$("#buttonSaveProfile").click(function(ev) {
			$("#buttonCancelProfile").prop("disabled", true);
			$("#buttonSaveProfile").prop("disabled", true);
					
			const result = fieldsValidation();
 
			if (typeof(result) === 'object') {
				console.lo
				$.ajax({
				  	url: `/user/update/patient`,
				  	method: "POST",
				  	headers: {
						"Content-Type": "application/json",
						"X-CSRF-TOKEN": $("input[name=_csrf]").val()
					},
				  	data: JSON.stringify(result)
				}).done(function(data) {
					$("#divProfileFeedback").html(`<div class="p-3 text-primary-emphasis border border-success-subtle bg-success-subtle">Your profile has been updated successfully.</div>`).addClass("p-2");
					
					setTimeout(function() {						
						$("#buttonCancelProfile").prop("disabled", false);
						$("#buttonSaveProfile").prop("disabled", false);
						$("#buttonCancelProfile").click();
					}, 3000);
					
				}).fail(function(err) {
					$("#buttonCancelProfile").prop("disabled", false);
					$("#buttonSaveProfile").prop("disabled", false);
					
					$("#divProfileFeedback").html(`<div class="p-3 text-primary-emphasis border border-danger-subtle bg-danger-subtle">An error has occured, please try again.</div>`).addClass("p-2");
					
					setTimeout(function() {
						$("#divProfileFeedback").html("").removeClass("p-2");
					}, 5000);
				});
			} else {
				$("#buttonCancelProfile").prop("disabled", false);
				$("#buttonSaveProfile").prop("disabled", false);
			}
		});
	}
	
	/* Returns Object, otherwise false on errors */
	function fieldsValidation() {
		let hasError = false;
		const fields = {};
		
		const firstName = $("#textFirstName");
		const lastName = $("#textLastName");
		const email = $("#textEmail");
		const phone = $("#textPhone");
		const gender = $("#selectGender");
		const birthDate = $("#textBirthDate");
		const address = $("#textAddress");
		
		let feedback = $("#textFirstNameFeedback");
		if (firstName.val().trim().length === 0) {
			feedback.html("Please enter first name.").attr("style", "display: block");
			firstName.attr("style", "border-color: #dc3545 !important");
			hasError = true;
		} else {
			feedback.html("").removeAttr("style");
			firstName.removeAttr("style");
			fields['firstName'] = firstName.val().trim();
		}
		
		feedback = $("#textLastNameFeedback");
		if (lastName.val().trim().length === 0) {
			feedback.html("Please enter last name.").attr("style", "display: block");
			lastName.attr("style", "border-color: #dc3545 !important");
			hasError = true;
		} else {
			feedback.html("").removeAttr("style");
			lastName.removeAttr("style");
			fields['lastName'] = lastName.val().trim();
		}
		
		feedback = $("#textEmailFeedback");
		if (email.val().trim().length === 0) {
			feedback.html("Please enter email address.").attr("style", "display: block");
			email.attr("style", "border-color: #dc3545 !important");
			hasError = true;
		} else {
			feedback.html("").removeAttr("style");
			email.removeAttr("style");
			fields['email'] = email.val().trim();
		}
		
		feedback = $("#textPhoneFeedback");
		if (phone.val().trim().length === 0) {
			feedback.html("Please enter phone number.").attr("style", "display: block");
			phone.attr("style", "border-color: #dc3545 !important");
			hasError = true;
		} else {
			feedback.html("").removeAttr("style");
			phone.removeAttr("style");
			fields['phone'] = phone.val().trim();
		}
		
		feedback = $("#selectGenderFeedback");
		if (gender.find(":selected").val() === "") {
			feedback.html("Please select gender.").attr("style", "display: block");
			gender.attr("style", "border-color: #dc3545 !important");
			hasError = true;
		} else {
			feedback.html("").removeAttr("style");
			gender.removeAttr("style");
			fields['gender'] = gender.find(":selected").val();
		}
		
		feedback = $("#textBirthDateFeedback");
		if (birthDate.val().trim().length === 0) {
			feedback.html("Please select birth date.").attr("style", "display: block");
			birthDate.attr("style", "border-color: #dc3545 !important");
			hasError = true;
		} else {
			feedback.html("").removeAttr("style");
			birthDate.removeAttr("style");
			fields['birthDate'] = birthDate.val().trim();
		}
		
		feedback = $("#textAddressFeedback");
		if (address.val().trim().length === 0) {
			feedback.html("Please enter address.").attr("style", "display: block");
			address.attr("style", "border-color: #dc3545 !important");
			hasError = true;
		} else {
			feedback.html("").removeAttr("style");
			address.removeAttr("style");
			fields['address'] = address.val().trim();
		}
		
		if (hasError) {
			return hasError;
		} else {
			return fields;	
		}
	}
});