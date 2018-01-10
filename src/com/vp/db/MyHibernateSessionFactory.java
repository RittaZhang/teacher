package com.vp.db;

import org.hibernate.SessionFactory;

import org.hibernate.cfg.Configuration;


public class MyHibernateSessionFactory {
	private static SessionFactory sessionFactory;
	//构造方法私有化，保证单例模式
	private MyHibernateSessionFactory(){}
	//共有的静态方法，获得会话工厂对象
	public static SessionFactory getSessionFactory(){
		if(sessionFactory==null){
			Configuration config = new Configuration().configure();
			//ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
			sessionFactory = config.buildSessionFactory();
			return sessionFactory;
		}else{
			return sessionFactory;
		}
	}
}
