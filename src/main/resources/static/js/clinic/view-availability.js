$().ready(function() {
	modalEvents();
	
	function modalEvents() {
		$("#viewAvailability").on("show.bs.modal", function(ev) {
			const btoaData = $(ev.relatedTarget).data("btoa-data");
			const atobData = window.atob(btoaData);
			const location = JSON.parse(atobData);
			
			$.ajax({
				url: `/clinic/fetch/availability/${location.id}`,
				method: "POST",
				headers: {
					"Content-Type": "application/json",
					"X-CSRF-TOKEN": $("input[name=_csrf]").val()
				}
			}).done(function(data) {
				// Healthcare Provider
				$("#divName").html(location.name);
				$("#divAddress").html(location.address);
				$("#divEmail").html(location.email);
				$("#divPhone").html(location.phone);
			
				// Availability
				let trHTML = "";
				for (row of data) {	
					trHTML += `
						<tr>
							<td class="fs-6 fw-medium">${row.dayOfWeek}</td>
							<td class="fs-6 fw-medium">${row.allDay ? "Yes" : "No"}</td>
							<td class="fs-6 fw-medium">${timeReformat(row.startTime, row.endTime)}</td>
						</tr>
					`;
			
				}
				$("#tableBodyAvailability").html(trHTML);
				
				// Hide spinner, then show contents
				setTimeout(function() {
					$("#divVASpin").addClass("d-none");
					$("#divVADialog").removeClass("modal-dialog-centered modal-sm").addClass("modal-lg");
					$("#divVAHeader").removeClass("d-none");
					$("#divVABody").removeClass("d-none");
				}, 500);
					
			}).fail(function(err) {
				console.log(err);
			});
		});

		$("#viewAvailability").on("hidden.bs.modal", function(ev) {
			$("#divVASpin").removeClass("d-none");
			$("#divVADialog").addClass("modal-dialog-centered modal-sm").removeClass("modal-lg");
			$("#divVAHeader").addClass("d-none");
			$("#divVABody").addClass("d-none");
		});
	}
	
	function timeReformat(startTime, endTime) {
		const startTimeParts = startTime.split(":");
		const endTimeParts = endTime.split(":");
		
		let time = "";
		
		if (parseInt(startTimeParts[0]) >= 12) {
			time = `${startTime} PM`;
		} else {
			time = `${startTime} AM`;
		}
		
		if (parseInt(endTimeParts[0]) >= 12) {
			time += ` - ${endTime} PM`;
		} else {
			time += ` - ${endTime} AM`;
		}
		
		return time;
	}

});