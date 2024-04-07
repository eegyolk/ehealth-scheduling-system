$().ready(function() {
	modalEvents();
	
	function modalEvents() {
		$("#viewSchedule").on("show.bs.modal", function(ev) {
			const btoaData = $(ev.relatedTarget).data("btoa-data");
			const doctor = JSON.parse(window.atob(btoaData));
			const schedules = doctor.doctorSchedules;
			
			// Healthcare Provider
			$("#divName").html(`Dr. ${doctor.firstName} ${doctor.lastName}`);
			$("#divDepartment").html(departmentReformat(doctor.department));
			$("#divEmail").html(doctor.email);
			$("#divPhone").html(doctor.phone);
			
			// Schedules
			let trHTML = "";
			for (sched of schedules) {
				const location = sched.location;

				trHTML += `
					<tr>
						<td class="fs-6 fw-medium">${location.name}<br/>
							<small class="opacity-50 fw-light"><i class="fa-solid fa-house-chimney-medical opacity-50"></i> ${location.address}</small></td>
						<td class="fs-6 fw-medium">${sched.dayOfWeek}</td>
						<td class="fs-6 fw-medium">${timeReformat(sched.startTime, sched.endTime)}</td>
						<td class="fs-6 fw-medium">${sched.slot}</td>
						<td class="fs-6 fw-medium">${sched.duration}</td>
					</tr>
				`;
		
			}
			$("#tableBodySchedules").html(trHTML);
		})
		
		$("#viewSchedule").on("hidden.bs.modal", function(ev) {
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