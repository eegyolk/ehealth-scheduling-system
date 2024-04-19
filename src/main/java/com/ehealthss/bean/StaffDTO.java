package com.ehealthss.bean;

import java.util.Date;

import com.ehealthss.model.Location;
import com.ehealthss.model.User;

public class StaffDTO {

	private Integer id;
	private User user;
	private Location location;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private Date createdOn;
	private Date updatedOn;
	
	public StaffDTO() {
	}

	public StaffDTO(Integer id, User user, Location location, String firstName, String lastName, String email,
			String phone, Date createdOn, Date updatedOn) {
		this.id = id;
		this.user = user;
		this.location = location;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
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

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
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
				"StaffDTO [id=%s, user=%s, location=%s, firstName=%s, lastName=%s, email=%s, phone=%s, createdOn=%s, updatedOn=%s]",
				id, user, location, firstName, lastName, email, phone, createdOn, updatedOn);
	}
	
}
