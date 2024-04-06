$().ready(function() {
	
	window.initDataTableAttendanceList = function(date=null) {
		window.tableAttendanceList = $("#tableAttendanceList").DataTable({
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
		    	url: "/attendance/fetch/attendances",
		    	type: "POST",
		    	data: function(data) {
					// Workaround to pass search field and value		
					const selectBy = $("#selectBy").find(":selected").val();
					if (selectBy != "") {
						if (parseInt(selectBy) === 1) { // By Clinic
							data.columns[7].search.value = parseInt(selectBy);
							data.columns[8].search.value = $("#selectByClinic").find(":selected").val();							
						} else if (parseInt(selectBy) === 2) { // By Date
							data.columns[7].search.value = parseInt(selectBy);
							data.columns[8].search.value = $("#textByDate").val();							
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
					searchable: false,
					orderable: false,
					render: function(data, type, row) {
						return `<small>${row.location.name} - ${row.location.address}</small>`;
					}
				},
				{
					data: "date",
					searchable: false,
					render: function(data, type, row) {
						return datetimeMarkup(data);
					}
				},
				{
					data: "inTime",
					searchable: false,
					orderable: false,
					render: function(data, type, row) {
						return `<small>${data}</small>`;
					}
				},
				{
					data: "outTime",
					searchable: false,
					orderable: false,
					render: function(data, type, row) {
						return outTimeMarkup(row);
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
	
	window.initDataTableAttendanceList();
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
				$("#divByClinic").addClass("d-none");
				$("#divByDate").addClass("d-none");
				$("#selectByClinic").val("");
				$("#textByDate").val("");
			} else if (parseInt(ev.target.value) === 1) {
				$("#divByClinic").removeClass("d-none");
				$("#divByDate").addClass("d-none");
				$("#selectByClinic").val("");
			} else if (parseInt(ev.target.value) === 2) {
				$("#divByClinic").addClass("d-none");
				$("#divByDate").removeClass("d-none");
				$("#textByDate").val("");
			}
		});
		
		$("#buttonSearch").click(function(ev) {
			window.tableAttendanceList.destroy();
			window.initDataTableAttendanceList($("#textReferenceNo").val());
		});
	}

	function initDateTimePicker() {
		window.datePicker = new tempusDominus.TempusDominus(document.getElementById("datetimepicker1"), {
			allowInputToggle: true,
			display: { components: { clock: false } },
			localization: { format: 'yyyy-MM-dd' },
			restrictions: { maxDate: new Date() },
  			useCurrent: false
		});
	}
	
	function datetimeMarkup(data) {
		if (data === null) {
			return `-`;
		} else {
			const datetime = new Date(data);
			return `<small>${datetime.toLocaleString().replace(",", "").replace(" 12:00:00 AM", "").replace(/(\:\d{2} AM)/, " AM").replace(/(\:\d{2} PM)/, " PM")}</small>`;	
		}
	}
	
	function outTimeMarkup(row) {
		if (row.outTime === null) {
			const btoaData = window.btoa(JSON.stringify(row));
			return `<a href="#" class="pe-auto" data-bs-toggle="modal" data-bs-target="#clockOut" data-btoa-data="${btoaData}"><i class="fa-solid fa-clock button text-primary fs-4"></i></a>`;
		} else {
			return `<small>${row.outTime}</small>`;
		}
	}
});