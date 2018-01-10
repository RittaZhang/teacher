package com.vp.dao;

import java.util.List;

import com.vp.model.Selection;


public interface SelectionDao {
	public List<String> getAllValueByName(String groupname);
	public void saveSchool(Selection selection);
	//查询第一种选择项信息
	public List<Selection> queryFirstSelection();
	
	//根据部门编号查询选择项信息
	public Selection querySelectionById(int id);
	
	//添加选择项信息
	public boolean addSelection(Selection t);
	
	//修改选择项信息
	public void updateSelection(Selection t);
	//获取所有分组名
	public List<String> queryAllGroupname();
	//根据分组名检索选择项
	public List<Selection> getSelectionByGroup(String groupname);
	//判断选项值是否存在
	public boolean isExistValue(String s);
	
}
