package com.ehealthss.model;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.ehealthss.model.enums.UserType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String username;
	private String password;

	@Enumerated(EnumType.STRING)
	private UserType type;

	@CreationTimestamp
	private Date createdOn;
	
	@Column(insertable = false)
	@UpdateTimestamp
	private Date updatedOn;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
	private Staff staff;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
	private Patient patient;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
	private Doctor doctor;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	@JsonIgnore
	private List<AppointmentActivity> appointmentActivities;

	public User() {
	}

	public User(String username, String password, UserType type, Staff staff, Patient patient, Doctor doctor) {
		this.username = username;
		this.password = password;
		this.type = type;
		this.staff = staff;
		this.patient = patient;
		this.doctor = doctor;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
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

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
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

	public List<AppointmentActivity> getAppointmentActivities() {
		return appointmentActivities;
	}

	public void setAppointmentActivities(List<AppointmentActivity> appointmentActivities) {
		this.appointmentActivities = appointmentActivities;
	}

	@Override
	public String toString() {
		return String.format("User [id=%s, username=%s, password=%s, type=%s, createdOn=%s, updatedOn=%s]", id,
				username, password, type, createdOn, updatedOn);
	}

	@Override
	public int hashCode() {
		return Objects.hash(createdOn, id, password, type, updatedOn, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		User other = (User) obj;
		return Objects.equals(createdOn, other.createdOn) && id == other.id && Objects.equals(password, other.password)
				&& type == other.type && Objects.equals(updatedOn, other.updatedOn)
				&& Objects.equals(username, other.username);
	}

}
