package com.ehealthss.model;

import java.util.Date;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.ehealthss.model.enums.DayOfWeek;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
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

@Entity
@Table(name = "location_availability")
public class LocationAvailability {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "location_id")
	@JsonIgnore
	private Location location;

	@Enumerated(EnumType.STRING)
	private DayOfWeek dayOfWeek;
	
	private boolean allDay;
	private String startTime;
	private String endTime;
	
	@CreationTimestamp
	private Date createdOn;
	
	@Column(insertable = false)
	@UpdateTimestamp
	private Date updatedOn;

	public LocationAvailability() {
	}

	public LocationAvailability(Location location, DayOfWeek dayOfWeek, boolean allDay, String startTime, String endTime) {
		this.location = location;
		this.dayOfWeek = dayOfWeek;
		this.allDay = allDay;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public boolean isAllDay() {
		return allDay;
	}

	public void setAllDay(boolean allDay) {
		this.allDay = allDay;
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
				"LocationAvailability [id=%s, dayOfWeek=%s, allDay=%s, startTime=%s, endTime=%s, createdOn=%s, updatedOn=%s]",
				id, dayOfWeek, allDay, startTime, endTime, createdOn, updatedOn);
	}

	@Override
	public int hashCode() {
		return Objects.hash(allDay, createdOn, dayOfWeek, endTime, id, startTime, updatedOn);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LocationAvailability other = (LocationAvailability) obj;
		return allDay == other.allDay && Objects.equals(createdOn, other.createdOn) && dayOfWeek == other.dayOfWeek
				&& Objects.equals(endTime, other.endTime) && id == other.id
				&& Objects.equals(startTime, other.startTime) && Objects.equals(updatedOn, other.updatedOn);
	}

}
