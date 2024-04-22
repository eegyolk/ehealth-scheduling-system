package com.ehealthss.model;

import java.util.Date;
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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "appointment_activity_alert")
public class AppointmentActivityAlert {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "appointment_activity_id")
	@JsonIgnore
	private AppointmentActivity appointmentActivity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sender_id")
	@JsonIgnore
	private User sender;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "receiver_id")
	@JsonIgnore
	private User receiver;

	private boolean seen;

	@CreationTimestamp
	private Date createdOn;

	@Column(insertable = false)
	@UpdateTimestamp
	private Date updatedOn;

	public AppointmentActivityAlert() {
	}

	public AppointmentActivityAlert(AppointmentActivity appointmentActivity, User sender, User receiver,
			boolean seen, Date createdOn, Date updatedOn) {
		this.appointmentActivity = appointmentActivity;
		this.sender = sender;
		this.receiver = receiver;
		this.seen = seen;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public AppointmentActivity getAppointmentActivity() {
		return appointmentActivity;
	}

	public void setAppointmentActivity(AppointmentActivity appointmentActivity) {
		this.appointmentActivity = appointmentActivity;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	public boolean isSeen() {
		return seen;
	}

	public void setSeen(boolean seen) {
		this.seen = seen;
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
				"AppointmentActivityAlert [id=%s, appointmentActivity=%s, sender=%s, receiver=%s, seen=%s, createdOn=%s, updatedOn=%s]",
				id, appointmentActivity, sender, receiver, seen, createdOn, updatedOn);
	}

	@Override
	public int hashCode() {
		return Objects.hash(createdOn, id, seen, updatedOn);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AppointmentActivityAlert other = (AppointmentActivityAlert) obj;
		return Objects.equals(appointmentActivity, other.appointmentActivity)
				&& Objects.equals(createdOn, other.createdOn) && Objects.equals(id, other.id)
				&& Objects.equals(receiver, other.receiver) && seen == other.seen
				&& Objects.equals(sender, other.sender) && Objects.equals(updatedOn, other.updatedOn);
	}
	
}
