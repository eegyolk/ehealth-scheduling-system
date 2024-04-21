package com.ehealthss.bean;

import java.util.Date;

import com.ehealthss.model.enums.AppointmentStatus;

public class CalendarEventRequestDTO {

	private int doctorId;
	private Date startDate;
	private Date endDate;
	private AppointmentStatus status;

	public CalendarEventRequestDTO() {
	}

	public CalendarEventRequestDTO(int doctorId, Date startDate, Date endDate, AppointmentStatus status) {
		this.doctorId = doctorId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
	}

	public int getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public AppointmentStatus getStatus() {
		return status;
	}

	public void setStatus(AppointmentStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return String.format("CalendarEventRequestDTO [doctorId=%s, startDate=%s, endDate=%s, status=%s]", doctorId,
				startDate, endDate, status);
	}

}
