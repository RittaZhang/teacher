package com.vp.dao.Impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.vp.action.Util;
import com.vp.dao.TeacherDao;
import com.vp.db.MyHibernateSessionFactory;
import com.vp.model.Teacher;
import com.vp.model.Teacher;


public class TeacherDaoImpl implements TeacherDao {

	@Override
	public List<Teacher> getAllTeacher() {
		Transaction tx = null;
		String hql = "";
//		List<Teacher> allTeacher = new ArrayList<Teacher>();
		try{
			Session session = MyHibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			hql = "from Teacher";
			Query query = session.createQuery(hql);
			List<Teacher> allTeacher = query.list();
			query = null;
			tx.commit();//提交事务
			
			
			return allTeacher;
			
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
	public Boolean add(Teacher t) {
		Transaction tx = null;
		try{
			Session session = MyHibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			session.save(t);
			
			tx.commit();//提交事务
			Util.addLog(t.getId(), "add a Teacher ");
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}finally{
			if (tx!=null) {
                tx=null;
            }
		}
	}
	
	@Override
	public Teacher add1(Teacher t) {
		Transaction tx = null;
		try{
			Session session = MyHibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			session.save(t);
			
			tx.commit();//提交事务
			Util.addLog(t.getId(), "add a Teacher ");
			return t;
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}finally{
			if (tx!=null) {
                tx=null;
            }
		}
	}
	
	public boolean isExist(Teacher t) {
		//Session session = HibernateUtil.openSession();
		Transaction tx = null;
		Teacher teacher = new Teacher();
		String hql = "";
		try{
			Session session = MyHibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			hql="from Teacher where code=?";
			Query query = session.createQuery(hql);
			query.setParameter(0, t.getCode());
			teacher = (Teacher) query.uniqueResult();
			query = null;
			tx.commit();//提交事务
			if(teacher!=null){
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
			//HibernateUtil.closeSession(session);
		}
	}
	

	@Override
	public Teacher getOneTeacher(String tid) {
		Transaction tx = null;
		try{
			Session session = MyHibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			Teacher teacher = (Teacher) session.get(Teacher.class, Integer.valueOf(tid));
			tx.commit();//提交事务
			return teacher;
			
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
	public void updateOneTeacher(Teacher t) {
		Transaction tx = null;
		try{
			Session session = MyHibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			session.update(t);
			tx.commit();//提交事务
			Util.addLog(t.getId(), "update a Teacher ");
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if (tx!=null) {
                tx=null;
            }
		}
		
	}
	

	@Override
	public List<Teacher> getTeacherByIdlist(List<Integer> tid) {
		Transaction tx = null;
		String hql = "";
		try{
			Session session = MyHibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			hql = "from Teacher t where ";
			for(Integer i=0;i<tid.size();i++){
				if(i==0){
					hql += "t.id ="+tid.get(i);
				}else{
					hql += "or t.id ="+tid.get(i);
				}
				
			}
			Query query = session.createQuery(hql);
			List<Teacher> teacherList = query.list();
			query = null;
			tx.commit();//提交事务
			return teacherList;
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
	public List<Teacher> getEduationByCon(String name, String code) {
		Transaction tx = null;
		String hql = "";
//		List<Teacher> allTeacher = new ArrayList<Teacher>();
		try{
			Session session = MyHibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			hql = "from Teacher ";
			if(!name.isEmpty()||!code.isEmpty()){
				hql+=" where ";
				if(!name.isEmpty()){
					hql+=" name like '%"+name+"%'";
				}
				if(!name.isEmpty()&&!code.isEmpty()){
					hql+=" and ";
				}
				if(!code.isEmpty()){
					hql+=" code like '%"+code+"%'";
				}
			}
			
			Query query = session.createQuery(hql);
			List<Teacher> allTeacher = query.list();
			query = null;
			tx.commit();//提交事务
			return allTeacher;
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}finally{
			if (tx!=null) {
                tx=null;
            }
		}
	}

	
	

}
