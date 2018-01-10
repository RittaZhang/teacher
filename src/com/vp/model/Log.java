package com.vp.model;

import java.util.Date;

public class Log {
 
	private int id;
	private int otherId;
	private Date loginDate;
	private String remarks;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOtherId() {
		return otherId;
	}
	public void setOtherId(int otherId) {
		this.otherId = otherId;
	}
	public Date getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
}
