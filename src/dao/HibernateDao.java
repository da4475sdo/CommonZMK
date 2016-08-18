package dao;

import java.util.List;
import java.util.Map;
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
	public boolean update(Class<?> entityType,SQLEntity sqlEntity){
		Session session=null;
		try{			
			session=sessionFactory.openSession();
			String tableName=entityType.getSimpleName();
			return HibernateUtils.generateUpdateSQL(tableName, sqlEntity, session);
		}catch(Exception ex){
			ex.printStackTrace();
			session.getTransaction().rollback(); 
			return false;
		}finally{
			session.close();
		}
	}

	@Override
	public boolean delete(Class<?> entityType,SQLEntity sqlEntity) {
		Session session=null;
		try{			
			session=sessionFactory.openSession();
			String tableName=entityType.getSimpleName();
			return HibernateUtils.generateDeleteSQL(tableName, sqlEntity, session);
		}catch(Exception ex){
			ex.printStackTrace();
			session.getTransaction().rollback(); 
			return false;
		}finally{
			session.close();
		}
	}

	@Override
	public List<Map<String, Object>> query(String tableName,SQLEntity sqlEntity) {
		Session session=sessionFactory.openSession();
		return HibernateUtils.generateSQL(tableName,sqlEntity,session);
	}

	@Override
	public List<Map<String, Object>> queryLinked(String tableName,String queryName,SQLEntity sqlEntity) {
		Session session=sessionFactory.openSession();
		sqlEntity.setNeedLink(true);
		return HibernateUtils.generateLinkedSQL(tableName, queryName, sqlEntity, session);
	}

	@Override
	public List<Map<String, Object>> queryCustom(String table, String queryName) {
		Session seesion=sessionFactory.openSession();
		return null;
	}
}