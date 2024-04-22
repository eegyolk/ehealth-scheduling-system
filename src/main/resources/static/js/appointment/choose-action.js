$().ready(function() {
	modalEvents();
	
	function modalEvents() {
		$("#chooseAction").on("show.bs.modal", function(ev) {
			const atobData = JSON.parse(window.atob($("#hiddenAppointmentData").val()));
			const appointment = { id: parseInt(atobData.id), ...atobData.extendedProps };

		    $("#buttonChooseActionView").data("btoa-data", window.btoa(JSON.stringify(appointment)))
		    $("#buttonChooseActionUpdate")
		    	.data("id", appointment.id)
		    	.data("status", appointment.status)
		    	.data("slot", appointment.slot)
		    	.data("join-waitlist", appointment.joinWaitlist);

			if ($("#hiddenAuthorizeRole").val() === "ROLE_PATIENT" || $("#hiddenAuthorizeRole").val() === "ROLE_STAFF") {
				const disabled = ["FULFILLED", "CANCELLED"].includes(appointment.status);
				$("#buttonChooseActionUpdate").prop("disabled", disabled);
			} else if ($("#hiddenAuthorizeRole").val() === "ROLE_DOCTOR") {
				const notDisabled = ["ARRIVED"].includes(appointment.status);
				$("#buttonChooseActionUpdate").prop("disabled", !notDisabled);
			}
		});
		
		$("#chooseAction").on("hidden.bs.modal", function(ev) {
			$("#hiddenAppointmentData").val("");
		});
	}
});