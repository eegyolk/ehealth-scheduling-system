package com.ehealthss.model.enums;

public enum PatientGender {
	MALE("Male"), FEMALE("Female");

	String gender;

	private PatientGender(String gender) {
		this.gender = gender;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

}
