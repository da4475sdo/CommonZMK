package entity.sqlEntity;

import java.util.List;


public class ConditionEntity {
	//条件之间的关系
	private String relation;
	//条件的名称
	private String name;
	//条件的值
	private String value;
	//值与名称之间的操作
	private String operation;
	//是否是组合条件
	private boolean isCombination;
	//组合条件
	private List<ConditionEntity> conditionConbination;
	//参数类型
	private int type;
	//参数变量名
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
