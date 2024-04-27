package com.ehealthss.model;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;

import com.ehealthss.model.enums.AppointmentStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "appointment_activity")
public class AppointmentActivity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "appointment_id")
	@JsonIgnore
	private Appointment appointment;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private User user;

	private String notes;

	@Enumerated(EnumType.STRING)
	private AppointmentStatus status;

	@CreationTimestamp
	private Date createdOn;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "appointmentActivity")
	@JsonIgnore
	private List<AppointmentActivityAlert> appointmentActivityAlerts;

	public AppointmentActivity() {
	}

	public AppointmentActivity(Appointment appointment, User user, String notes, AppointmentStatus status) {
		this.appointment = appointment;
		this.user = user;
		this.notes = notes;
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public AppointmentStatus getStatus() {
		return status;
	}

	public void setStatus(AppointmentStatus status) {
		this.status = status;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public List<AppointmentActivityAlert> getAppointmentActivityAlerts() {
		return appointmentActivityAlerts;
	}

	public void setAppointmentActivityAlerts(List<AppointmentActivityAlert> appointmentActivityAlerts) {
		this.appointmentActivityAlerts = appointmentActivityAlerts;
	}

	@Override
	public String toString() {
		return String.format("AppointmentActivity [id=%s, notes=%s, status=%s, createdOn=%s]", id, notes, status,
				createdOn);
	}

	@Override
	public int hashCode() {
		return Objects.hash(createdOn, id, notes, status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		AppointmentActivity other = (AppointmentActivity) obj;
		return Objects.equals(createdOn, other.createdOn) && id == other.id && Objects.equals(notes, other.notes)
				&& status == other.status;
	}

}
