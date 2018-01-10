package com.vp.action;

import java.util.Date;
import java.util.List;

import com.vp.dao.DepartmentDao;
import com.vp.dao.LogDao;
import com.vp.dao.SelectionDao;
import com.vp.dao.Impl.DepartmentDaoImpl;
import com.vp.dao.Impl.LogDaoImpl;
import com.vp.dao.Impl.SelectionDaoImpl;
import com.vp.model.Department;
import com.vp.model.Log;

public class Util extends SuperAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 获取所有一级部门的数据
	 * @return
	 */
	public List<Department> getAllDepartment1(){
		DepartmentDao department = new DepartmentDaoImpl();
		List<Department> allDepartment1s = department.getAllDepartment1();
		return allDepartment1s;
	}
	
	/**
	 * 获取所有二级部门的数据
	 * @return
	 */
	public List<Department> getAllDepartment2(){
		DepartmentDao department = new DepartmentDaoImpl();
		List<Department> allDepartment2s = department.getAllDepartment2();
		return allDepartment2s;
	}
	
	/**
	 * 通过name获取 对应的selection中的value
	 * @param name selection表字段
	 * @return
	 */
	public List<String> getAllValueByName(String name){
		SelectionDao selection  = new SelectionDaoImpl();
		List<String> allSchool = selection.getAllValueByName(name);
		return allSchool;
	}
	public static void addLog(Integer otherId,String operation){
		LogDao logdao = new LogDaoImpl();
		Date today = new Date();
		//SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		//日期的string类型
		//fmt.format(today); 
		Log log = new Log();
		log.setLoginDate(today);
		log.setOtherId(otherId);
		log.setRemarks(operation);
		logdao.addLog(log);
	}
	/**
	 * 判断字符串是否是空 或者null
	 * @param value
	 * @return
	 */
	public static boolean isEmpty(String value) {
	    if ( value == null || value.length() == 0 ){
	        return true;
	    }else{
	        return false;
	    }
	}
	
}
