$().ready(function() {
	
	const calendar = new FullCalendar.Calendar(document.getElementById("calendar"), {
	    initialView: "dayGridMonth",
	    initialDate: new Date(),
	    themeSystem: "bootstrap5",
	    dayMaxEvents: true,
	    headerToolbar: {
	      left: "prev,next",
	      center: "title",
	      right: "today"
	    }
	});

	calendar.render();

});