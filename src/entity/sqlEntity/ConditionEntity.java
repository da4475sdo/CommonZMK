package entity.sqlEntity;


public class ConditionEntity {
	//条件之间的关系
	private String relation;
	//条件的名称
	private String name;
	//条件的值
	private String value;
	//值与名称之间的操作
	private int operation;
	//是否是组合条件
	private boolean isCombination;
	//组合条件
	private ConditionEntity[] conditionConbination;
	
	public boolean isCombination() {
		return isCombination;
	}
	public void setCombination(boolean isCombination) {
		this.isCombination = isCombination;
	}
	
	public ConditionEntity[] getConditionConbination() {
		return conditionConbination;
	}
	public void setConditionConbination(ConditionEntity[] conditionConbination) {
		this.conditionConbination = conditionConbination;
	}
	
	public int getOperation() {
		return operation;
	}
	public void setOperation(int operation) {
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
