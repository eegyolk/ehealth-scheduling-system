$().ready(function() {
	modalEvents();
	
	function modalEvents() {
		$("#viewAppointment").on("show.bs.modal", function(ev) {
			const btoaData = $(ev.relatedTarget).data("btoa-data");
			const appointment = JSON.parse(window.atob(btoaData));
			const location = appointment.location;
			const doctor = appointment.doctor;
			const appointmentActivities = appointment.appointmentActivities;
			
			// Appointment Details
			$("#divTitle").html(appointment.description);
			$("#divReason").html(appointment.reason);
			$("#divDatetime").html(datetimeReformat(appointment.datetime));
			$("#divJoinWaitlist").html(appointment.joinWaitlist ? "Yes, I would like to join the waitlist." : "No, I do not wish to join the waitlist");
			$("#divLastUpdate").html(appointment.updatedOn ? datetimeReformat(appointment.updatedOn) : datetimeReformat(appointment.createdOn));
			$("#divReferenceNo").html(appointment.referenceNo);
			$("#divSlotNo").html(appointment.slot);
			$("#divStatus").html(statusMarkup(appointment.status));
			
			// Healthcare Provider
			$("#divClinicName").html(location.name);
			$("#divClinicAddress").html(location.address);
			$("#divClinicEmail").html(location.email);
			$("#divClinicPhone").html(location.phone);
			$("#divDoctorName").html(`Dr. ${doctor.firstName} ${doctor.lastName}`);
			$("#divDoctorDepartment").html(departmentReformat(doctor.department));
			$("#divDoctorEmail").html(doctor.email);
			$("#divDoctorPhone").html(doctor.phone);
			
			// Activites
			const sortedActivites = appointmentActivities.sort(function(a, b){return b.id - a.id});
			let trHTML = "";
			for (activity of sortedActivites) {
				const user = activity.user;
				trHTML += `
					<tr>
						<td class="fs-6 fw-medium">${user.username}<br/>
							<small class="opacity-50 fw-light"><i class="fa-solid fa-calendar-days opacity-50"></i> ${datetimeReformat(activity.createdOn)}</small></td>
						<td class="fs-6 fw-medium">${statusMarkup(activity.status)}</td>
						<td class="fs-6 fw-medium">${activity.notes}</td>
					</tr>
				`;
		
			}
			$("#tableBodyActivities").html(trHTML);
		})
		
		$("#viewAppointment").on("hide.bs.modal", function(ev) {
			// Appointment Details
			$("#divTitle").html("");
			$("#divReason").html("");
			$("#divDatetime").html("");
			$("#divJoinWaitlist").html("");
			$("#divLastUpdate").html("");
			$("#divReferenceNo").html("");
			$("#divSlotNo").html("");
			$("#divStatus").html("");
			
			// Healthcare Provider
			$("#divClinicName").html("");
			$("#divClinicAddress").html("");
			$("#divClinicEmail").html("");
			$("#divClinicPhone").html("");
			$("#divDoctorName").html("");
			$("#divDoctorDepartment").html("");
			$("#divDoctorEmail").html("");
			$("#divDoctorPhone").html("");
			
			// Activities
			$("#tableBodyActivities").html("");
		});
	}
	
	function datetimeReformat(data) {
		const datetime = new Date(data);
		return `${datetime.toLocaleString().replace(",", "").replace(/(\:\d{2} AM)/, " AM").replace(/(\:\d{2} PM)/, " PM")}`;
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