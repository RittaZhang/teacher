package com.vp.model;

import java.io.Serializable;

public class Department implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String departmentName;
	private String remarks;
	// 在多方定义一个一方的引用
	private Department fatherDept;
	private String departmentLevel;
	private String departmentCode;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Department getFatherDept() {
		return fatherDept;
	}
	public void setFatherDept(Department fatherDept) {
		this.fatherDept = fatherDept;
	}
	public String getDepartmentLevel() {
		return departmentLevel;
	}
	public void setDepartmentLevel(String departmentLevel) {
		this.departmentLevel = departmentLevel;
	}
	public String getDepartmentCode() {
		return departmentCode;
	}
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}
	
	
	
}
