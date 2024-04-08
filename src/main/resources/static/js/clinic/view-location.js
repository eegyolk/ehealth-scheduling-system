$().ready(function() {
	modalEvents();
	
	function modalEvents() {
		$("#viewLocation").on("show.bs.modal", function(ev) {
			const btoaData = $(ev.relatedTarget).data("btoa-data");
			const atobData = window.atob(btoaData);
			const location = JSON.parse(atobData);

			// Healthcare Provider
			$("#divNameVL").html(location.name);
			$("#divAddressVL").html(location.address);
			$("#divEmailVL").html(location.email);
			$("#divPhoneVL").html(location.phone);

			// Google Map			
			const position = { lat: location.latitude, lng: location.longitude };
			const map_parameters = {
				center: position,
				zoom: 14,
			};
			const map = new google.maps.Map(document.getElementById('divMap'), map_parameters);
          	new google.maps.Marker({
             	position: position,
             	map: map,
             	title: `${location.name} - ${location.address}`
          	});
          	
          	// Hide spinner, then show contents
			setTimeout(function() {
				$("#divVLSpin").addClass("d-none");
				$("#divVLDialog").removeClass("modal-dialog-centered modal-sm").addClass("modal-lg");
				$("#divVLHeader").removeClass("d-none");
				$("#divVLBody").removeClass("d-none");
			}, 500);
		});
		
		$("#viewLocation").on("hidden.bs.modal", function(ev) {
			$("#divVLSpin").removeClass("d-none");
			$("#divVLDialog").addClass("modal-dialog-centered modal-sm").removeClass("modal-lg");
			$("#divVLHeader").addClass("d-none");
			$("#divVLBody").addClass("d-none");
		});
	}
});