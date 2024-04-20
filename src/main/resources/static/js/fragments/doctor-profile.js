$().ready(function() {
	let currentFirstName;
	let currentLastName;
	let currentEmail;
	let currentPhone;
	let currentDepartment;
		
	modalEvents();
	buttonEvents();
	
	function modalEvents() {
		$("#doctorProfile").on("shown.bs.modal", function(ev) {
			currentFirstName = $("#textFirstName").val();
			currentLastName = $("#textLastName").val();
			currentEmail = $("#textEmail").val();
			currentPhone = $("#textPhone").val();
			currentDepartment = $("#selectDepartment").find(":selected").val();
		});
		
		$("#doctorProfile").on("hidden.bs.modal", function(ev) {
			$("#divProfileFeedback").html("").removeClass("p-2");
			$("#textFirstName").val(currentFirstName).removeAttr("style");
			$("#textFirstNameFeedback").html("").removeAttr("style");
			$("#textLastName").val(currentLastName).removeAttr("style");
			$("#textLastNameFeedback").html("").removeAttr("style");
			$("#textEmail").val(currentEmail).removeAttr("style");
			$("#textEmailFeedback").html("").removeAttr("style");
			$("#textPhone").val(currentPhone).removeAttr("style");
			$("#textPhoneFeedback").html("").removeAttr("style");
			$("#selectDepartment").val(currentDepartment).removeAttr("style");
			$("#selectDepartmentFeedback").html("").removeAttr("style");
			$("#buttonCancelProfile").prop("disabled", false);
			$("#buttonSaveProfile").prop("disabled", false);
		});
	}
	
	function buttonEvents() {
		$("#buttonSaveProfile").click(function(ev) {
			$("#buttonCancelProfile").prop("disabled", true);
			$("#buttonSaveProfile").prop("disabled", true);
					
			const result = fieldsValidation();
 
			if (typeof(result) === 'object') {
				$.ajax({
				  	url: `/user/update/doctor`,
				  	method: "POST",
				  	headers: {
						"Content-Type": "application/json",
						"X-CSRF-TOKEN": $("input[name=_csrf]").val()
					},
				  	data: JSON.stringify(result)
				}).done(function(data) {
					$("#divProfileFeedback").html(`<div class="p-3 text-primary-emphasis border border-success-subtle bg-success-subtle">Your profile has been updated successfully.</div>`).addClass("p-2");
					
					currentFirstName = result.firstName;
					currentLastName = result.lastName;
					currentEmail = result.email;
					currentPhone = result.phone;
					currentDepartment = result.department;
					
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
		const department = $("#selectDepartment");
		
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
		
		feedback = $("#selectDepartmentFeedback");
		if (department.find(":selected").val() === "") {
			feedback.html("Please select department.").attr("style", "display: block");
			department.attr("style", "border-color: #dc3545 !important");
			hasError = true;
		} else {
			feedback.html("").removeAttr("style");
			department.removeAttr("style");
			fields['department'] = department.find(":selected").val();
		}
		
		if (hasError) {
			return hasError;
		} else {
			return fields;	
		}
	}
});