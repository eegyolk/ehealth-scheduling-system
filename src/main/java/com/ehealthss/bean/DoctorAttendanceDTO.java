package com.ehealthss.bean;

import java.util.Date;

import com.ehealthss.model.Doctor;
import com.ehealthss.model.Location;

public class DoctorAttendanceDTO {

	private Integer id;
	private Doctor doctor;
	private Location location;
	private Date date;
	private String inTime;
	private String outTime;
	private String signature;
	private Date createdOn;
	private Date updatedOn;

	public DoctorAttendanceDTO() {
	}

	public DoctorAttendanceDTO(Integer id, Doctor doctor, Location location, Date date, String inTime, String outTime,
			String signature, Date createdOn, Date updatedOn) {
		this.id = id;
		this.doctor = doctor;
		this.location = location;
		this.date = date;
		this.inTime = inTime;
		this.outTime = outTime;
		this.signature = signature;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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
				"DoctorAttendanceDTO [id=%s, doctor=%s, location=%s, date=%s, inTime=%s, outTime=%s, signature=%s, createdOn=%s, updatedOn=%s]",
				id, doctor, location, date, inTime, outTime, signature, createdOn, updatedOn);
	}
	
}
