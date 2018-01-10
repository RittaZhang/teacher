package com.vp.dao;

import java.util.List;

import com.vp.model.Department;
import com.vp.model.Teacher;

public interface DepartmentDao {
	 //根据部门编号查询部门资料
	public Department queryDepartmentById(int id);

	//添加部门资料
	public boolean addDepartment(Department t);
	
	//修改部门资料
	public void updateDepartment(Department t);
	
	//删除部门资料
	public boolean deleteDepartment(int id);
	//获取一级部门
	public List<Department> getAllDepartment1();
	//获取二级部门
	public List<Department> getAllDepartment2();
	//判断部门编号是否重复
	public boolean isExist(Department d);
    //通过一级部门查询二级部门id
    public List<Integer> getDepartment2IdByDepart1Id(List<Integer> depart1Id);
    //通过部门id查询教师
    public List<Teacher> getTeacherByDepartId(List<Integer> departId);
}
