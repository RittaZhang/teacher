package com.vp.model;

import java.util.Date;

public class Education {

	private int id;
	private String graduateInstitutions;
	private String major;
	private  String degree;
	//private int teacher_id;
	private String remarks;
	private Date graduateDate;
	private Teacher teacher;
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id=id;
	}
	public String getGraduateInstitutions()
	{
		return graduateInstitutions;
	}
	public void setGraduateInstitutions(String graduateInstitutions)
	{
		this.graduateInstitutions=graduateInstitutions;
	}
	public String getMajor()
	{
		return major;
	}
	public void setMajor(String major )
	{
		this.major=major;
	}
	public String getDegree()
	{
		return degree;
	}
	public void setDegree(String degree)
	{
		this.degree=degree;
	}
	public String getRemarks()
	{
		return remarks;
	}
	public void setRemarks(String remarks)
	{
		this.remarks=remarks;
	}
	
	public Date getGraduateDate() {
		return graduateDate;
	}
	public void setGraduateDate(Date graduateDate) {
		this.graduateDate = graduateDate;
	}
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	
}
