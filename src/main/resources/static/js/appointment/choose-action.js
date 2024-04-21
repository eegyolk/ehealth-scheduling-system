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
							
		});
		
		$("#chooseAction").on("hidden.bs.modal", function(ev) {
			$("#hiddenAppointmentData").val("");
		});
	}
});