package com.ehealthss.bean;

import java.util.Date;

import com.ehealthss.model.Appointment;
import com.ehealthss.model.User;
import com.ehealthss.model.enums.AppointmentStatus;

public class AppointmentActivityDTO {

	private Integer id;
	private Appointment appointment;
	private User user;
	private String notes;
	private AppointmentStatus status;
	private Date createdOn;
	private int slot;

	public AppointmentActivityDTO() {
	}

	public AppointmentActivityDTO(Integer id, Appointment appointment, User user, String notes,
			AppointmentStatus status, Date createdOn, int slot) {
		this.id = id;
		this.appointment = appointment;
		this.user = user;
		this.notes = notes;
		this.status = status;
		this.createdOn = createdOn;
		this.slot = slot;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public AppointmentStatus getStatus() {
		return status;
	}

	public void setStatus(AppointmentStatus status) {
		this.status = status;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public int getSlot() {
		return slot;
	}

	public void setSlot(int slot) {
		this.slot = slot;
	}

	@Override
	public String toString() {
		return String.format(
				"AppointmentActivityDTO [id=%s, appointment=%s, user=%s, notes=%s, status=%s, createdOn=%s, slot=%s]",
				id, appointment, user, notes, status, createdOn, slot);
	}

}
