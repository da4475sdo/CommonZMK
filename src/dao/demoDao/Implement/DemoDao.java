package dao.demoDao.Implement;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dao.IHibernateDao;
import dao.demoDao.Interface.IDemoDao;
import entity.sqlEntity.SQLEntity;

@Component
public class DemoDao implements IDemoDao{
	@Autowired
	private IHibernateDao baseDao;
	@Override
	public boolean saveEntity(Object entity, Class<?> entityType) {
		// TODO Auto-generated method stub
		return baseDao.save(entity, entityType);
	}

	@Override
	public boolean update(Class<?> entityType, SQLEntity sqlEntity) {
		// TODO Auto-generated method stub
		return baseDao.update(entityType, sqlEntity);
	}

	@Override
	public boolean delete(Class<?> entityType, SQLEntity sqlEntity) {
		// TODO Auto-generated method stub
		return baseDao.delete(entityType, sqlEntity);
	}

	@Override
	public List<Map<String, Object>> getCommonDataList(String tableName,
			SQLEntity sqlEntity) {
		// TODO Auto-generated method stub
		return baseDao.query(tableName, sqlEntity);
	}

	@Override
	public List<Map<String, Object>> getLinkedDataList(String tableName,
			String queryName, SQLEntity sqlEntity) {
		// TODO Auto-generated method stub
		return baseDao.queryLinked(tableName, queryName, sqlEntity);
	}

	@Override
	public List<Map<String, Object>> getCustomDataList(String tableName,
			String queryName, SQLEntity sqlEntity) {
		// TODO Auto-generated method stub
		return baseDao.queryCustom(tableName, queryName, sqlEntity);
	}

}
