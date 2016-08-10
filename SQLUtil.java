import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;


public class SQLUtil {
	public static void generateSQL(SQLEntity sqlEntity){
		Criteria c=null;
		
		//���ö�����
		if(sqlEntity.isNeedLink()){
			Map<String,String> alias=sqlEntity.getAlias();
			Iterator<String> IAlias=alias.keySet().iterator();
			while(IAlias.hasNext()){
				c.createAlias(alias.get(IAlias.next()),IAlias.next());
			}
		}
		
		//������Ҫ��ѯ���ֶ�
		String[] properties=sqlEntity.getProperties();
		ProjectionList proList=Projections.projectionList();
		for(int i=0,len=properties.length;i<len;i++){
			proList.add(Projections.property(properties[i]));
		}
		c.setProjection(proList);
		
		//���ò�ѯ����
		List<ConditionEntity> conditions=sqlEntity.getCondition();
		Junction junction=Restrictions.conjunction();
		for(int i=0,len=conditions.size();i<len;i++){
			Criterion cri=null;
			ConditionEntity condition=conditions.get(i);
			//���������
			if(!condition.isCombination()){
				junction=conbinateCondition(condition, cri);
			}else{
				ConditionEntity[] conditionArray=condition.getConditionConbination();
				Junction conbineJunction=null;
				for(int j=0,leng=conditionArray.length ;j<leng;j++){
					conbineJunction=conbinateCondition(condition, cri);
				}
				if("and".equals(condition.getRelation())){
					junction.add(Restrictions.and(conbineJunction));
				}else{
					junction.add(Restrictions.or(conbineJunction));
				}
 			}
		}
		c.add(junction);
		
		//���������ֶ�
		String[] sortFields=sqlEntity.getSortField();
		//��������ʽ
		String order=sqlEntity.getOrder();
		for(int i=0,len=sortFields.length;i<len;i++){
			Order orderFun="desc".equals(order)?Order.desc(sortFields[i]):Order.asc(sortFields[i]);
			c.addOrder(orderFun);
		}
		
		//���õ�ǰҳ��
		int pagination=sqlEntity.getPagination();
		c.setFirstResult(pagination);
		
		//����ÿҳ�������
		int limit=sqlEntity.getLimit();
		c.setMaxResults(limit);
	}
	
	public static Junction conbinateCondition(ConditionEntity condition,Criterion cri){
		Junction junction=Restrictions.conjunction();
		SQLOperatorUtil.getCondition(condition, cri);
		if("and".equals(condition.getRelation())){
			junction.add(Restrictions.and(cri));
		}else{
			junction.add(Restrictions.or(cri));
		}
		return junction;
	}
}
