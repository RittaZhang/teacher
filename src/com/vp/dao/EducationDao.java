package com.vp.dao;

import java.util.List;
import java.util.Set;

import com.vp.model.Education;


public interface EducationDao {
	public Set<Education> getEducationsByTId(Integer id);
	public Education getEducationById(Integer id);
	public void updateEducation(Education e);
	public void deleteEducation(Integer eidList);
	public void addEducation(List<Education> eList,Integer tid);
}
