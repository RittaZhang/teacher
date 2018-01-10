package com.vp.dao.Impl;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.vp.dao.LogDao;
import com.vp.db.MyHibernateSessionFactory;
import com.vp.model.Log;

public class LogDaoImpl implements LogDao {

	@Override
	public void addLog(Log log) {
		Transaction tx = null;
		try{
			Session session = MyHibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			session.save(log);
			tx.commit();//提交事务
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if (tx!=null) {
                tx=null;
            }
		}
	}

}
