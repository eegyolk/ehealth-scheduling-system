package com.ehealthss.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.ehealthss.model.enums.DoctorDepartment;

@Entity
@Table(name = "doctor")
public class Doctor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private DoctorDepartment department;
	private Date createdOn;
	private Date updatedOn;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "doctor")
	private List<DoctorSchedule> doctorSchedules;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "doctor")
	private List<DoctorAttendance> doctorAttendances;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "doctor")
	private List<Appointment> appointments;

	public Doctor() {
	}

	public Doctor(User user, String firstName, String lastName, String email, String phone,
			DoctorDepartment department, Date createdOn, Date updatedOn) {
		this.user = user;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.department = department;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
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
				"Doctor [id=%s, user=%s, firstName=%s, lastName=%s, email=%s, phone=%s, department=%s, createdOn=%s, updatedOn=%s]",
				id, user, firstName, lastName, email, phone, department, createdOn, updatedOn);
	}

	@Override
	public int hashCode() {
		return Objects.hash(createdOn, department, email, firstName, id, lastName, phone, updatedOn, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Doctor other = (Doctor) obj;
		return Objects.equals(createdOn, other.createdOn) && department == other.department
				&& Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName) && id == other.id
				&& Objects.equals(lastName, other.lastName) && Objects.equals(phone, other.phone)
				&& Objects.equals(updatedOn, other.updatedOn) && Objects.equals(user, other.user);
	}

}
