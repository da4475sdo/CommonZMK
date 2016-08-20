package controller;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.demoService.Interface.IDemoService;
import dao.util.SQLOperatorUtil;
import entity.demo.Demo;
import entity.sqlEntity.ConditionEntity;
import entity.sqlEntity.SQLEntity;

@RestController
public class DemoController {
	@Autowired
	private IDemoService daoService;
	
	@RequestMapping(path="/demo",method=RequestMethod.POST)
	public String demoPut(String message){
		Demo demo =new Demo();
		demo.setDemoID("111");
		demo.setName("zmk");
		SQLEntity sqlEntity=new SQLEntity();
		sqlEntity.setProperties(new String[]{"demoID","name"});
		ConditionEntity condition1=new ConditionEntity();
		ConditionEntity condition2=new ConditionEntity();
		ConditionEntity condition3=new ConditionEntity();
		condition1.setCombination(true);
		condition1.setName("name");
		condition1.setParamName("name1");
		condition1.setRelation("and");
		condition1.setValue("zmk");
		condition1.setOperation("=");
		condition1.setType(SQLOperatorUtil.STRING);
		condition2.setCombination(false);
		condition2.setName("demoID");
		condition2.setParamName("demoID1");
		condition2.setRelation("or");
		condition2.setValue("333");
		condition2.setOperation("=");
		condition2.setType(SQLOperatorUtil.STRING);
		condition3.setCombination(false);
		condition3.setName("demoID");
		condition3.setParamName("demoID2");
		condition3.setValue("333");
		condition3.setOperation("=");
		condition3.setType(SQLOperatorUtil.STRING);
		List<ConditionEntity> conbinaCons=new ArrayList<ConditionEntity>();
		conbinaCons.add(condition2);
		conbinaCons.add(condition3);
		condition1.setConditionConbination(conbinaCons);
		sqlEntity.addCondition(condition1);
		sqlEntity.addCondition(condition2);
		sqlEntity.addCondition(condition3);
		
		
		
		sqlEntity.setLimit(10);
		sqlEntity.setPagination(0);
		sqlEntity.setOrder(new String[]{"desc"});
		sqlEntity.setSortField(new String[]{"demoID"});
		return JSONArray.fromObject(daoService.getCommonDataList("Demo", sqlEntity)).toString();
		//return JSONObject.fromObject(new ArrayList<String>())+"";
	}
}
