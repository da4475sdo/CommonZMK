package service.demoService.Implement;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dao.demoDao.Interface.IDemoDao;

import entity.sqlEntity.SQLEntity;
import service.demoService.Interface.IDemoService;

@Component
public class DemoService implements IDemoService{
	@Autowired
	private IDemoDao demoDao;
	
	@Override
	public boolean saveEntity(Object entity, Class<?> entityType) {
		// TODO Auto-generated method stub
		return demoDao.saveEntity(entity, entityType);
	}

	@Override
	public boolean update(Class<?> entityType, SQLEntity sqlEntity) {
		// TODO Auto-generated method stub
		return demoDao.update(entityType, sqlEntity);
	}

	@Override
	public boolean delete(Class<?> entityType, SQLEntity sqlEntity) {
		// TODO Auto-generated method stub
		return demoDao.delete(entityType, sqlEntity);
	}

	@Override
	public List<Map<String, Object>> getCommonDataList(String tableName,
			SQLEntity sqlEntity) {
		// TODO Auto-generated method stub
		return demoDao.getCommonDataList(tableName, sqlEntity);
	}

	@Override
	public List<Map<String, Object>> getLinkedDataList(String tableName,
			String queryName, SQLEntity sqlEntity) {
		// TODO Auto-generated method stub
		return demoDao.getLinkedDataList(tableName, queryName, sqlEntity);
	}

	@Override
	public List<Map<String, Object>> getCustomDataList(String tableName,
			String queryName, SQLEntity sqlEntity) {
		// TODO Auto-generated method stub
		return demoDao.getCustomDataList(tableName, queryName, sqlEntity);
	}
	
}
