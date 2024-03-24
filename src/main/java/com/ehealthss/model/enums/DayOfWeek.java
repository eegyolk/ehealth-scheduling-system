package com.ehealthss.model.enums;

public enum DayOfWeek {
	MON("Mon"), TUE("Tue"), WED("Wed"), THU("Thu"), FRI("Fri"), SAT("Sat"), SUN("Sun");

	String dow;

	private DayOfWeek(String dow) {
		this.dow = dow;
	}

	public String getDow() {
		return dow;
	}

	public void setDow(String dow) {
		this.dow = dow;
	}

}
