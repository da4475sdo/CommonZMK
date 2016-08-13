package entity.sqlEntity;

import java.util.List;


public class ConditionEntity {
	//����֮��Ĺ�ϵ
	private String relation;
	//����������
	private String name;
	//������ֵ
	private String value;
	//ֵ������֮��Ĳ���
	private String operation;
	//�Ƿ����������
	private boolean isCombination;
	//�������
	private List<ConditionEntity> conditionConbination;
	//��������
	private int type;
	//����������
	private String paramName;
	
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	public boolean isCombination() {
		return isCombination;
	}
	public void setCombination(boolean isCombination) {
		this.isCombination = isCombination;
	}
	
	public List<ConditionEntity> getConditionConbination() {
		return conditionConbination;
	}
	public void setConditionConbination(List<ConditionEntity> conditionConbination) {
		this.conditionConbination = conditionConbination;
	}
	
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
