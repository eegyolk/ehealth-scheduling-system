$().ready(function() {
	modalEvents();
	buttonEvents();
	
	function modalEvents() {
		$("#clockOut").on("show.bs.modal", function(ev) {
			const btoaScheduleTodayData = $("#hiddenScheduleTodayCO").val();
			
			if (btoaScheduleTodayData.length === 0) {
				$("#divFeedbackCO").html(`<div class="p-3 text-primary-emphasis border border-info-subtle bg-info-subtle">You don't have any work schedule for today.</div>`).addClass("p-2");
				$("#textDateCO").prop("disabled", true);
				$("#textInTimeCO").prop("disabled", true);
				$("#selectTimeCO").prop("disabled", true);
				$("#checkConfirmInformationCO").prop("disabled", true);
				$("#buttonCancelCO").prop("disabled", true);
				$("#buttonSaveCO").prop("disabled", true);
				
				setTimeout(function() {
					$("#buttonCancelCO").prop("disabled", false);
					$("#buttonSaveCO").prop("disabled", false);
					$("#buttonCancelCO").click();
				}, 3000);
			} else {
				// Set the attendance data
				const btoaData = $(ev.relatedTarget).data("btoa-data");
				const atobData = window.atob(btoaData);
				const data = JSON.parse(atobData);
				
				$("#hiddenIdCO").val(data.id);
				$("#textInTimeCO").val(data.inTime);
				
				// Set the schedule data
				$("#selectTimeCO").html("<option selected disabled value=''>...</option>").val("").prop("disabled", false);
				
				const atobScheduleTodayData = window.atob(btoaScheduleTodayData);
				const scheduleTodayData = JSON.parse(atobScheduleTodayData);
			    
			    for (i = parseInt(scheduleTodayData.startTime.split(":")[0]); i <= parseInt(scheduleTodayData.endTime.split(":")[0]); i++) {
					const hour = i.toLocaleString('en-US', {minimumIntegerDigits: 2, useGrouping:false});
					if (i === parseInt(scheduleTodayData.startTime.split(":")[0]) && 30 === parseInt(scheduleTodayData.startTime.split(":")[1])) {
						$("#selectTimeCO").append($("<option></option>").attr("value", `${hour}:30 ${i < 12 ? 'AM' : 'PM'}`).text(`${hour}:30 ${i < 12 ? 'AM' : 'PM'}`));
					} else {
						if (i === parseInt(scheduleTodayData.endTime.split(":")[0]) && 30 === parseInt(scheduleTodayData.endTime.split(":")[1])) {
							$("#selectTimeCO").append($("<option></option>").attr("value", `${hour}:00 ${i < 12 ? 'AM' : 'PM'}`).text(`${hour}:00 ${i < 12 ? 'AM' : 'PM'}`));
						} else if (i < parseInt(scheduleTodayData.endTime.split(":")[0])) {
							console.log(3, `${hour}:00 ${i < 12 ? 'AM' : 'PM'}`, `${hour}:30 ${i < 12 ? 'AM' : 'PM'}`);
							$("#selectTimeCO").append($("<option></option>").attr("value", `${hour}:00 ${i < 12 ? 'AM' : 'PM'}`).text(`${hour}:00 ${i < 12 ? 'AM' : 'PM'}`));
							$("#selectTimeCO").append($("<option></option>").attr("value", `${hour}:30 ${i < 12 ? 'AM' : 'PM'}`).text(`${hour}:30 ${i < 12 ? 'AM' : 'PM'}`));
						}	
					}					
				}
			}
		});
		
		$("#clockOut").on("hidden.bs.modal", function(ev) {
			$("#divFeedbackCO").html("").removeClass("p-2");
			$("#hiddenIdCO").val("");
			$("#textInTimeCO").val("");
			$("#selectTimeCO").val("").prop("disabled", true);
			$('#checkConfirmInformationCO').prop("checked", false);
			$("#buttonCancelCO").prop("disabled", false);
			$("#buttonSaveCO").prop("disabled", false);
		});
	}
	
	function buttonEvents() {
		$("#buttonBack").click(function(ev) {
			history.back();	
		});
		
		$("#buttonSaveCO").click(function(ev) {
			$("#buttonCancelCO").prop("disabled", true);
			$("#buttonSaveCO").prop("disabled", true);

			const attendanceId = $("#hiddenIdCO").val();
			const result = fieldsValidation();
			
			if (typeof(result) === 'object') {
				$.ajax({
				  	url: `/attendance/update/${attendanceId}`,
				  	method: "POST",
				  	headers: {
						"Content-Type": "application/json",
						"X-CSRF-TOKEN": $("input[name=_csrf]").val()
					},
				  	data: JSON.stringify(result)
				}).done(function(data) {
					$("#divFeedbackCO").html(`<div class="p-3 text-primary-emphasis border border-success-subtle bg-success-subtle">Your attendance has been updated successfully.</div>`).addClass("p-2");
					
					setTimeout(function() {
						// We destroy first then re-initialize the data table
						// from /js/attendance/data-table.js file
						window.tableAttendanceList.destroy();
						window.initDataTableAttendanceList();
						
						$("#buttonCancelCO").prop("disabled", false);
						$("#buttonSaveCO").prop("disabled", false);
						$("#buttonCancelCO").click();
					}, 3000);
					
				}).fail(function(err) {
					$("#buttonCancelCO").prop("disabled", false);
					$("#buttonSaveCO").prop("disabled", false);
					
					$("#divFeedbackCO").html(`<div class="p-3 text-primary-emphasis border border-danger-subtle bg-danger-subtle">An error has occured, plase try again later.</div>`).addClass("p-2");
					
					setTimeout(function() {
						$("#divFeedbackCO").html("").removeClass("p-2");
					}, 5000);
				})
			} else {
				$("#buttonCancelCO").prop("disabled", false);
				$("#buttonSaveCO").prop("disabled", false);
			}
		});
	}
	
	/* Returns Object, otherwise false on errors */
	function fieldsValidation() {
		let hasError = false;
		const fields = {};
		
		const time = $('#selectTimeCO');
		const confirmInformation = $('#checkConfirmInformationCO');
		
		let feedback = $("#selectTimeFeedbackCO");
		if (time.find(":selected").val() === "") {
			feedback.html("Please choose a time").attr("style", "display: block");
			time.attr("style", "border-color: #dc3545 !important");
			hasError = true;
		} else {
			feedback.html("").removeAttr("style");
			time.removeAttr("style");
			fields['outTime'] = time.val().trim();
		} 
	
		feedback = $("#checkConfirmInformationFeedbackCO");
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
