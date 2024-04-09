package com.ehealthss.model;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.ehealthss.model.enums.PatientGender;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "patient")
public class Patient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private User user;

	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private PatientGender gender;
	private Date birthDate;
	private String address;
	private Date createdOn;
	private Date updatedOn;

	@OneToOne(mappedBy = "patient")
	private PatientSetting patientSetting;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "patient")
	@JsonIgnore
	private List<Appointment> appointments;

	public Patient() {
	}

	public Patient(User user, String firstName, String lastName, String email, String phone, PatientGender gender,
			Date birthDate, String address, PatientSetting patientSetting) {
		this.user = user;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.gender = gender;
		this.birthDate = birthDate;
		this.address = address;
		this.patientSetting = patientSetting;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public PatientGender getGender() {
		return gender;
	}

	public void setGender(PatientGender gender) {
		this.gender = gender;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public PatientSetting getPatientSetting() {
		return patientSetting;
	}

	public void setPatientSetting(PatientSetting patientSetting) {
		this.patientSetting = patientSetting;
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
				"Patient [id=%s, firstName=%s, lastName=%s, email=%s, phone=%s, gender=%s, birthDate=%s, address=%s, createdOn=%s, updatedOn=%s]",
				id, firstName, lastName, email, phone, gender, birthDate, address, createdOn, updatedOn);
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, birthDate, createdOn, email, firstName, gender, id, lastName, phone, updatedOn);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		Patient other = (Patient) obj;
		return Objects.equals(address, other.address) && Objects.equals(birthDate, other.birthDate)
				&& Objects.equals(createdOn, other.createdOn) && Objects.equals(email, other.email)
				&& Objects.equals(firstName, other.firstName) && gender == other.gender && id == other.id
				&& Objects.equals(lastName, other.lastName) && Objects.equals(phone, other.phone)
				&& Objects.equals(updatedOn, other.updatedOn);
	}

}
