package com.ehealthss.bean;

import java.util.Date;

import com.ehealthss.model.Doctor;
import com.ehealthss.model.Location;
import com.ehealthss.model.Patient;

public class PatientSettingDTO {

	private Integer id;
	private Patient patient;
	private Doctor preferredDoctor;
	private Location preferredLocation;
	private String preferredTime;
	private Date createdOn;
	private Date updatedOn;
	
	public PatientSettingDTO() {
	}

	public PatientSettingDTO(Integer id, Patient patient, Doctor preferredDoctor, Location preferredLocation,
			String preferredTime, Date createdOn, Date updatedOn) {
		this.id = id;
		this.patient = patient;
		this.preferredDoctor = preferredDoctor;
		this.preferredLocation = preferredLocation;
		this.preferredTime = preferredTime;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
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

	public Doctor getPreferredDoctor() {
		return preferredDoctor;
	}

	public void setPreferredDoctor(Doctor preferredDoctor) {
		this.preferredDoctor = preferredDoctor;
	}

	public Location getPreferredLocation() {
		return preferredLocation;
	}

	public void setPreferredLocation(Location preferredLocation) {
		this.preferredLocation = preferredLocation;
	}

	public String getPreferredTime() {
		return preferredTime;
	}

	public void setPreferredTime(String preferredTime) {
		this.preferredTime = preferredTime;
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
				"PatientSettingDTO [id=%s, patient=%s, preferredDoctor=%s, preferredLocation=%s, preferredTime=%s, createdOn=%s, updatedOn=%s]",
				id, patient, preferredDoctor, preferredLocation, preferredTime, createdOn, updatedOn);
	}

}
