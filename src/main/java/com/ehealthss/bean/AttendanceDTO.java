package com.ehealthss.bean;

import java.util.Date;

public class AttendanceDTO {
	
	private Date date;
	private String inTime;
	private String outTime;
	private String signature;
	
	public AttendanceDTO() {
	}
	
	public AttendanceDTO(Date date, String inTime, String outTime, String signature) {
		this.date = date;
		this.inTime = inTime;
		this.outTime = outTime;
		this.signature = signature;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getInTime() {
		return inTime;
	}

	public void setInTime(String inTime) {
		this.inTime = inTime;
	}

	public String getOutTime() {
		return outTime;
	}

	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	@Override
	public String toString() {
		return String.format("AttendanceDTO [date=%s, inTime=%s, outTime=%s, signature=%s]", date, inTime, outTime,
				signature);
	}
	
}
