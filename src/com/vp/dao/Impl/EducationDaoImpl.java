package com.vp.dao.Impl;

import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.vp.action.Util;
import com.vp.dao.EducationDao;
import com.vp.db.MyHibernateSessionFactory;
import com.vp.model.Education;
import com.vp.model.Teacher;

public class EducationDaoImpl implements EducationDao {
	@Override
	public Set<Education> getEducationsByTId(Integer id) {
		Transaction tx = null;
		Teacher teacher = new Teacher();
		String hql = "";
		try{
			Session session = MyHibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			hql="from Teacher where id=?";
			Query query = session.createQuery(hql);
			query.setParameter(0, id);
			teacher = (Teacher) query.uniqueResult();
			query = null;
			tx.commit();//提交事务
			if(teacher!=null){
				return teacher.getEducations();
			}else{
				return null;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}finally{
			if (tx!=null) {
                tx=null;
            }
			//HibernateUtil.closeSession(session);
		}
	}

	@Override
	public Education getEducationById(Integer id) {
		Transaction tx = null;
		try{
			Session session = MyHibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			Education education = (Education) session.get(Education.class, id);
			tx.commit();//提交事务
			return education;
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}finally{
			if (tx!=null) {
                tx=null;
            }
		}
	}

	@Override
	public void updateEducation(Education e) {
		Transaction tx = null;
		try{
			Session session = MyHibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			session.update(e);
			
			tx.commit();//提交事务
			Util.addLog(e.getId(), "update a Education");
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if (tx!=null) {
                tx=null;
            }
		}
	}

	@Override
	public void deleteEducation(Integer eid) {
		Transaction tx = null;
		try{
			Session session = MyHibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			Education e =(Education) session.get(Education.class,eid);
			session.delete(e);
			tx.commit();//提交事务
			Util.addLog(eid, "delete a Education");
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if (tx!=null) {
                tx=null;
            }
		}
	}

	@Override
	public void addEducation(List<Education> eList, Integer tid) {
		Transaction tx = null;
		try{
			Session session = MyHibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			Teacher teacher = (Teacher) session.get(Teacher.class,tid);
			for(Education e:eList){
				e.setTeacher(teacher);
				session.save(e);
			}
			
			tx.commit();//提交事务
			Util.addLog(teacher.getId(), "add Educations to teacher");
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if (tx!=null) {
                tx=null;
            }
		}
	}

}
