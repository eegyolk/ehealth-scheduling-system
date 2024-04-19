$().ready(function() {
	modalEvents();
	buttonEvents();
	
	function modalEvents() {
		$("#account").on("hidden.bs.modal", function(ev) {
			$("#divAccountFeedback").html("").removeClass("p-2");
			$("#textOldPassword").val("").removeAttr("style");
			$("#textOldPasswordFeedback").html("").removeAttr("style");
			$("#textNewPassword").val("").removeAttr("style");
			$("#textNewPasswordFeedback").html("").removeAttr("style");
			$("#textConfirmNew").val("").removeAttr("style");
			$("#textConfirmNewFeedback").html("").removeAttr("style");
			$("#buttonCancelAccount").prop("disabled", false);
			$("#buttonSaveAccount").prop("disabled", false);
		});
	}
	
	function buttonEvents() {
		$("#buttonSaveAccount").click(function(ev) {
			$("#buttonCancelAccount").prop("disabled", true);
			$("#buttonSaveAccount").prop("disabled", true);
					
			const result = fieldsValidation();
 
			if (typeof(result) === 'object') {
				$.ajax({
				  	url: `/user/update/password`,
				  	method: "POST",
				  	headers: {
						"Content-Type": "application/json",
						"X-CSRF-TOKEN": $("input[name=_csrf]").val()
					},
				  	data: JSON.stringify(result)
				}).done(function(data) {
					$("#divAccountFeedback").html(`<div class="p-3 text-primary-emphasis border border-success-subtle bg-success-subtle">Password has been updated successfully.</div>`).addClass("p-2");
					
					setTimeout(function() {						
						$("#buttonCancelAccount").prop("disabled", false);
						$("#buttonSaveAccount").prop("disabled", false);

						window.location.href = "/logout";
					}, 3000);
					
				}).fail(function(err) {
					$("#buttonCancelAccount").prop("disabled", false);
					$("#buttonSaveAccount").prop("disabled", false);
					
					$("#divAccountFeedback").html(`<div class="p-3 text-primary-emphasis border border-danger-subtle bg-danger-subtle">${err.responseJSON.message}.</div>`).addClass("p-2");
					
					setTimeout(function() {
						$("#divAccountFeedback").html("").removeClass("p-2");
					}, 5000);
				});
			} else {
				$("#buttonCancelAccount").prop("disabled", false);
				$("#buttonSaveAccount").prop("disabled", false);
			}
		});
	}
	
	/* Returns Object, otherwise false on errors */
	function fieldsValidation() {
		let hasError = false;
		const fields = {};
		
		const oldPassword = $("#textOldPassword");
		const newPassword = $("#textNewPassword");
		const confirmNew = $("#textConfirmNew");
		
		let feedback = $("#textOldPasswordFeedback");
		if (oldPassword.val().trim().length === 0) {
			feedback.html("Please enter your old password.").attr("style", "display: block");
			oldPassword.attr("style", "border-color: #dc3545 !important");
			hasError = true;
		} else {
			feedback.html("").removeAttr("style");
			oldPassword.removeAttr("style");
			fields['oldPassword'] = oldPassword.val().trim();
		}
		
		feedback = $("#textNewPasswordFeedback");
		if (newPassword.val().trim().length === 0) {
			feedback.html("Please enter your new password.").attr("style", "display: block");
			newPassword.attr("style", "border-color: #dc3545 !important");
			hasError = true;
		} else {
			feedback.html("").removeAttr("style");
			newPassword.removeAttr("style");
			fields['newPassword'] = newPassword.val().trim();
		}
		
		feedback = $("#textConfirmNewFeedback");
		if (confirmNew.val().trim().length === 0) {
			feedback.html("Please confirm your new password.").attr("style", "display: block");
			confirmNew.attr("style", "border-color: #dc3545 !important");
			hasError = true;
		} else {
			feedback.html("").removeAttr("style");
			confirmNew.removeAttr("style");
		}
		
		if (newPassword.val().trim().length > 0 && confirmNew.val().trim().length > 0 && newPassword.val().trim() != confirmNew.val().trim()) {
			$("#textNewPasswordFeedback").html("New password mismatch.").attr("style", "display: block");
			$("#textConfirmNewFeedback").html("Confirm password mismatch.").attr("style", "display: block");
			newPassword.attr("style", "border-color: #dc3545 !important");
			confirmNew.attr("style", "border-color: #dc3545 !important");
			hasError = true;
		}
		
		if (hasError) {
			return hasError;
		} else {
			return fields;	
		}
	}
});