package com.ehealthss.bean;

import java.util.Date;

import com.ehealthss.model.User;

public class AppointmentActivityAlertDTO {

	private Integer id;
	private AppointmentActivityDTO appointmentActivity;
	private User sender;
	private User receiver;
	private boolean seen;
	private Date createdOn;
	private Date updatedOn;
	
	public AppointmentActivityAlertDTO() {
	}

	public AppointmentActivityAlertDTO(Integer id, AppointmentActivityDTO appointmentActivity, User sender, User receiver,
			boolean seen, Date createdOn, Date updatedOn) {
		this.id = id;
		this.appointmentActivity = appointmentActivity;
		this.sender = sender;
		this.receiver = receiver;
		this.seen = seen;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public AppointmentActivityDTO getAppointmentActivity() {
		return appointmentActivity;
	}

	public void setAppointmentActivity(AppointmentActivityDTO appointmentActivity) {
		this.appointmentActivity = appointmentActivity;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	public boolean isSeen() {
		return seen;
	}

	public void setSeen(boolean seen) {
		this.seen = seen;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	@Override
	public String toString() {
		return String.format(
				"AppointmentActivityAlertDTO [id=%s, appointmentActivity=%s, sender=%s, receiver=%s, seen=%s, createdOn=%s, updatedOn=%s]",
				id, appointmentActivity, sender, receiver, seen, createdOn, updatedOn);
	}
	
}
