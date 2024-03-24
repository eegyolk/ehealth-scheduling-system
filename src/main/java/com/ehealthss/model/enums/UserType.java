package com.ehealthss.model.enums;

public enum UserType {
	PATIENT("Patient"), DOCTOR("Doctor"), STAFF("Staff");

	String type;

	private UserType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
