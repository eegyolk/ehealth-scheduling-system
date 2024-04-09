package com.ehealthss.bean;

import java.util.Date;

import com.ehealthss.model.Doctor;
import com.ehealthss.model.Location;
import com.ehealthss.model.enums.DayOfWeek;

public class DoctorScheduleDTO {

	private Integer id;
	private Doctor doctor;
	private Location location;
	private DayOfWeek dayOfWeek;
	private String startTime;
	private String endTime;
	private int slot;
	private int duration;
	private Date createdOn;
	private Date updatedOn;

	public DoctorScheduleDTO() {
	}

	public DoctorScheduleDTO(Integer id, Doctor doctor, Location location, DayOfWeek dayOfWeek, String startTime, String endTime,
			int slot, int duration, Date createdOn, Date updatedOn) {
		this.id = id;
		this.doctor = doctor;
		this.location = location;
		this.dayOfWeek = dayOfWeek;
		this.startTime = startTime;
		this.endTime = endTime;
		this.slot = slot;
		this.duration = duration;
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

	public DayOfWeek getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(DayOfWeek dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public int getSlot() {
		return slot;
	}

	public void setSlot(int slot) {
		this.slot = slot;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
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
				"DoctorScheduleDTO [id=%s, doctor=%s, location=%s, dayOfWeek=%s, startTime=%s, endTime=%s, slot=%s, duration=%s, createdOn=%s, updatedOn=%s]",
				id, doctor, location, dayOfWeek, startTime, endTime, slot, duration, createdOn, updatedOn);
	}

}
