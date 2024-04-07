package com.ehealthss.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.Date;
import java.util.Objects;

import com.ehealthss.model.enums.DayOfWeek;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "doctor_schedule")
public class DoctorSchedule implements Comparable<DoctorSchedule> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "doctor_id")
	@JsonBackReference
	private Doctor doctor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "location_id")
	private Location location;

	@Enumerated(EnumType.STRING)
	private DayOfWeek dayOfWeek;
	
	private String startTime;
	private String endTime;
	private int slot;
	private int duration;
	private Date createdOn;
	private Date updatedOn;

	public DoctorSchedule() {
	}

	public DoctorSchedule(Doctor doctor, Location location, DayOfWeek dayOfWeek, String startTime, String endTime,
			int slot, int duration, Date createdOn, Date updatedOn) {
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
				"DoctorSchedule [id=%s, dayOfWeek=%s, startTime=%s, endTime=%s, slot=%s, duration=%s, createdOn=%s, updatedOn=%s]",
				id, dayOfWeek, startTime, endTime, slot, duration, createdOn, updatedOn);
	}

	@Override
	public int hashCode() {
		return Objects.hash(createdOn, dayOfWeek, duration, endTime, id, slot, startTime, updatedOn);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DoctorSchedule other = (DoctorSchedule) obj;
		return Objects.equals(createdOn, other.createdOn) && dayOfWeek == other.dayOfWeek && duration == other.duration
				&& Objects.equals(endTime, other.endTime) && id == other.id && slot == other.slot
				&& Objects.equals(startTime, other.startTime) && Objects.equals(updatedOn, other.updatedOn);
	}

	@Override
	public int compareTo(DoctorSchedule o) {
		return o.getId().compareTo(this.getId());
	}

}
