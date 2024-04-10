$().ready(function() {
	
	window.initDataTableAppointmentList = function() {
		window.tableAppointmentList = $("#tableAppointmentList").DataTable({
			autoWidth: false,
			info: false,
			pagingType: "full_numbers",
			processing: true,
			searching: false,
			serverSide : true,
			tabInde: 1,
			order: {
		        idx: 0,
		        dir: "desc",
		        name: "id"
		    },
			ajax: {
		    	url: "/appointment/fetch/appointments",
		    	type: "POST",
		    	data: function(data) {
					// Workaround to pass search field and value		
					const selectBy = $("#selectBy").find(":selected").val();
					if (selectBy != "") {
						if (parseInt(selectBy) === 1) { // By Reference No.
							data.columns[11].search.value = parseInt(selectBy);
							data.columns[12].search.value = $("#textReferenceNo").val().trim().length >= 3 ? $("#textReferenceNo").val().trim() : "";							
						} else if (parseInt(selectBy) === 2) { // By Patient
							data.columns[11].search.value = parseInt(selectBy);
							data.columns[12].search.value = $("#textPatient").val().trim().length >= 3 ? $("#textPatient").val().trim() : "";
						} else if (parseInt(selectBy) === 3) { // By Practitioner
							data.columns[11].search.value = parseInt(selectBy);
							data.columns[12].search.value = $("#textPractitioner").val().trim().length >= 3 ? $("#textPractitioner").val().trim() : "";							
						} else if (parseInt(selectBy) === 4) { // By Clinic
							data.columns[11].search.value = parseInt(selectBy);
							data.columns[12].search.value = $("#selectClinic").find(":selected").val();						
						} else if (parseInt(selectBy) === 5) { // By Date
							data.columns[11].search.value = parseInt(selectBy);
							data.columns[12].search.value = $("#textDate").val();						
						} else if (parseInt(selectBy) === 6) { // By Status
							data.columns[11].search.value = parseInt(selectBy);
							data.columns[12].search.value = $("#selectStatus").find(":selected").val();						
						}
					}

					return JSON.stringify(data);
				},
		    	beforeSend: function(request) {
					request.setRequestHeader("Content-Type", "application/json");
					request.setRequestHeader("X-CSRF-TOKEN", $("input[name=_csrf]").val());	
				}
		  	},
		  	layout: {
				topStart: "",
			    topEnd: "",
			    bottomStart: "pageLength",
			    bottomEnd: "paging"
			},
		  	columns : [
				{
					data: "id",
					searchable: false,
					render: function(data, type, row) {
						return `<small>${data}</small>`;
					}
				},
				{
					data: "referenceNo",
					searchable: true,
					render: function(data, type, row) {
						return `<small>${data}</small>`;
					}
				},
				{
					searchable: false,
					orderable: false,
					render: function(data, type, row) {
						return `<small>${row.patient.firstName} ${row.patient.lastName}</small>`;
					}
				},
				{
					searchable: false,
					orderable: false,
					visible: $("#hiddenAuthorizeRole").val() === "ROLE_STAFF" ? true : false,
					render: function(data, type, row) {
						return `<small>Dr. ${row.doctor.firstName} ${row.doctor.lastName}</small>`;
					}
				},
				{
					searchable: false,
					orderable: false,
					visible: $("#hiddenAuthorizeRole").val() === "ROLE_DOCTOR" ? true : false,
					render: function(data, type, row) {
						return `<small>${row.location.name}`;
					}
				},
				{
					data: "datetime",
					searchable: false,
					render: function(data, type, row) {
						return datetimeMarkup(data);
					}
				},
				{
					data: "status",
					searchable: false,
					render: function(data, type, row) {
						return statusMarkup(data);
					}
				},
				{
					data: "slot",
					searchable: true,
					render: function(data, type, row) {
						return `<small>${data}</small>`;
					}
				},
				{
					data: "createdOn",
					searchable: false,
					render: function(data, type, row) {
						return datetimeMarkup(data);
					}
				},
				{
					data: "updatedOn",
					searchable: false,
					render: function(data, type, row) {
						return datetimeMarkup(data);
					}
				},
				{ // Action
					searchable: false,
					orderable: false,
					render: function(data, type, row) {
						return actionMarkup(row);
					}
				},
				{ // Use this as a workaround for search functionality
					name: "searchByFieldName",
					searchable: false,
					orderable: false,
					visible: false,
					render: function() {
						return "";
					}
				},
				{ // Use this as a workaround for search functionality
					name: "searchByFieldValue",
					searchable: false,
					orderable: false,
					visible: false,
					render: function() {
						return "";
					}
				},
    		],
    		drawCallback: function() {
				$('[data-bs-toggle="tooltip"]').tooltip({
					container: 'body'
				});
          	},
		});
	};
	
	window.initDataTableAppointmentList();
	initToolTip();
	initSearch();
	initDateTimePicker();
	
	function initToolTip() {
		const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]')
		const tooltipList = [...tooltipTriggerList].map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl))	
	}
	
	function initSearch() {
		$("#selectBy").change(function(ev) {
			if (ev.target.value === "") {
				$("#divByReferenceNo").addClass("d-none");
				$("#divByPatient").addClass("d-none");
				$("#divByPractitioner").addClass("d-none");
				$("#divByClinic").addClass("d-none");
				$("#divByDate").addClass("d-none");
				$("#divByStatus").addClass("d-none");
				$("#textReferenceNo").val("");
				$("#textPatient").val("");
				$("#textPractitioner").val("");
				$("#selectClinic").val("");
				$("#textDate").val("");
				$("#selectStatus").val("");
			} else if (parseInt(ev.target.value) === 1) {
				$("#divByReferenceNo").removeClass("d-none");
				$("#divByPatient").addClass("d-none");
				$("#divByPractitioner").addClass("d-none");
				$("#divByClinic").addClass("d-none");
				$("#divByDate").addClass("d-none");
				$("#divByStatus").addClass("d-none");
				$("#textReferenceNo").val("");
			} else if (parseInt(ev.target.value) === 2) {
				$("#divByReferenceNo").addClass("d-none");
				$("#divByPatient").removeClass("d-none");
				$("#divByPractitioner").addClass("d-none");
				$("#divByClinic").addClass("d-none");
				$("#divByDate").addClass("d-none");
				$("#divByStatus").addClass("d-none");
				$("#textPatient").val("");
			} else if (parseInt(ev.target.value) === 3) {
				$("#divByReferenceNo").addClass("d-none");
				$("#divByPatient").addClass("d-none");
				$("#divByPractitioner").removeClass("d-none");
				$("#divByClinic").addClass("d-none");
				$("#divByDate").addClass("d-none");
				$("#divByStatus").addClass("d-none");
				$("#textPractitioner").val("");
			} else if (parseInt(ev.target.value) === 4) {
				$("#divByReferenceNo").addClass("d-none");
				$("#divByPatient").addClass("d-none");
				$("#divByPractitioner").addClass("d-none");
				$("#divByClinic").removeClass("d-none");
				$("#divByDate").addClass("d-none");
				$("#divByStatus").addClass("d-none");
				$("#selectClinic").val("");
			} else if (parseInt(ev.target.value) === 5) {
				$("#divByReferenceNo").addClass("d-none");
				$("#divByPatient").addClass("d-none");
				$("#divByPractitioner").addClass("d-none");
				$("#divByClinic").addClass("d-none");
				$("#divByDate").removeClass("d-none");
				$("#divByStatus").addClass("d-none");
				$("#textDate").val("");
			} else if (parseInt(ev.target.value) === 6) {
				$("#divByReferenceNo").addClass("d-none");
				$("#divByPatient").addClass("d-none");
				$("#divByPractitioner").addClass("d-none");
				$("#divByClinic").addClass("d-none");
				$("#divByDate").addClass("d-none");
				$("#divByStatus").removeClass("d-none");
				$("#selectStatus").val("");
			}
		});
		
		$("#buttonSearch").click(function(ev) {
			window.tableAppointmentList.destroy();
			window.initDataTableAppointmentList();
		});
	}
	
	function initDateTimePicker() {
		window.datePicker = new tempusDominus.TempusDominus(document.getElementById("datetimepicker1"), {
			allowInputToggle: true,
			display: { components: { clock: false } },
			localization: { format: 'yyyy-MM-dd' },
  			useCurrent: false
		});
	}
	
	function datetimeMarkup(data) {
		if (data === null) {
			return `-`;
		} else {
			const datetime = new Date(data);
			return `<small>${datetime.toLocaleString().replace(",", "").replace(/(\:\d{2} AM)/, " AM").replace(/(\:\d{2} PM)/, " PM")}</small>`;
		}
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
	
	function actionMarkup(row) {
		const disableEdit = ["FULFILLED", "CANCELLED"].includes(row.status);
		const btoaData = window.btoa(JSON.stringify(row));
		
		return `
			<div class="btn-group">
		  		<button class="btn btn-primary btn-sm dropdown-toggle" type="button" data-bs-toggle="dropdown" data-bs-auto-close="outside" aria-expanded="false">
		    		Action
		  		</button>
		  		<ul class="dropdown-menu">
		    		<li><a class="dropdown-item" href="#viewAppointment" data-btoa-data="${btoaData}" data-bs-toggle="modal" data-bs-target="#viewAppointment"><small><i class="fa-solid fa-eye"></i> View</small></a></li>
		    		<li><a class="dropdown-item ${disableEdit ? 'disabled' : ''}" href="#editAppointment" data-btoa-data="${btoaData}" data-bs-toggle="modal" data-bs-target="#editAppointment"><small><i class="fa-solid fa-eraser"></i> Edit</small></a></li>
		  		</ul>
			</div>
		`;
	}
});