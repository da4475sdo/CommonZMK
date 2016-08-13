package dao;

import java.util.List;
import java.util.Map;

import entity.sqlEntity.SQLEntity;


public interface IHibernateDao {
	public boolean save(Object entity,Class<?> entityType);
	public boolean update(Class<?> entityType,SQLEntity sqlEntity);
	public boolean delete(Class<?> entityType,SQLEntity sqlEntity);
	public List<Map<String,Object>> query(Class<?> entityType,SQLEntity sqlEntity);
}
