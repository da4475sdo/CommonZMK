package dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import dao.util.HibernateUtils;
import entity.sqlEntity.SQLEntity;


public class HibernateDao implements IHibernateDao{
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public boolean save(Object entity,Class<?> entityType){
		Session session=null;
		try{			
			session=sessionFactory.openSession();
			session.beginTransaction();
			session.save(entityType.cast(entity));
			session.getTransaction().commit();
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
			session.getTransaction().rollback(); 
			return false;
		}finally{
			session.close();
		}
	}
	
	@Override
	public boolean update(Object entity,Class<?> entityType){
		Session session=null;
		try{			
			session=sessionFactory.openSession();
			session.beginTransaction();
			session.update(entityType.cast(entity));
			session.getTransaction().commit();
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
			session.getTransaction().rollback(); 
			return false;
		}finally{
			session.close();
		}
	}

	@Override
	public boolean delete(Object entity, Class<?> entityType) {
		Session session=null;
		try{			
			session=sessionFactory.openSession();
			session.beginTransaction();
			session.delete(entityType.cast(entity));
			session.getTransaction().commit();
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
			session.getTransaction().rollback(); 
			return false;
		}finally{
			session.close();
		}
	}

	@Override
	public List<Map<String, Object>> query(Object entity, Class<?> entityType,SQLEntity sqlEntity) {
		Session session=sessionFactory.openSession();
		Criteria mainCriteria=session.createCriteria(entityType);
		HibernateUtils.generateSQL(sqlEntity, mainCriteria);
		return null;
	}
}