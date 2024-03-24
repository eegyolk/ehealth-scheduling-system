package com.ehealthss.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.ehealthss.model.enums.AppointmentStatus;

@Entity
@Table(name = "appointment")
public class Appointment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "patient_id")
	private Patient patient;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "doctor_id")
	private Doctor doctor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "location_id")
	private Location location;

	private String referenceNo;
	private Date datetime;
	private String description;
	private String reason;
	private AppointmentStatus status;
	private int slot;
	private Date createdOn;
	private Date updatedOn;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "appointment")
	private List<AppointmentActivity> appointmentActivities;

	public Appointment() {
	}

	public Appointment(Patient patient, Doctor doctor, Location location, String referenceNo, Date datetime,
			String description, String reason, AppointmentStatus status, int slot) {
		this.patient = patient;
		this.doctor = doctor;
		this.location = location;
		this.referenceNo = referenceNo;
		this.datetime = datetime;
		this.description = description;
		this.reason = reason;
		this.status = status;
		this.slot = slot;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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
				"Appointment [id=%s, referenceNo=%s, datetime=%s, description=%s, reason=%s, status=%s, slot=%s, createdOn=%s, updatedOn=%s]",
				id, referenceNo, datetime, description, reason, status, slot, createdOn, updatedOn);
	}

	@Override
	public int hashCode() {
		return Objects.hash(createdOn, datetime, description, id, reason, referenceNo, slot, status, updatedOn);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Appointment other = (Appointment) obj;
		return Objects.equals(createdOn, other.createdOn) && Objects.equals(datetime, other.datetime)
				&& Objects.equals(description, other.description) && id == other.id
				&& Objects.equals(reason, other.reason) && Objects.equals(referenceNo, other.referenceNo)
				&& slot == other.slot && status == other.status && Objects.equals(updatedOn, other.updatedOn);
	}

}
