$().ready(function() {
	modalEvents();
	
	function modalEvents() {
		$("#viewSchedule").on("show.bs.modal", function(ev) {
			const btoaData = $(ev.relatedTarget).data("btoa-data");
			const doctor = JSON.parse(window.atob(btoaData));
			
			$.ajax({
				url: `/doctor/fetch/schedule/${doctor.id}`,
				method: "POST",
				headers: {
					"Content-Type": "application/json",
					"X-CSRF-TOKEN": $("input[name=_csrf]").val()
				}
			}).done(function(data) {
				// Healthcare Provider
				$("#divName").html(`Dr. ${doctor.firstName} ${doctor.lastName}`);
				$("#divDepartment").html(departmentReformat(doctor.department));
				$("#divEmail").html(doctor.email);
				$("#divPhone").html(doctor.phone);
				
				// Schedules
				let trHTML = "";
				for (row of data) {
					const location = row.location;
	
					trHTML += `
						<tr>
							<td class="fs-6 fw-medium">${location.name}<br/>
								<small class="opacity-50 fw-light"><i class="fa-solid fa-house-chimney-medical opacity-50"></i> ${location.address}</small></td>
							<td class="fs-6 fw-medium">${row.dayOfWeek}</td>
							<td class="fs-6 fw-medium">${timeReformat(row.startTime, row.endTime)}</td>
							<td class="fs-6 fw-medium">${row.slot}</td>
							<td class="fs-6 fw-medium">${row.duration}</td>
						</tr>
					`;
			
				}
				$("#tableBodySchedules").html(trHTML);
				
				// Hide spinner, then show contents
				setTimeout(function() {
					$("#divVSSpin").addClass("d-none");
					$("#divVSDialog").removeClass("modal-dialog-centered modal-sm").addClass("modal-lg");
					$("#divVSHeader").removeClass("d-none");
					$("#divVSBody").removeClass("d-none");
				}, 500);
					
			}).fail(function(err) {
				console.log(err);
			});
		});
		
		$("#viewSchedule").on("hidden.bs.modal", function(ev) {
			$("#divVSSpin").removeClass("d-none");
			$("#divVSDialog").addClass("modal-dialog-centered modal-sm").removeClass("modal-lg");
			$("#divVSHeader").addClass("d-none");
			$("#divVSBody").addClass("d-none");
			$("#tableBodySchedules").html("");
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
	
	function departmentReformat(data) {
		const departmentParts = data.toLowerCase().split("_");
		let department = "";
		if (departmentParts.length === 1) {
			department = departmentParts[0].charAt(0).toUpperCase() + departmentParts[0].slice(1);
		} else if (departmentParts.length === 2) {
			department = `${departmentParts[0].charAt(0).toUpperCase() + departmentParts[0].slice(1)} ${departmentParts[1].charAt(0).toUpperCase() + departmentParts[1].slice(1)}`;
		}
		return department;
	}
});