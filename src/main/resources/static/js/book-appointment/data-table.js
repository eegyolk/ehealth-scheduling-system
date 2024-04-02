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
		    	url: "/book-appointment/fetch/appointments",
		    	type: "POST",
		    	data: function(data) {
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
					searchable: false,
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
	
	window.initDataTableAppointmentList();
	initToolTip();
	
	function initToolTip() {
		const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]')
		const tooltipList = [...tooltipTriggerList].map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl))	
	}
	
	function flatten(params) {
		params.columns.forEach(function (column, index) {
		    params['columns[' + index + '].data'] = column.data;
		    params['columns[' + index + '].name'] = column.name;
		    params['columns[' + index + '].searchable'] = column.searchable;
		    params['columns[' + index + '].orderable'] = column.orderable;
		    params['columns[' + index + '].search.regex'] = column.search.regex;
		    params['columns[' + index + '].search.value'] = column.search.value;
	  	});
	  	delete params.columns;
	
	  	params.order.forEach(function (order, index) {
		    params['order[' + index + '].column'] = order.column;
		    params['order[' + index + '].dir'] = order.dir;
		});
	  	delete params.order;
	
		params['search.regex'] = params.search.regex;
		params['search.value'] = params.search.value;
		delete params.search;
		
		return params;
	}
	
	function datetimeMarkup(data) {
		const parsedData = Date.parse(data);
		const datetime = new Date(parsedData);
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
		return `
			<div class="btn-group">
		  		<button class="btn btn-primary btn-sm dropdown-toggle" type="button" data-bs-toggle="dropdown" data-bs-auto-close="outside" aria-expanded="false">
		    		Action
		  		</button>
		  		<ul class="dropdown-menu">
		    		<li><a class="dropdown-item" href="#"><small><i class="fa-solid fa-eye"></i> View</small></a></li>
		    		<li><a class="dropdown-item" href="#"><small><i class="fa-solid fa-eraser"></i> Cancel</small></a></li>
		  		</ul>
			</div>
		`;
	}
});