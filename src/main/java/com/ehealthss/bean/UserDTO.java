package com.ehealthss.bean;

import java.util.Date;
import java.util.List;

import com.ehealthss.model.AppointmentActivity;
import com.ehealthss.model.Doctor;
import com.ehealthss.model.Patient;
import com.ehealthss.model.Staff;
import com.ehealthss.model.enums.UserType;
 
public class UserDTO {

	private Integer id;
	private String username;
	private String password;
	private String oldPassword;
	private String newPassword;
	private UserType type;
	private Date createdOn;
	private Date updatedOn;
	private Staff staff;
	private Patient patient;
	private Doctor doctor;
	private List<AppointmentActivity> appointmentActivities;
	
	public UserDTO() {
	}

	public UserDTO(Integer id, String username, String password, String oldPassword, String newPassword, UserType type,
			Date createdOn, Date updatedOn, Staff staff, Patient patient, Doctor doctor,
			List<AppointmentActivity> appointmentActivities) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
		this.type = type;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.staff = staff;
		this.patient = patient;
		this.doctor = doctor;
		this.appointmentActivities = appointmentActivities;
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

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
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
		return String.format(
				"UserDTO [id=%s, username=%s, password=%s, oldPassword=%s, newPassword=%s, type=%s, createdOn=%s, updatedOn=%s, staff=%s, patient=%s, doctor=%s, appointmentActivities=%s]",
				id, username, password, oldPassword, newPassword, type, createdOn, updatedOn, staff, patient, doctor,
				appointmentActivities);
	}
	
}
