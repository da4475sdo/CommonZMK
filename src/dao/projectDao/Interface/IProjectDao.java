package dao.projectDao.Interface;

import java.util.List;
import java.util.Map;

import entity.sqlEntity.SQLEntity;

public interface IProjectDao {
	public boolean saveEntity(Object entity,Class<?> entityType);
	public boolean update(Class<?> entityType,SQLEntity sqlEntity);
	public boolean delete(Class<?> entityType,SQLEntity sqlEntity);
	public List<Map<String,Object>> getCommonDataList(String tableName,SQLEntity sqlEntity);
	public List<Map<String,Object>> getLinkedDataList(String tableName,String queryName,SQLEntity sqlEntity);
	public List<Map<String,Object>> getCustomDataList(String tableName,String queryName,SQLEntity sqlEntity);
}
