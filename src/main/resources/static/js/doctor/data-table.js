$().ready(function() {
	
	window.initDataTableDoctorList = function() {
		window.tableDoctorList = $("#tableDoctorList").DataTable({
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
		    	url: "/doctor/fetch/doctors",
		    	type: "POST",
		    	data: function(data) {
					// Workaround to pass search field and value		
					const selectBy = $("#selectBy").find(":selected").val();
					if (selectBy != "") {
						if (parseInt(selectBy) === 1) { // By Name
							data.columns[7].search.value = parseInt(selectBy);
							data.columns[8].search.value = $("#textName").val().trim().length >= 3 ? $("#textName").val().trim() : "";							
						} else if (parseInt(selectBy) === 2) { // By Department
							data.columns[7].search.value = parseInt(selectBy);
							data.columns[8].search.value = $("#selectDepartment").find(":selected").val();						
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
						return `<small>Dr. ${row.firstName} ${row.lastName}</small>`;
					}
				},
				{
					data: "department",
					searchable: false,
					render: function(data, type, row) {
						return  departmentMarkup(data);
					}
				},
				{
					data: "email",
					searchable: false,
					orderable: false,
					render: function(data, type, row) {
						return `<small>${data}</small>`;
					}
				},
				{
					data: "phone",
					searchable: false,
					orderable: false,
					render: function(data, type, row) {
						return `<small>${data}</small>`;
					}
				},
				{
					searchable: false,
					orderable: false,
					render: function(data, type, row) {
						return scheduleMarkup(row);
					}
				},
				{
					searchable: false,
					orderable: false,
					render: function(data, type, row) {
						return todayMarkup(row);
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
	
	window.initDataTableDoctorList();
	initToolTip();
	initSearch();

	
	function initToolTip() {
		const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]')
		const tooltipList = [...tooltipTriggerList].map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl))	
	}
	
	function initSearch() {
		$("#selectBy").change(function(ev) {
			if (ev.target.value === "") {
				$("#divByName").addClass("d-none");
				$("#divByDepartment").addClass("d-none");
				$("#textName").val("");
				$("#selectDepartment").val("");
			} else if (parseInt(ev.target.value) === 1) {
				$("#divByName").removeClass("d-none");
				$("#divByDepartment").addClass("d-none");
				$("#textName").val("");
			} else if (parseInt(ev.target.value) === 2) {
				$("#divByName").addClass("d-none");
				$("#divByDepartment").removeClass("d-none");
				$("#selectDepartment").val("");
			}
		});
		
		$("#buttonSearch").click(function(ev) {
			window.tableDoctorList.destroy();
			window.initDataTableDoctorList();
		});
	}
	
	function departmentMarkup(data) {
		const departmentParts = data.toLowerCase().split("_");
			let department = "";
			if (departmentParts.length === 1) {
				department = departmentParts[0].charAt(0).toUpperCase() + departmentParts[0].slice(1);
			} else if (departmentParts.length === 2) {
				department = `${departmentParts[0].charAt(0).toUpperCase() + departmentParts[0].slice(1)} ${departmentParts[1].charAt(0).toUpperCase() + departmentParts[1].slice(1)}`;
			}
			return `<small>${department}</small>`;
	}
	
	function scheduleMarkup(row) {
		const btoaData = window.btoa(JSON.stringify(row));
		return `
			<div class="text-center">
				<a href="#" class="pe-auto" data-bs-toggle="modal" data-bs-target="#viewSchedule" data-btoa-data="${btoaData}">
					<i class="fa-solid fa-calendar-days button text-primary fs-4"></i>
				</a>
			</div>
		`;
	}
	
	function todayMarkup(row) {
		const attendances = row.doctorAttendances;
		if (attendances.length > 0) {
			if (attendances[0].inTime != null && (attendances[0].outTime === null || attendances[0].outTime.length === 0)) {
				return `
					<div class="text-center">
						<span class="badge text-bg-success">Doctor is IN</span> @ <small>${attendances[0].location.name}</small>
					</div>
				`;	
			} else {
				return `
					<div class="text-center">
						<span class="badge text-bg-danger">Doctor is OUT</span> @ <small>${attendances[0].location.name}</small>
					</div>
				`;
			}
		}
		return `<div class="text-center"><small>-</small></div>`;
	}
});