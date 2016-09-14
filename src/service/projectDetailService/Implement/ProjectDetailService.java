package service.projectDetailService.Implement;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dao.projectDao.Interface.IProjectDao;
import entity.sqlEntity.SQLEntity;
import service.projectDetailService.Interface.IProjectDetailService;

@Component
public class ProjectDetailService implements IProjectDetailService{
	@Autowired
	private IProjectDao projectDao;
	
	@Override
	public boolean saveEntity(Object entity, Class<?> entityType) {
		// TODO Auto-generated method stub
		return projectDao.saveEntity(entity, entityType);
	}

	@Override
	public boolean update(Class<?> entityType, SQLEntity sqlEntity) {
		// TODO Auto-generated method stub
		return projectDao.update(entityType, sqlEntity);
	}

	@Override
	public boolean delete(Class<?> entityType, SQLEntity sqlEntity) {
		// TODO Auto-generated method stub
		return projectDao.delete(entityType, sqlEntity);
	}

	@Override
	public List<Map<String, Object>> getCommonDataList(String tableName,
			SQLEntity sqlEntity) {
		// TODO Auto-generated method stub
		return projectDao.getCommonDataList(tableName, sqlEntity);
	}

	@Override
	public List<Map<String, Object>> getLinkedDataList(String tableName,
			String queryName, SQLEntity sqlEntity) {
		// TODO Auto-generated method stub
		return projectDao.getLinkedDataList(tableName, queryName, sqlEntity);
	}

	@Override
	public List<Map<String, Object>> getCustomDataList(String tableName,
			String queryName, SQLEntity sqlEntity) {
		// TODO Auto-generated method stub
		return projectDao.getCustomDataList(tableName, queryName, sqlEntity);
	}
}
