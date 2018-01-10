package com.vp.model;

import java.util.Date;

public class Attendance {

	private int id;
	private Date attendanceDate;
	private String attendanceStatus;
	private int teacher_id;
	private String remarks;
	//private Teacher teacher;

	public int getId()
	{
		return id;
	}
	public void setId( int id)
	{
		this.id=id;
	}
	public Date getAttendanceDate()
	{
		return attendanceDate;
	}
	public void setAttendanceDate(Date attendanceDate)
	{
		this.attendanceDate=attendanceDate;
	}
	public String getAttendanceStatus()
	{
		return attendanceStatus;
	}
	public void setAttendanceStatus(String attendanceStatus)
	{
		this.attendanceStatus=attendanceStatus;
	}
//	public int getTeacher_id()
//	{
//		return teacher_id;
//	}
//	public void setTeacher_id(int teacher_id)
//	{
//		this.teacher_id=teacher_id;
//	}
	public String getRemarks()
	{
		return remarks;
	}
	public void setRemarks(String remarks)
	{
		this.remarks=remarks;
	}
//	public Teacher getTeacher() {
//		return teacher;
//	}
//	public void setTeacher(Teacher teacher) {
//		this.teacher = teacher;
//	}
	public int getTeacher_id() {
		return teacher_id;
	}
	public void setTeacher_id(int teacher_id) {
		this.teacher_id = teacher_id;
	}
}
