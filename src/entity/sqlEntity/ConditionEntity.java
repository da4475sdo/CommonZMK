package entity.sqlEntity;


public class ConditionEntity {
	//����֮��Ĺ�ϵ
	private String relation;
	//����������
	private String name;
	//������ֵ
	private String value;
	//ֵ������֮��Ĳ���
	private int operation;
	//�Ƿ����������
	private boolean isCombination;
	//�������
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
