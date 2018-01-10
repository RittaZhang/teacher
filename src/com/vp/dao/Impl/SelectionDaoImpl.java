package com.vp.dao.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.vp.action.Util;
import com.vp.dao.SelectionDao;
import com.vp.db.MyHibernateSessionFactory;
import com.vp.model.Selection;


public class SelectionDaoImpl implements SelectionDao {

	@Override
	public List<String> getAllValueByName(String groupname) {
		Transaction tx = null;
		String hql = "";
		try{
			Session session = MyHibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			hql = "select optionvalue from Selection where groupname = '"+groupname+"'";
			Query query = session.createQuery(hql);
			List<String> allTitle = query.list();
			query = null;
			tx.commit();//提交事务
			return allTitle;
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
	public void saveSchool(Selection selection) {
		Transaction tx = null;
		String hql = "";
		String hql1 = "";
		try{
			Session session = MyHibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			
			
			hql1 = "from Selection where groupname = '毕业院校' and optionvalue = '"+selection.getOptionvalue()+"'";
			System.out.println("hql1-----"+hql1);
			Query query1 = session.createQuery(hql1);
			List<Selection> school1 = query1.list();
			query1 = null;
			if(school1.size()==0){
				hql = " from Selection where groupname = '毕业院校' order by optionorder desc";
				Query query = session.createQuery(hql);
				List<Selection> allSchool = query.list();
				query = null;
				
				selection.setOptionorder(allSchool.get(0).getOptionorder()+1);
				session.save(selection);
				
			}
			tx.commit();//提交事务
			if(school1.size()==0){
				Util.addLog(selection.getId(), "add a Selection and groupname = 毕业院校");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if (tx!=null) {
                tx=null;
            }
		}
	}

	@Override
	public List<Selection> queryFirstSelection() {
		// TODO Auto-generated method stub
		Transaction tx = null;
		List<Selection> list = null;
		String hql = "";
		try{
			Session session = MyHibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			hql="from Selection where groupname='毕业院校'";
			Query query = session.createQuery(hql);
			list = query.list();
			tx.commit();
			return list;
		}catch(Exception ex){
			ex.printStackTrace();
			tx.commit();
			return list;
		}finally{
			if(tx!=null){
				tx=null;
			}
		}
	}

	@Override
	public Selection querySelectionById(int id) {
		// TODO Auto-generated method stub
		Transaction tx = null;
		try{
			Session session = MyHibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			Selection selection = (Selection) session.get(Selection.class, id);
			tx.commit();
			return selection;
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}finally{
			if(tx!=null){
				tx=null;
			}
		}
	}

	@Override
	public boolean addSelection(Selection s) {
		// TODO Auto-generated method stub
		Transaction tx = null;
		String hql = "";
		try{
			Session session = MyHibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			System.out.println("s.getGroupname()-------"+s.getGroupname());
			hql = " from Selection where groupname = '"+s.getGroupname()+"'"+" order by optionorder desc";
			System.out.println("hql-------"+hql);
			Query query = session.createQuery(hql);
			List<Selection> searchSchool = query.list();
			query = null;
			
			s.setOptionorder(searchSchool.get(0).getOptionorder()+1);
			session.save(s);
			
			tx.commit();
			Util.addLog(s.getId(), "add a Selection ");
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}finally{
			if(tx!=null){
				tx=null;
			}
		}
	}

	@Override
	public void updateSelection(Selection s) {
		// TODO Auto-generated method stub
		Transaction tx = null;
		try{
			Session session = MyHibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			session.update(s);
			
			tx.commit();
			Util.addLog(s.getId(), "update a Selection ");
		}catch(Exception ex){
			ex.printStackTrace();
			//tx.commit();
		}finally{
			if(tx!=null){
				tx=null;
			}
		}
	}

	@Override
	public List<String> queryAllGroupname() {
		Transaction tx = null;
		String hql = "";
		try{
			Session session = MyHibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			hql = "select distinct groupname from Selection";
			Query query = session.createQuery(hql);
			List<String> allGroup = query.list();
			query = null;
			tx.commit();//提交事务
			return allGroup;
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
	public List<Selection> getSelectionByGroup(String groupname) {
		Transaction tx = null;
		String hql = "";
		List<Selection> list = null;
		try{
			Session session = MyHibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			hql = "from Selection where groupname=?";
			Query query = session.createQuery(hql);
			query.setParameter(0, groupname);
			list = query.list();
			query = null;
			tx.commit();//提交事务
			return list;
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
	public boolean isExistValue(String s) {
		Transaction tx = null;
		Selection sel = new Selection();
		String hql = "";
		try{
			Session session = MyHibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			hql="from Selection where optionvalue=?";
			Query query = session.createQuery(hql);
			query.setParameter(0, s);
			sel = (Selection) query.uniqueResult();
			query = null;
			tx.commit();//提交事务
			if(sel!=null){
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
