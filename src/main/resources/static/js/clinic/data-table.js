$().ready(function() {
	
	window.initDataTableClinicList = function() {
		window.tableClinicList = $("#tableClinicList").DataTable({
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
		    	url: "/clinic/fetch/clinics",
		    	type: "POST",
		    	data: function(data) {
					// Workaround to pass search field and value		
					const selectBy = $("#selectBy").find(":selected").val();
					if (selectBy != "") {
						if (parseInt(selectBy) === 1) { // By Name
							data.columns[7].search.value = parseInt(selectBy);
							data.columns[8].search.value = $("#textName").val().trim().length >= 3 ? $("#textName").val().trim() : "";							
						} else if (parseInt(selectBy) === 2) { // By Address
							data.columns[7].search.value = parseInt(selectBy);
							data.columns[8].search.value = $("#textAddress").val().trim().length >= 3 ? $("#textAddress").val().trim() : "";						
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
					data: "name",
					searchable: false,
					orderable: false,
					render: function(data, type, row) {
						return `<small>${data}</small>`;
					}
				},
				{
					data: "address",
					searchable: false,
					render: function(data, type, row) {
						return `<small>${data}</small>`;
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
						return mapMarkup(row);
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
	
	window.initDataTableClinicList();
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
				$("#divByAddress").addClass("d-none");
				$("#textName").val("");
				$("#textAddress").val("");
			} else if (parseInt(ev.target.value) === 1) {
				$("#divByName").removeClass("d-none");
				$("#divByAddress").addClass("d-none");
				$("#textName").val("");
			} else if (parseInt(ev.target.value) === 2) {
				$("#divByName").addClass("d-none");
				$("#divByAddress").removeClass("d-none");
				$("#textAddress").val("");
			}
		});
		
		$("#buttonSearch").click(function(ev) {
			window.tableClinicList.destroy();
			window.initDataTableClinicList();
		});
	}

	function scheduleMarkup(row) {
		const btoaData = window.btoa(JSON.stringify(row));
		return `
			<div class="text-center">
				<a href="#" class="pe-auto" data-bs-toggle="modal" data-bs-target="#viewAvailability" data-btoa-data="${btoaData}">
					<i class="fa-solid fa-calendar-days button text-primary fs-4"></i>
				</a>
			</div>
		`;
	}
	
	function mapMarkup(row) {
		const btoaData = window.btoa(JSON.stringify(row));
		return `
			<div class="text-center">
				<a href="#" class="pe-auto" data-bs-toggle="modal" data-bs-target="#viewLocation" data-btoa-data="${btoaData}">
					<i class="fa-solid fa-map button text-primary fs-4"></i>
				</a>
			</div>
		`;
	}
	
});