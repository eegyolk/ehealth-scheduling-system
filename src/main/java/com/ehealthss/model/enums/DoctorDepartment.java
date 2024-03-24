package com.ehealthss.model.enums;

public enum DoctorDepartment {
	CARDIOLOGIST("Cardiologist"), FAMILY_DOCTOR("Family Doctor"), INTERNIST("Internist"), NEUROLOGIST("Neurologist"),
	ONCOLOGIST("Oncologist"), PEDIATRICIAN("Pediatrician");

	String department;

	private DoctorDepartment(String department) {
		this.department = department;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

}
