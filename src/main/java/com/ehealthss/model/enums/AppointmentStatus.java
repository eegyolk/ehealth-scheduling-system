package com.ehealthss.model.enums;

public enum AppointmentStatus {
	PENDING("Pending"), BOOKED("Booked"), ARRIVED("Arrived"), FULFILLED("Fulfilled"), CANCELLED("Cancelled"),
	WAITLIST("Waitlist");

	String status;

	private AppointmentStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
