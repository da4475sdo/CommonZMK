package dao.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;

import utils.xml.XMLUtils;
import entity.sqlEntity.ConditionEntity;
import entity.sqlEntity.SQLEntity;


public class HibernateUtils {
	public static List<Map<String,Object>> generateSQL(String tableName,SQLEntity sqlEntity,Session session){
		StringBuilder hql=new StringBuilder();
		hql.append("select distinct ");
		String[] properties=sqlEntity.getProperties();
		//生成查询字段
		for(int i=0,len=properties.length;i<len;i++){
			hql.append(properties[i]);
			if(i<len-1){
				hql.append(",");
			}
		}
		//设置单表查询的表名
		if(!sqlEntity.isNeedLink()){			
			hql.append(" from ").append(tableName);
		}else{//设置多表关联查询表名
			String[] links=tableName.split(";");
			if(links!=null&&links.length>=2){				
				String table=links[0];
				String queryName=links[1];
				try {
					StringBuilder linkedCondition=configLinks(hql, table, queryName);
					hql.append(" where ").append(linkedCondition.toString());
				} catch (JDOMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
			}else{
				return null;
			}
		}
		//生成查询条件
		List<ConditionEntity> conditions=sqlEntity.getCondition();
		if(conditions!=null&&conditions.size()!=0){
			hql=sqlEntity.isNeedLink()?hql.append(" and "):hql.append(" where");
			generateConditions(conditions,hql);
		}
		//生成排序条件
		String[] sortFields=sqlEntity.getSortField();
		String[] orders=sqlEntity.getOrder();
		hql.append(" order by ");
		for(int i=0,len=sortFields.length;i<len;i++){
			hql.append(sortFields[i]).append(" ").append(orders[i]);
			if(i<len-1){
				hql.append(",");
			}
		}
		
		return queryData(hql.toString(), session, sqlEntity, conditions, properties);
	}
	
	public static boolean generateUpdateSQL(String tableName,SQLEntity sqlEntity,Session session){
		Transaction trans=session.beginTransaction();
		StringBuilder hql=new StringBuilder();
		//设置表名
		hql.append("update ").append(tableName).append(" set ");
		//设置更新的属性
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
		//生成查询条件
		List<ConditionEntity> conditions=sqlEntity.getCondition();
		if(conditions!=null&&conditions.size()!=0){	
			hql.append(" where");
			//生成查询条件
			generateConditions(conditions,hql);
		}
		Query updateQuery=session.createQuery(hql.toString());
		//设置变量的值
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
		//设置表名
		hql.append("delete ").append("from ").append(tableName);
		//生成查询条件
		List<ConditionEntity> conditions=sqlEntity.getCondition();
		if(conditions!=null&&conditions.size()!=0){	
			hql.append(" where");
			//生成查询条件
			generateConditions(conditions,hql);
		}
		Query deleteQuery=session.createQuery(hql.toString());
		//设置变量的值
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
	
	public static List<Map<String,Object>> generateLinkedSQL(String tableName,String queryName,SQLEntity sqlEntity,Session session){
		return generateSQL(tableName+";"+queryName, sqlEntity, session);
	}
	
	public static List<Map<String,Object>> generateCustomSQL(String tableName,String queryName,Session session,SQLEntity sqlEntity){
		try {
			String[] properties=sqlEntity.getProperties();
			String xmlFile="/entity/"+tableName+"/"+tableName+".xml";
			Element root = XMLUtils.parserXml(xmlFile);
			XPath xPath;
			xPath = XPath.newInstance("/table-links/sql-query[@id='"+queryName+"']");
			Element cutomSql=(Element)xPath.selectSingleNode(root);
			String hql=cutomSql.getText();
			return queryData(hql, session, sqlEntity, null, properties);
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
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
			//非复合条件
			if(!isCombination){				
				String name=condition.getName();
				String paramName=condition.getParamName();
				String operation=condition.getOperation();
				hql.append(" ").append(name).append(operation).append(":").append(paramName).append(" ").append(relation);
			}else{
				List<ConditionEntity> CombinationCons=condition.getConditionConbination();
				conditions.addAll(CombinationCons);
				hql.append(" (");
				generateConditions(CombinationCons, hql);
				hql.append(") ");
				hql.append(relation);
			}
		}
	}
	
	public static StringBuilder configLinks(StringBuilder hql,String tableName,String queryName) throws JDOMException{
		StringBuilder linkedCondition=new StringBuilder();
		String xmlFile="/entity/"+tableName+"/"+tableName+".xml";
		Element root = XMLUtils.parserXml(xmlFile);
		XPath xPath=XPath.newInstance("/table-links/table-link[@id='"+queryName+"']");
		Element link=(Element)xPath.selectSingleNode(root);
		String mainTable=link.getAttributeValue("table");
		String mainTableAlia=link.getAttributeValue("alia");
		if(mainTableAlia!=null&&!"".equals(mainTableAlia)){			
			hql.append(" from ").append(mainTable).append(" as ").append(mainTableAlia);
		}else{
			hql.append(" from ").append(mainTable);
		}
		hql.append(",");
		
		List<?> linkFields=link.getChildren();
		for(int i=0,len=linkFields.size();i<len;i++){
			Element linkField=(Element)linkFields.get(i);
			String linkedTable=linkField.getAttributeValue("table");
			String linkedTableAlia=linkField.getAttributeValue("alia");
			String linkedContent=linkField.getText();
			if(linkedTableAlia!=null&&!"".equals(linkedTableAlia)){
				hql.append(linkedTable).append(" as ").append(linkedTableAlia);
			}
			//添加连表条件
			linkedCondition.append(linkedContent);
			if(i<len-1){
				hql.append(",");
				linkedCondition.append(" and ");
			}
		}
		return linkedCondition;
	}
	
	public static List<Map<String,Object>> queryData(String hql,Session session,SQLEntity sqlEntity,List<ConditionEntity> conditions,String[] properties){
		Query query=session.createQuery(hql.toString());
		//设置查询参数的值
		if(conditions!=null&&conditions.size()>0){			
			for(int i=0,len=conditions.size();i<len;i++){
				SQLOperatorUtil.setCondition(conditions.get(i), query);
			}
		}
		//设置页数
		int pagination=sqlEntity.getPagination();
		query.setFirstResult(pagination);
		//设置每页的多大条数
		int limit=sqlEntity.getLimit();
		query.setMaxResults(limit);
		//查询数据
		List<?> list=query.list();
		List<Map<String,Object>> dataList=sqlListToListMap(list,properties);
		return dataList;
	}
}
