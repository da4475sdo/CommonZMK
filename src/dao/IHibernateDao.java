package dao;

import java.util.List;
import java.util.Map;

import entity.sqlEntity.SQLEntity;


public interface IHibernateDao {
	public boolean save(Object entity,Class<?> entityType);
	public boolean update(Object entity,Class<?> entityType);
	public boolean delete(Object entity,Class<?> entityType);
	public List<Map<String,Object>> query(Object entity,Class<?> entityType,SQLEntity sqlEntity);
}
