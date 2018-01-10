package com.vp.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.vp.model.Attendance;

public interface AttendanceDao {
	public Set<Attendance> getAttendancesByTid(Integer id);
	public Attendance getAttendanceById(Integer id);
	public void updateAttendance(Attendance a);
	public void deleteAttendance(Integer aid);
	public void addAttendance(List<Attendance> attList,Integer tid);
	public boolean isExist(Date date);
}
