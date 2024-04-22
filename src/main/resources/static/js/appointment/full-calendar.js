$().ready(function() {
	
	window.initAppointmentCalendar = function(id=0) {
	  	window.appointmentCalendar = new FullCalendar.Calendar(document.getElementById("appointmentCalendar"), {
		    initialView: "dayGridMonth",
		    initialDate: new Date(),
		    themeSystem: "bootstrap5",
		    dayMaxEvents: true,
		    headerToolbar: {
		      left: "prev,next today",
		      center: "title",
		      right: "dayGridMonth,timeGridWeek,timeGridDay,listMonth"
		    },
			events: function(info, successCallback, failureCallback) {
				const data = {
					id: id,
					startDate: info.startStr,
					endDate: info.endStr
				}
				
				// No filter section for PATIENT
				if ($("#hiddenAuthorizeRole").val() === "ROLE_PATIENT") {
					delete data['id'];
				} else {
					if (id === 0) return [];
				}
	
				$.ajax({
					url: "/appointment/fetch/appointments",
					method: "POST",
					headers: {
						"Content-Type": "application/json",
						"X-CSRF-TOKEN": $("input[name=_csrf]").val()
					},
					data: JSON.stringify(data),
				}).done(function(data) {
					successCallback(data.map(row => {
						row["title"] = row.description;
						row["start"] = row.datetime;
						row["textColor"] = "black";
	
						switch(row.status.toUpperCase()) {
							case "PENDING": {
								row["color"] = "#6c757d";
								row["textColor"] = "white";
								break;
							}
							case "BOOKED": {
								row["color"] = "cyan";
								break;
							}
							case "ARRIVED": {
								row["color"] = "blue";
								break;
							}
							case "FULFILLED": {
								row["color"] = "green";
								break;
							}
							case "CANCELLED": {
								row["color"] = "red";
								break;
							}
							case "WAITLIST": {
								row["color"] = "yellow";
								break;
							}
						}
								
						return row;
					}));
				}).fail(function(err) {
					failureCallback(err);
				});
			},
			eventClick: function(info) {
				const btoaData = window.btoa(JSON.stringify(info.event));
				$("#hiddenAppointmentData").val(btoaData);
				$("#chooseAction").modal("show");
			}
	  	});
	
	  	window.appointmentCalendar.render();
	}

	window.initAppointmentCalendar(0);
	
});