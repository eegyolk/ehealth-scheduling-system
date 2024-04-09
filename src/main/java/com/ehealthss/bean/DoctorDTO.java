package com.ehealthss.bean;

import java.util.Date;
import java.util.List;

import com.ehealthss.model.Appointment;
import com.ehealthss.model.DoctorSchedule;
import com.ehealthss.model.User;
import com.ehealthss.model.enums.DoctorDepartment;

public class DoctorDTO {

	private Integer id;
	private User user;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private DoctorDepartment department;
	private Date createdOn;
	private Date updatedOn;
	private List<DoctorSchedule> doctorSchedules;
	private List<DoctorAttendanceDTO> doctorAttendances;
	private List<Appointment> appointments;

	public DoctorDTO() {
	}

	public DoctorDTO(Integer id, User user, String firstName, String lastName, String email, String phone,
			DoctorDepartment department, Date createdOn, Date updatedOn, List<DoctorSchedule> doctorSchedules,
			List<DoctorAttendanceDTO> doctorAttendances, List<Appointment> appointments) {
		this.id = id;
		this.user = user;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.department = department;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.doctorSchedules = doctorSchedules;
		this.doctorAttendances = doctorAttendances;
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

	public DoctorDepartment getDepartment() {
		return department;
	}

	public void setDepartment(DoctorDepartment department) {
		this.department = department;
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

	public List<DoctorSchedule> getDoctorSchedules() {
		return doctorSchedules;
	}

	public void setDoctorSchedules(List<DoctorSchedule> doctorSchedules) {
		this.doctorSchedules = doctorSchedules;
	}

	public List<DoctorAttendanceDTO> getDoctorAttendances() {
		return doctorAttendances;
	}

	public void setDoctorAttendances(List<DoctorAttendanceDTO> doctorAttendances) {
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
				"DoctorDTO [id=%s, user=%s, firstName=%s, lastName=%s, email=%s, phone=%s, department=%s, createdOn=%s, updatedOn=%s, doctorSchedules=%s, doctorAttendances=%s, appointments=%s]",
				id, user, firstName, lastName, email, phone, department, createdOn, updatedOn, doctorSchedules,
				doctorAttendances, appointments);
	}

}
