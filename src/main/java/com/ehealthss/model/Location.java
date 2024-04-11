package com.ehealthss.model;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "location")
public class Location implements Comparable<Location> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;
	private String email;
	private String phone;
	private String address;
	private Double latitude;
	private Double longitude;
	
	@CreationTimestamp
	private Date createdOn;
	
	@Column(insertable = false)
	@UpdateTimestamp
	private Date updatedOn;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "location")
	@JsonIgnore
	private List<LocationAvailability> locationAvailabilities;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "location")
	@JsonIgnore
	private List<Staff> staff;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "location")
	@JsonIgnore
	private List<DoctorSchedule> doctorSchedules;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "location")
	@JsonIgnore
	private List<DoctorAttendance> doctorAttendances;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "location")
	@JsonIgnore
	private List<Appointment> appointments;

	public Location() {
	}

	public Location(String name, String email, String phone, String address, Double latitude, Double longitude,
			Date createdOn, Date updatedOn) {
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
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

	public List<LocationAvailability> getLocationAvailabilities() {
		return locationAvailabilities;
	}

	public void setLocationAvailabilities(List<LocationAvailability> locationAvailabilities) {
		this.locationAvailabilities = locationAvailabilities;
	}

	public List<Staff> getStaff() {
		return staff;
	}

	public void setStaff(List<Staff> staff) {
		this.staff = staff;
	}

	public List<DoctorSchedule> getDoctorSchedules() {
		return doctorSchedules;
	}

	public void setDoctorSchedules(List<DoctorSchedule> doctorSchedules) {
		this.doctorSchedules = doctorSchedules;
	}
	
	public List<DoctorAttendance> getDoctorAttendances() {
		return doctorAttendances;
	}

	public void setDoctorAttendances(List<DoctorAttendance> doctorAttendances) {
		this.doctorAttendances = doctorAttendances;
	}
	
	public List<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}

	@Override
	public String toString() {
		return String.format(
				"Location [id=%s, name=%s, email=%s, phone=%s, address=%s, latitude=%s, longitude=%s, createdOn=%s, updatedOn=%s]",
				id, name, email, phone, address, latitude, longitude, createdOn, updatedOn);
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, createdOn, email, id, latitude, longitude, name, phone, updatedOn);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		return Objects.equals(address, other.address) && Objects.equals(createdOn, other.createdOn)
				&& Objects.equals(email, other.email) && id == other.id && Objects.equals(latitude, other.latitude)
				&& Objects.equals(longitude, other.longitude) && Objects.equals(name, other.name)
				&& Objects.equals(phone, other.phone) && Objects.equals(updatedOn, other.updatedOn);
	}

	@Override
	public int compareTo(Location o) {
		return o.getId().compareTo(this.getId());
	}

}
