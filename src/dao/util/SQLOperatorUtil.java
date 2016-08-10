package dao.util;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import entity.sqlEntity.ConditionEntity;


public class SQLOperatorUtil {
	//等于
	public static final int EQ=0;
	//like
	public static final int LIKE=1;
	//小于
	public static final int LT=2;
	//小于等于
	public static final int LE=3;
	//大于
	public static final int GT=4;
	//大于等于
	public static final int GE=5;
	//between
	public static final int BETWEEN=6;
	//isNUll
	public static final int ISNULL=7;
	
	public static void  getCondition(ConditionEntity condition,Criterion cri){
		switch(condition.getOperation()){
		case 0:cri=Restrictions.eq(condition.getName(), condition.getValue());break;
		case 1:cri=Restrictions.like(condition.getName(), condition.getValue());break;
		case 2:cri=Restrictions.lt(condition.getName(), condition.getValue());break;
		case 3:cri=Restrictions.le(condition.getName(), condition.getValue());break;
		case 4:cri=Restrictions.gt(condition.getName(), condition.getValue());break;
		case 5:cri=Restrictions.ge(condition.getName(), condition.getValue());break;
		case 6:cri=Restrictions.between(condition.getName(), condition.getValue().split(";")[0],condition.getValue().split(";")[1]);break;
		case 7:cri=Restrictions.isNull(condition.getName());break;
		}
	}
}
