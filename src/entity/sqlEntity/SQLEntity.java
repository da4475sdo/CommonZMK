package entity.sqlEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class SQLEntity {
	//��Ҫ��ѯ���ֶ�
	private String[] properties;
	//��ѯ����
	private List<ConditionEntity> condition;
	//�����ֶ�
	private String[] sortField;
	//����ʽ
	private String[] order;
	//��ǰҳ��
	private int pagination;
	//ÿҳ�������
	private int limit;
	//�Ƿ���Ҫ����
	private boolean needLink;
	//������
	private Map<String,String> alias;
	//ʵ�����Ժ�ֵ
	private List<ConditionEntity> entity;
	
	public SQLEntity(){
		this.condition=new ArrayList<ConditionEntity>();
		this.entity=new ArrayList<ConditionEntity>();
	}
	
	public List<ConditionEntity> getEntity() {
		return entity;
	}
	public void setEntity(List<ConditionEntity> entity) {
		this.entity = entity;
	}
	
	public Map<String, String> getAlias() {
		return alias;
	}
	public void setAlias(Map<String, String> alias) {
		this.alias = alias;
	}
	
	public boolean isNeedLink() {
		return needLink;
	}
	public void setNeedLink(boolean needLink) {
		this.needLink = needLink;
	}
	
	public int getPagination() {
		return pagination;
	}
	public void setPagination(int pagination) {
		this.pagination = pagination;
	}
	
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	public String[] getProperties() {
		return properties;
	}
	public void setProperties(String[] properties) {
		this.properties = properties;
	}
	
	public List<ConditionEntity> getCondition() {
		return condition;
	}
	public void setCondition(List<ConditionEntity> condition) {
		this.condition = condition;
	}
	
	public String[] getSortField() {
		return sortField;
	}
	public void setSortField(String[] sortField) {
		this.sortField = sortField;
	}
	
	public String[] getOrder() {
		return order;
	}
	public void setOrder(String[] order) {
		this.order = order;
	}
	
	public void addCondition(ConditionEntity condition){
		this.condition.add(condition);
	}
	
	public void addEntity(ConditionEntity condition){
		this.entity.add(condition);
	}
}
