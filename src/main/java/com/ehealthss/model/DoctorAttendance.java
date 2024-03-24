package com.ehealthss.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "doctor_attendance")
public class DoctorAttendance {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "doctor_id")
	private Doctor doctor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "location_id")
	private Location location;

	private Date date;
	private String inTime;
	private String outTime;
	private String signature;
	private Date createdOn;
	private Date updatedOn;

	public DoctorAttendance() {
	}

	public DoctorAttendance(Doctor doctor, Location location, Date date, String inTime, String outTime,
			String signature) {
		this.doctor = doctor;
		this.location = location;
		this.date = date;
		this.inTime = inTime;
		this.outTime = outTime;
		this.signature = signature;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getInTime() {
		return inTime;
	}

	public void setInTime(String inTime) {
		this.inTime = inTime;
	}

	public String getOutTime() {
		return outTime;
	}

	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
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
				"DoctorAttendance [id=%s, date=%s, inTime=%s, outTime=%s, signature=%s, createdOn=%s, updatedOn=%s]",
				id, date, inTime, outTime, signature, createdOn, updatedOn);
	}

	@Override
	public int hashCode() {
		return Objects.hash(createdOn, date, id, inTime, outTime, signature, updatedOn);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DoctorAttendance other = (DoctorAttendance) obj;
		return Objects.equals(createdOn, other.createdOn) && Objects.equals(date, other.date) && id == other.id
				&& Objects.equals(inTime, other.inTime) && Objects.equals(outTime, other.outTime)
				&& Objects.equals(signature, other.signature) && Objects.equals(updatedOn, other.updatedOn);
	}

}
