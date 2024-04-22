$().ready(function() {
	buttonEvents();

	function buttonEvents() {
		$("#buttonBack").click(function(ev) {
			history.go(-1);	
		});
		
		$("#buttonRefresh").click(function(ev) {
			location.reload();	
		});	
	}
	
});