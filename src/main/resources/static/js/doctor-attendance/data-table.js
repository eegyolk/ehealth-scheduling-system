$().ready(function() {
	
	window.initDataTableDoctorAttendanceList = function() {
		window.tableDoctorAttendanceList = $("#tableDoctorAttendanceList").DataTable({
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
		    	url: "/doctor-attendance/fetch/attendances",
		    	type: "POST",
		    	data: function(data) {
					// Workaround to pass search field and value		
					const selectBy = $("#selectBy").find(":selected").val();
					if (selectBy != "") {
						if (parseInt(selectBy) === 1) { // By Name
							data.columns[8].search.value = parseInt(selectBy);
							data.columns[9].search.value = $("#textName").val().trim().length >= 3 ? $("#textName").val().trim() : "";							
						} else if (parseInt(selectBy) === 2) { // By Date
							data.columns[8].search.value = parseInt(selectBy);
							data.columns[9].search.value = $("#textDate").val();							
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
						return `<small>Dr. ${row.doctor.firstName} ${row.doctor.lastName}</small>`;
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
						return `<small>${data ? data : '-'}</small>`;
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
    		]
		});
	};
	
	window.initDataTableDoctorAttendanceList();
	initSearch();
	initDateTimePicker();
	
	function initSearch() {
		$("#selectBy").change(function(ev) {
			if (ev.target.value === "") {
				$("#divByName").addClass("d-none");
				$("#divByDate").addClass("d-none");
				$("#textName").val("");
				$("#textDate").val("");
			} else if (parseInt(ev.target.value) === 1) {
				$("#divByName").removeClass("d-none");
				$("#divByDate").addClass("d-none");
				$("#textName").val("");
			} else if (parseInt(ev.target.value) === 2) {
				$("#divByName").addClass("d-none");
				$("#divByDate").removeClass("d-none");
				$("#textDate").val("");
			}
		});
		
		$("#buttonSearch").click(function(ev) {
			window.tableDoctorAttendanceList.destroy();
			window.initDataTableDoctorAttendanceList();
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
	
});