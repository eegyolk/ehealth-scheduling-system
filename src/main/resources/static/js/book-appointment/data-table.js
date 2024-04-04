$().ready(function() {
	
	window.initDataTableAppointmentList = function(referenceNo=null) {
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
		    	url: "/book-appointment/fetch/appointments",
		    	type: "POST",
		    	data: function(data) {
					if (referenceNo != null && referenceNo.length >= 3) {
						data.search.value = referenceNo;	
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
					data: "datetime",
					searchable: false,
					render: function(data, type, row) {
						return datetimeMarkup(data);
					}
				},
				{
					data: "description",
					searchable: false,
					orderable: false,
					render: function(data, type, row) {
						return truncateText(data, 15);
					}
				},
				{
					data: "reason",
					searchable: false,
					orderable: false,
					render: function(data, type, row) {
						return truncateText(data, 25);
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
					data: "createdOn",
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
				}
    		],
    		drawCallback: function() {
				$('[data-bs-toggle="tooltip"]').tooltip({
					container: 'body'
				});
          	},
		});
	};
	
	window.initDataTableAppointmentList(null);
	initToolTip();
	initSearchByReferenceNo();
	
	function initToolTip() {
		const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]')
		const tooltipList = [...tooltipTriggerList].map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl))	
	}
	
	function initSearchByReferenceNo() {
		$("#buttonSearch").click(function(ev) {
			window.tableAppointmentList.destroy();
			window.initDataTableAppointmentList($("#textReferenceNo").val());
		});
	}
	
	function datetimeMarkup(data) {
		const datetime = new Date(data);
		return `<small>${datetime.toLocaleString().replace(",", "").replace(/(\:\d{2} AM)/, " AM").replace(/(\:\d{2} PM)/, " PM")}</small>`;
	}
	
	function truncateText(data, length) {
		if (data.length > length) {
			return `<small data-bs-toggle="tooltip" data-bs-placement="bottom" data-bs-custom-class="custom-tooltip" data-bs-title="${data}"></i>${data.substring(0, length)}...</small>`;	
		} else {
			return `<small>${data}</small>`;
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
		const disableCancel = ["FULFILLED", "CANCELLED"].includes(row.status);
		const btoaData = window.btoa(JSON.stringify(row));
		
		return `
			<div class="btn-group">
		  		<button class="btn btn-primary btn-sm dropdown-toggle" type="button" data-bs-toggle="dropdown" data-bs-auto-close="outside" aria-expanded="false">
		    		Action
		  		</button>
		  		<ul class="dropdown-menu">
		    		<li><a class="dropdown-item" href="#viewAppointment" data-btoa-data="${btoaData}" data-bs-toggle="modal" data-bs-target="#viewAppointment"><small><i class="fa-solid fa-eye"></i> View</small></a></li>
		    		<li><a class="dropdown-item ${disableCancel ? 'disabled' : ''}" href="#cancelAppointment" data-btoa-data="${btoaData}" data-bs-toggle="modal" data-bs-target="#cancelAppointment"><small><i class="fa-solid fa-eraser"></i> Cancel</small></a></li>
		  		</ul>
			</div>
		`;
	}
});