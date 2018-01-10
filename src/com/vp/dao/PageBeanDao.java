package com.vp.dao;

import java.util.List;


public interface PageBeanDao {
	
	public List<?> queryByPage(String hql, int offset, int pageSize);
    
    public int getAllRowCount(String hql);
}
