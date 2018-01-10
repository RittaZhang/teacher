package com.vp.dao.Impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.vp.dao.PageBeanDao;
import com.vp.db.MyHibernateSessionFactory;

public class PageBeanDaoImpl implements PageBeanDao{

	@Override
	public List<?> queryByPage(String hql, int offset, int pageSize) {
		// TODO Auto-generated method stub
		Transaction tx = null;
        List<?> list = null;
        try{
        	Session session = MyHibernateSessionFactory.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            Query query = session.createQuery(hql).setFirstResult(offset).setMaxResults(pageSize);
            list = query.list();
            tx.commit();
            return list;
        }catch(Exception ex){
			ex.printStackTrace();
			return null;
		}finally{
			if(tx!=null){
				tx=null;
			}
			//HibernateUtil.closeSession(session);
		}
       
	}

	@Override
	public int getAllRowCount(String hql) {
		// TODO Auto-generated method stub
		Transaction tx = null;
        int allRows = 0;
        try{
        	Session session = MyHibernateSessionFactory.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            Query query = session.createQuery(hql);
            allRows = query.list().size();
            tx.commit();
            return allRows;
        }catch(Exception ex){
			ex.printStackTrace();
			return -1;
		}finally{
			if(tx!=null){
				tx=null;
			}
			//HibernateUtil.closeSession(session);
		}
       
       
	}

}
