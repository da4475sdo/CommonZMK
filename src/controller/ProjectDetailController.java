package controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import entity.sqlEntity.SQLEntity;
import service.projectDetailService.Interface.IProjectDetailService;
import utils.json.JsonUtils;

@RestController
public class ProjectDetailController {
	@Autowired
	private IProjectDetailService projectDetailService;
	
	@RequestMapping(path="/getProjectDetails",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public String getProjectDetails(@ModelAttribute("conditionJson") String conditionJson){
		SQLEntity sqlEntity=JsonUtils.jsonToSQLEntity(conditionJson);
		List<Map<String,Object>> dataList=projectDetailService.getCommonDataList("projectDetail", sqlEntity);
		String responseJson=JsonUtils.listMapToJson(dataList);
		return responseJson;
	}
}
