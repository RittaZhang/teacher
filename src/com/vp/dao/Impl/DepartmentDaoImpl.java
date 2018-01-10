package com.vp.dao.Impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.vp.action.Util;
import com.vp.dao.DepartmentDao;
//import com.vp.db.HibernateUtil;
import com.vp.db.MyHibernateSessionFactory;
import com.vp.model.Department;
import com.vp.model.Teacher;

public class DepartmentDaoImpl implements DepartmentDao{
	@Override
	public Department queryDepartmentById(int id) {
		// TODO Auto-generated method stub
		//Session session = HibernateUtil.openSession();
		Transaction tx = null;
		try{
			Session session = MyHibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			Department dept = (Department) session.get(Department.class, id);
			tx.commit();
			return dept;
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
	public boolean addDepartment(Department t) {
		// TODO Auto-generated method stub
		//Session session = HibernateUtil.openSession();
		Transaction tx = null;
		try{
			Session session = MyHibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			session.save(t);
			tx.commit();
			Util.addLog(t.getId(), "add a Department");
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}finally{
			if(tx!=null){
				tx=null;
			}
			//HibernateUtil.closeSession(session);
		}
	}

	@Override
	public void updateDepartment(Department t) {
		// TODO Auto-generated method stub
		//Session session = HibernateUtil.openSession();
		Transaction tx = null;
		try{
			Session session = MyHibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			session.update(t);
			tx.commit();
			Util.addLog(t.getId(), "update a Department");
		}catch(Exception ex){
			ex.printStackTrace();
			//tx.commit();
		}finally{
			if(tx!=null){
				tx=null;
			}
			//HibernateUtil.closeSession(session);
		}
	}

	@Override
	public boolean deleteDepartment(int id) {
		// TODO Auto-generated method stub
		//Session session = HibernateUtil.openSession();
		Transaction tx = null;
		try{
			Session session = MyHibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			Department d =(Department) session.get(Department.class,id);
			Integer did = d.getId();
			session.delete(d);
			tx.commit();
			Util.addLog(did, "delete a Department");
			
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
			tx.commit();
			return false;
		}finally{
			if(tx!=null){
				tx=null;
			}
			//HibernateUtil.closeSession(session);
		}
	}

	@Override
	public List<Department> getAllDepartment1() {
		//Session session = HibernateUtil.openSession();
		Transaction tx = null;
		String hql = "";
		try{
			Session session = MyHibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			hql = "from Department d where d.fatherDept = null";
			Query query = session.createQuery(hql);
			List<Department> allDepartment = query.list();
			query = null;
			tx.commit();//提交事务
			return allDepartment;
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
	public List<Department> getAllDepartment2() {
		//Session session = HibernateUtil.openSession();
		Transaction tx = null;
		String hql = "";
		try{
			Session session = MyHibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			hql = "from Department d where d.fatherDept != null";
			Query query = session.createQuery(hql);
			List<Department> allDepartment = query.list();
			query = null;
			tx.commit();//提交事务
			return allDepartment;
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
	public boolean isExist(Department d) {
		//Session session = HibernateUtil.openSession();
		Transaction tx = null;
		Department dept = new Department();
		String hql = "";
		try{
			Session session = MyHibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			hql="from Department where departmentCode=?";
			Query query = session.createQuery(hql);
			query.setParameter(0, d.getDepartmentCode());
			dept = (Department) query.uniqueResult();
			query = null;
			tx.commit();//提交事务
			if(dept!=null){
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
	public List<Integer> getDepartment2IdByDepart1Id(List<Integer> depart1Id) {
		Transaction tx = null;
		String hql = "";
		try{
			//Session session = HibernateUtil.openSession();
			Session session = MyHibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			String level = "二级";
			for(Integer i=0;i<depart1Id.size();i++){
				System.out.println("depart1Id=="+depart1Id.get(i));
				
			}
			
			
			hql = "from Department d where ";
			for(Integer i=0;i<depart1Id.size();i++){
				System.out.println("depart1Id=="+depart1Id.get(i));
				if(i==0){
					hql += "d.fatherDept.id ="+depart1Id.get(i);
				}else{
					hql += "or d.fatherDept.id ="+depart1Id.get(i);
				}
			}
			hql += " and d.departmentLevel = '"+level+"'";
			Query query = session.createQuery(hql);
			List<Department> department2 = query.list();
			List<Integer> department2Id = new ArrayList<Integer>();
			for(Department d:department2){
				department2Id.add(d.getId());
			}
			query = null;
			tx.commit();//提交事务
			return department2Id;
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
	public List<Teacher> getTeacherByDepartId(List<Integer> departId) {
		Transaction tx = null;
		String hql = "";
		try{
			Session session = MyHibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			hql = "from Teacher t where ";
			for(Integer i=0;i<departId.size();i++){
				if(i==0){
					hql += "t.department.id ="+departId.get(i);
				}else{
					hql += "or t.department.id ="+departId.get(i);
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
}
