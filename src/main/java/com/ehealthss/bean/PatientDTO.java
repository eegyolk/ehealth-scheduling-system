package com.ehealthss.bean;

import java.util.Date;
import java.util.List;

import com.ehealthss.model.Appointment;
import com.ehealthss.model.PatientSetting;
import com.ehealthss.model.User;
import com.ehealthss.model.enums.PatientGender;

public class PatientDTO {

	private Integer id;
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
	private PatientSetting patientSetting;
	private List<Appointment> appointments;
	
	public PatientDTO() {
	}

	public PatientDTO(Integer id, User user, String firstName, String lastName, String email, String phone,
			PatientGender gender, Date birthDate, String address, Date createdOn, Date updatedOn,
			PatientSetting patientSetting, List<Appointment> appointments) {
		this.id = id;
		this.user = user;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.gender = gender;
		this.birthDate = birthDate;
		this.address = address;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.patientSetting = patientSetting;
		this.appointments = appointments;
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
				"PatientDTO [id=%s, user=%s, firstName=%s, lastName=%s, email=%s, phone=%s, gender=%s, birthDate=%s, address=%s, createdOn=%s, updatedOn=%s, patientSetting=%s, appointments=%s]",
				id, user, firstName, lastName, email, phone, gender, birthDate, address, createdOn, updatedOn,
				patientSetting, appointments);
	}
	
}
