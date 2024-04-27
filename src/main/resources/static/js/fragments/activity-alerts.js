$().ready(function() {
	fetchActivityAlerts();
	
	function fetchActivityAlerts() {
		$.ajax({
			url: `/user/fetch/activity-alerts`,
			method: "POST",
			headers: {
				"Content-Type": "application/json",
				"X-CSRF-TOKEN": $("input[name=_csrf]").val()
			}
		}).done(function(data) {
			if (data.length > 0) {
				$("#activityAlertsCount")
					.addClass("position-absolute top-10 start-30 mt-1 translate-middle badge rounded-pill border border-light bg-danger")
					.html(`${data.length} <span class="visually-hidden">unread messages</span>`);
				$("#offCanvasActivityAlertsCount").html(data.length);
				
				let markup = "";
				
				for (index in data) {
					const currentDate = new Date();
					const recordDate = new Date(data[index].createdOn);
					const diffInDays = Math.floor((currentDate - recordDate) / 86400000);
					
					markup += `
					<a href="#" onClick="javascript:updateActivityAlert(this, ${data[index].id})" class="list-group-item list-group-item-action d-flex gap-3 py-3" aria-current="true">
						<img src="/img/bell-solid.svg" alt="twbs" width="32" height="32" class="rounded-circle flex-shrink-0">
						<div class="d-flex gap-2 w-100 justify-content-between">
							<div>
								<h6 class="mb-0">${data[index].appointmentActivity.appointment.description}</h6>
								<p class="mb-0 opacity-75">${data[index].appointmentActivity.notes}</p>
								${statusMarkup(data[index].appointmentActivity.status)}
							</div>
								<small class="opacity-50 text-nowrap">${diffInDays}d</small>
						</div>
					</a>`;
				}
				
				$("#offCanvasActivityAlerts").html(markup);
				
			} else {
				$("#activityAlertsCount")
					.removeClass("position-absolute top-10 start-30 mt-1 translate-middle badge rounded-pill border border-light bg-danger")
					.html("");
				$("#offCanvasActivityAlertsCount").html(0);
				$("#offCanvasActivityAlerts").html("You're all caught up! No new alerts.");
			}
						
		});
	}
	
	// Attached this function to onClick event of anchor
	window.updateActivityAlert = function (el, id) {
		$.ajax({
			url: `/user/update/activity-alert/${id}`,
			method: "POST",
			headers: {
				"Content-Type": "application/json",
				"X-CSRF-TOKEN": $("input[name=_csrf]").val()
			}
		}).done(function() {
			setTimeout(function() {
				el.remove();
				fetchActivityAlerts();
										
				}, 1000);
					
		});
	}
	
	function statusMarkup(data) {
		switch(data.toUpperCase()) {
			case "PENDING": {
				return `<span class="badge text-bg-secondary">${data}</span>`;
			};
			case "BOOKED": {
				return `<span class="badge text-bg-info">${data}</span>`;
			};
			case "ARRIVED": {
				return `<span class="badge text-bg-primary">${data}</span>`;
			};
			case "FULFILLED": {
				return `<span class="badge text-bg-success">${data}</span>`;
			};
			case "CANCELLED": {
				return `<span class="badge text-bg-danger">${data}</span>`;
			};
			case "WAITLIST": {
				return `<span class="badge text-bg-warning">${data}</span>`;
			}
		}
	}
	
});