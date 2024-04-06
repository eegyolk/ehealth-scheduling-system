$().ready(function() {
	modalEvents();
	buttonEvents();
	
	function modalEvents() {
		$("#clockIn").on("show.bs.modal", function(ev) {
			const hasAttendance = $("#hiddenHasAttendance").val();
			
			if (hasAttendance) {
				$("#divFeedback").html(`<div class="p-3 text-primary-emphasis border border-info-subtle bg-info-subtle">You already have clock-in/clock-out for today.</div>`).addClass("p-2");
				$("#textDate").prop("disabled", true);
				$("#selectTime").prop("disabled", true);
				$("#checkConfirmInformation").prop("disabled", true);
				$("#buttonCancel").prop("disabled", true);
				$("#buttonSave").prop("disabled", true);
				
				setTimeout(function() {
					$("#buttonCancel").prop("disabled", false);
					$("#buttonSave").prop("disabled", false);
					$("#buttonCancel").click();
				}, 3000);
			} else {
				const btoaData = $("#hiddenScheduleToday").val();
			
				if (btoaData.length === 0) {
					$("#divFeedback").html(`<div class="p-3 text-primary-emphasis border border-info-subtle bg-info-subtle">You don't have any work schedule for today.</div>`).addClass("p-2");
					$("#textDate").prop("disabled", true);
					$("#selectTime").prop("disabled", true);
					$("#checkConfirmInformation").prop("disabled", true);
					$("#buttonCancel").prop("disabled", true);
					$("#buttonSave").prop("disabled", true);
					
					setTimeout(function() {
						$("#buttonCancel").prop("disabled", false);
						$("#buttonSave").prop("disabled", false);
						$("#buttonCancel").click();
					}, 3000);
				} else {
					$("#selectTime").html("<option selected disabled value=''>...</option>").val("").prop("disabled", false);
					
					const atobData = window.atob(btoaData);
					const data = JSON.parse(atobData);
					
					$("#hiddenScheduleId").val(data.id);
			    
				    for (i = parseInt(data.startTime.split(":")[0]); i <= parseInt(data.endTime.split(":")[0]); i++) {
						const hour = i.toLocaleString('en-US', {minimumIntegerDigits: 2, useGrouping:false});
						if (i === parseInt(data.startTime.split(":")[0]) && 30 === parseInt(data.startTime.split(":")[1])) {
							$("#selectTime").append($("<option></option>").attr("value", `${hour}:30 ${i < 12 ? 'AM' : 'PM'}`).text(`${hour}:30 ${i < 12 ? 'AM' : 'PM'}`));
						} else {
							if (i === parseInt(data.endTime.split(":")[0]) && 30 === parseInt(data.endTime.split(":")[1])) {
								$("#selectTime").append($("<option></option>").attr("value", `${hour}:00 ${i < 12 ? 'AM' : 'PM'}`).text(`${hour}:00 ${i < 12 ? 'AM' : 'PM'}`));
							} else if (i < parseInt(data.endTime.split(":")[0])) {						
								$("#selectTime").append($("<option></option>").attr("value", `${hour}:00 ${i < 12 ? 'AM' : 'PM'}`).text(`${hour}:00 ${i < 12 ? 'AM' : 'PM'}`));
								$("#selectTime").append($("<option></option>").attr("value", `${hour}:30 ${i < 12 ? 'AM' : 'PM'}`).text(`${hour}:30 ${i < 12 ? 'AM' : 'PM'}`));
							}
						}
					}
				}	
			}
		});
		
		$("#clockIn").on("hidden.bs.modal", function(ev) {
			$("#divFeedback").html("").removeClass("p-2");
			$("#selectTime").val("").prop("disabled", true);
			$('#checkConfirmInformation').prop("checked", false);
			$("#buttonCancel").prop("disabled", false);
			$("#buttonSave").prop("disabled", false);
		});
	}
	
	function buttonEvents() {
		$("#buttonBack").click(function(ev) {
			history.back();	
		});
		
		$("#buttonSave").click(function(ev) {
			$("#buttonCancel").prop("disabled", true);
			$("#buttonSave").prop("disabled", true);
					
			const scheduleId = $("#hiddenScheduleId").val();
			const result = fieldsValidation();
			
			if (typeof(result) === 'object') {
				$.ajax({
				  	url: `/attendance/create/${scheduleId}`,
				  	method: "POST",
				  	headers: {
						"Content-Type": "application/json",
						"X-CSRF-TOKEN": $("input[name=_csrf]").val()
					},
				  	data: JSON.stringify(result)
				}).done(function(data) {
					$("#divFeedback").html(`<div class="p-3 text-primary-emphasis border border-success-subtle bg-success-subtle">Your attendance has been created successfully.</div>`).addClass("p-2");
					$("#hiddenHasAttendance").val(true);
					
					setTimeout(function() {
						// We destroy first then re-initialize the data table
						// from /js/attendance/data-table.js file
						window.tableAttendanceList.destroy();
						window.initDataTableAttendanceList();
						
						$("#buttonCancel").prop("disabled", false);
						$("#buttonSave").prop("disabled", false);
						$("#buttonCancel").click();
					}, 3000);
					
				}).fail(function(err) {
					$("#buttonCancel").prop("disabled", false);
					$("#buttonSave").prop("disabled", false);
					
					$("#divFeedback").html(`<div class="p-3 text-primary-emphasis border border-danger-subtle bg-danger-subtle">An error has occured, plase try again later.</div>`).addClass("p-2");
					
					setTimeout(function() {
						$("#divFeedback").html("").removeClass("p-2");
					}, 5000);
				})
			} else {
				$("#buttonCancel").prop("disabled", false);
				$("#buttonSave").prop("disabled", false);
			}
		});
	}
	
	/* Returns Object, otherwise false on errors */
	function fieldsValidation() {
		let hasError = false;
		const fields = {};
		
		const time = $('#selectTime');
		const confirmInformation = $('#checkConfirmInformation');
		
		let feedback = $("#selectTimeFeedback");
		if (time.find(":selected").val() === "") {
			feedback.html("Please choose a time").attr("style", "display: block");
			time.attr("style", "border-color: #dc3545 !important");
			hasError = true;
		} else {
			feedback.html("").removeAttr("style");
			time.removeAttr("style");
			
			const date = new Date($("#textDate").val());
			
			fields['date'] = date.toISOString();
			fields['inTime'] = time.val().trim();
		} 
	
		feedback = $("#checkConfirmInformationFeedback");
		if (confirmInformation.is(":checked") === false ) {
			feedback.html("Please confirm").attr("style", "display: block");
			confirmInformation.attr("style", "border-color: #dc3545 !important");
			hasError = true;
		} else {
			feedback.html("").removeAttr("style");
			confirmInformation.removeAttr("style");
		}
		
		if (hasError) {
			return hasError;
		} else {
			return fields;	
		}
	}
});
