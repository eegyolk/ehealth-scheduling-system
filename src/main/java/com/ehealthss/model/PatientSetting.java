package com.ehealthss.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "patient_setting")
public class PatientSetting {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "patient_id")
	private Patient patient;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "doctor_id")
	private Doctor preferredDoctor;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "location_id")
	private Location preferredLocation;

	private String preferredTime;
	private Date createdOn;
	private Date updatedOn;

	public PatientSetting() {
	}

	public PatientSetting(Patient patient, Doctor preferredDoctor, Location preferredLocation, String preferredTime) {
		this.patient = patient;
		this.preferredDoctor = preferredDoctor;
		this.preferredLocation = preferredLocation;
		this.preferredTime = preferredTime;
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
		return String.format("PatientSetting [id=%s, preferredTime=%s, createdOn=%s, updatedOn=%s]", id, preferredTime,
				createdOn, updatedOn);
	}

	@Override
	public int hashCode() {
		return Objects.hash(createdOn, id, preferredTime, updatedOn);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PatientSetting other = (PatientSetting) obj;
		return Objects.equals(createdOn, other.createdOn) && id == other.id
				&& Objects.equals(preferredTime, other.preferredTime) && Objects.equals(updatedOn, other.updatedOn);
	}

}
