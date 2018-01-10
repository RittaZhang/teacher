package com.vp.dao.Impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.vp.dao.ManagerDao;
//import com.vp.db.HibernateUtil;
import com.vp.db.MyHibernateSessionFactory;
import com.vp.model.Manager;




public class ManagerDaoImpl implements ManagerDao{

	@Override
	public boolean managerLogin(Manager m) {
		Transaction tx = null;
		String hql = "";
		Manager manager = new Manager();
		try{
			Session session = MyHibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			hql = "from Manager m where m.account=? and m.password=?";
			Query query = session.createQuery(hql);
			query.setParameter(0, m.getAccount());
			query.setParameter(1, m.getPassword());
			manager = (Manager) query.uniqueResult();
			query = null;
			tx.commit();//提交事务
			if(manager!=null){
				return true;
			}else{
				return false;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}finally{
			if (tx!=null) {
                tx=null;
            }
		}
	}

}
