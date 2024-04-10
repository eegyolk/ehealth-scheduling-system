package com.ehealthss.bean;

import java.util.Date;
import java.util.List;

import com.ehealthss.model.AppointmentActivity;
import com.ehealthss.model.Doctor;
import com.ehealthss.model.Location;
import com.ehealthss.model.Patient;
import com.ehealthss.model.enums.AppointmentStatus;

public class AppointmentDTO {

	private Integer id;
	private Patient patient;
	private Doctor doctor;
	private Location location;
	private String referenceNo;
	private Date datetime;
	private String description;
	private String reason;
	private AppointmentStatus status;
	private boolean joinWaitlist;
	private int slot;
	private Date createdOn;
	private Date updatedOn;
	private List<AppointmentActivity> appointmentActivities;

	public AppointmentDTO() {
	}

	public AppointmentDTO(Integer id, Patient patient, Doctor doctor, Location location, String referenceNo,
			Date datetime, String description, String reason, AppointmentStatus status, boolean joinWaitlist, int slot,
			Date createdOn, Date updatedOn, List<AppointmentActivity> appointmentActivities) {
		this.id = id;
		this.patient = patient;
		this.doctor = doctor;
		this.location = location;
		this.referenceNo = referenceNo;
		this.datetime = datetime;
		this.description = description;
		this.reason = reason;
		this.status = status;
		this.joinWaitlist = joinWaitlist;
		this.slot = slot;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.appointmentActivities = appointmentActivities;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public AppointmentStatus getStatus() {
		return status;
	}

	public void setStatus(AppointmentStatus status) {
		this.status = status;
	}

	public boolean isJoinWaitlist() {
		return joinWaitlist;
	}

	public void setJoinWaitlist(boolean joinWaitlist) {
		this.joinWaitlist = joinWaitlist;
	}

	public int getSlot() {
		return slot;
	}

	public void setSlot(int slot) {
		this.slot = slot;
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

	public List<AppointmentActivity> getAppointmentActivities() {
		return appointmentActivities;
	}

	public void setAppointmentActivities(List<AppointmentActivity> appointmentActivities) {
		this.appointmentActivities = appointmentActivities;
	}

	@Override
	public String toString() {
		return String.format(
				"AppointmentDTO [id=%s, patient=%s, doctor=%s, location=%s, referenceNo=%s, datetime=%s, description=%s, reason=%s, status=%s, joinWaitlist=%s, slot=%s, createdOn=%s, updatedOn=%s, appointmentActivities=%s]",
				id, patient, doctor, location, referenceNo, datetime, description, reason, status, joinWaitlist, slot,
				createdOn, updatedOn, appointmentActivities);
	}
	
}
