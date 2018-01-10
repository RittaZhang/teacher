package com.vp.dao.Impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.vp.action.Util;
import com.vp.dao.AttendanceDao;
import com.vp.db.MyHibernateSessionFactory;
import com.vp.model.Attendance;
import com.vp.model.Education;
import com.vp.model.Teacher;

public class AttendanceDaoImpl implements AttendanceDao{

	@Override
	public Set<Attendance> getAttendancesByTid(Integer id) {
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
				return teacher.getAttendances();
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
	public Attendance getAttendanceById(Integer id) {
		// TODO Auto-generated method stub
		Transaction tx = null;
		try{
			Session session = MyHibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			Attendance attendance = (Attendance) session.get(Attendance.class, id);
			tx.commit();//提交事务
			return attendance;
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
	public void updateAttendance(Attendance a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAttendance(Integer aid) {
		Transaction tx = null;
		try{
			Session session = MyHibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			Attendance att =(Attendance) session.get(Attendance.class,aid);
			session.delete(att);
			tx.commit();//提交事务
			Util.addLog(aid, "delete a Education");
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if (tx!=null) {
                tx=null;
            }
		}
	}

	@Override
	public void addAttendance(List<Attendance> attList, Integer tid) {
		Transaction tx = null;
		try{
			Session session = MyHibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			Teacher teacher = (Teacher) session.get(Teacher.class,tid);
			for(Attendance a:attList){
				a.setTeacher_id(teacher.getId());
				session.save(a);
			}
			tx.commit();//提交事务
			Util.addLog(teacher.getId(), "add Attendance to teacher");
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if (tx!=null) {
                tx=null;
            }
		}
		
	}

	@Override
	public boolean isExist(Date date) {
		//Session session = HibernateUtil.openSession();
		Transaction tx = null;
		Attendance att = new Attendance();
		String hql = "";
		try{
			Session session = MyHibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			hql="from Attendance where attendanceDate=?";
			Query query = session.createQuery(hql);
			query.setParameter(0, date);
			att = (Attendance) query.uniqueResult();
			query = null;
			tx.commit();//提交事务
			if(att!=null){
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
