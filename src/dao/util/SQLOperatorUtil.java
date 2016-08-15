package dao.util;
import org.hibernate.Query;

import entity.sqlEntity.ConditionEntity;


public class SQLOperatorUtil {
	
	public static final int STRING=0;
	public static final int INTEGER=1;
	public static final int BOOLEAN=2;
	public static final int FLOAT=3;
	public static final int DOUBLE=4;
	public static final int TEXT=5;
	
	public static void  setCondition(ConditionEntity condition,Query query){
		if(!condition.isCombination()){			
			switch(condition.getType()){
			case 0:query.setString(condition.getParamName(), condition.getValue());break;
			case 1:query.setInteger(condition.getParamName(), Integer.parseInt(condition.getValue()));break;
			case 2:query.setBoolean(condition.getParamName(), Boolean.parseBoolean(condition.getValue()));break;
			case 3:query.setFloat(condition.getParamName(), Float.parseFloat(condition.getValue()));break;
			case 4:query.setDouble(condition.getParamName(), Double.parseDouble(condition.getValue()));break;
			case 5:query.setText(condition.getParamName(), condition.getValue());break;
			}
		}
	}
}
