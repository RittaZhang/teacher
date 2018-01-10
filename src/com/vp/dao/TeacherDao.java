package com.vp.dao;

import java.util.List;

import com.vp.model.Teacher;


public interface TeacherDao {
	public List<Teacher> getAllTeacher();
	public boolean isExist(Teacher t);
	public Boolean add(Teacher t);
	public Teacher getOneTeacher(String tid);
	public void updateOneTeacher(Teacher t);
	public List<Teacher> getTeacherByIdlist(List<Integer> tid);
	public List<Teacher> getEduationByCon(String name,String code);
	public Teacher add1(Teacher t);
}
