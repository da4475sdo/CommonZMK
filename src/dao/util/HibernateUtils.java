package dao.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import entity.sqlEntity.ConditionEntity;
import entity.sqlEntity.SQLEntity;


public class HibernateUtils {
	public static List<Map<String,Object>> generateSQL(String tableName,SQLEntity sqlEntity,Session session){
		StringBuilder hql=new StringBuilder();
		hql.append("select distinct ");
		String[] properties=sqlEntity.getProperties();
		//���ɲ�ѯ�ֶ�
		for(int i=0,len=properties.length;i<len;i++){
			hql.append(properties[i]);
			if(i<len-1){
				hql.append(",");
			}
		}
		//���õ����ѯ�ı���
		if(!sqlEntity.isNeedLink()){			
			hql.append(" from ").append(tableName);
		}else{//���ö�������ѯ����
			
		}
		//���ɲ�ѯ����
		List<ConditionEntity> conditions=sqlEntity.getCondition();
		if(conditions!=null&&conditions.size()!=0){			
			hql.append(" where");
			generateConditions(conditions,hql);
		}
		//������������
		String[] sortFields=sqlEntity.getSortField();
		String[] orders=sqlEntity.getOrder();
		hql.append(" order by ");
		for(int i=0,len=sortFields.length;i<len;i++){
			hql.append(sortFields[i]).append(" ").append(orders[i]);
			if(i<len-1){
				hql.append(",");
			}
		}
		Query query=session.createQuery(hql.toString());
		//���ò�ѯ������ֵ
		for(int i=0,len=conditions.size();i<len;i++){
			SQLOperatorUtil.setCondition(conditions.get(i), query);
		}
		//����ҳ��
		int pagination=sqlEntity.getPagination();
		query.setFirstResult(pagination);
		//����ÿҳ�Ķ������
		int limit=sqlEntity.getLimit();
		query.setMaxResults(limit);
		//��ѯ����
		List<?> list=query.list();
		List<Map<String,Object>> dataList=sqlListToListMap(list,properties);
		return dataList;
	}
	
	public static boolean generateUpdateSQL(String tableName,SQLEntity sqlEntity,Session session){
		Transaction trans=session.beginTransaction();
		StringBuilder hql=new StringBuilder();
		//���ñ���
		hql.append("update ").append(tableName).append(" set ");
		//���ø��µ�����
		List<ConditionEntity> entitys=sqlEntity.getEntity();
		for(int i=0,len=entitys.size();i<len;i++){
			ConditionEntity entity=entitys.get(i);
			String name=entity.getName();
			String paramName=entity.getParamName();
			hql.append(" ").append(name).append("=").append(":").append(paramName).append(" ");
			if(i<len-1){
				hql.append(",");
			}
		}
		//���ɲ�ѯ����
		List<ConditionEntity> conditions=sqlEntity.getCondition();
		if(conditions!=null&&conditions.size()!=0){	
			hql.append(" where");
			//���ɲ�ѯ����
			generateConditions(conditions,hql);
		}
		Query updateQuery=session.createQuery(hql.toString());
		//���ñ�����ֵ
		conditions.addAll(entitys);
		for(int i=0,len=conditions.size();i<len;i++){
			SQLOperatorUtil.setCondition(conditions.get(i), updateQuery);
		}
		int result=updateQuery.executeUpdate();
		trans.commit();
		if(result>0){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean generateDeleteSQL(String tableName,SQLEntity sqlEntity,Session session){
		Transaction trans=session.beginTransaction();
		StringBuilder hql=new StringBuilder();
		//���ñ���
		hql.append("delete ").append("from ").append(tableName);
		//���ɲ�ѯ����
		List<ConditionEntity> conditions=sqlEntity.getCondition();
		if(conditions!=null&&conditions.size()!=0){	
			hql.append(" where");
			//���ɲ�ѯ����
			generateConditions(conditions,hql);
		}
		Query deleteQuery=session.createQuery(hql.toString());
		//���ñ�����ֵ
		for(int i=0,len=conditions.size();i<len;i++){
			SQLOperatorUtil.setCondition(conditions.get(i), deleteQuery);
		}
		int result=deleteQuery.executeUpdate();
		trans.commit();
		if(result>0){
			return true;
		}else{
			return false;
		}
	}
	
	public static List<Map<String,Object>> sqlListToListMap(List<?> list,String[] properties){
		List<Map<String,Object>> listMap=new ArrayList<Map<String,Object>>();
		for(int i=0,len=list.size();i<len;i++){
			Object[] datas=(Object[])list.get(i);
			Map<String,Object> map=new HashMap<String,Object>();
			for(int j=0,leng=datas.length;j<leng;j++){				
				map.put(properties[j], datas[j]);
			}
			listMap.add(map);
		}
		return listMap;
	}
	
	private static void generateConditions(List<ConditionEntity> conditions,StringBuilder hql){
		for(int i=0,len=conditions.size();i<len;i++){
			ConditionEntity condition=conditions.get(i);
			boolean isCombination=condition.isCombination();
			String relation=condition.getRelation()!=null?condition.getRelation():"";
			//�Ǹ�������
			if(!isCombination){				
				String name=condition.getName();
				String paramName=condition.getParamName();
				String operation=condition.getOperation();
				hql.append(" ").append(name).append(operation).append(":").append(paramName).append(" ").append(relation);
			}else{
				List<ConditionEntity> CombinationCons=condition.getConditionConbination();
				hql.append(" (");
				generateConditions(CombinationCons, hql);
				hql.append(") ");
				hql.append(relation);
			}
		}
	}
	
	public static void configLinks(){
		
	}
}
