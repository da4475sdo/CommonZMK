package controller;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import entity.sqlEntity.SQLEntity;
import service.projectService.Interface.IProjectService;
import utils.json.JsonUtils;

@RestController
public class ProjectController {
	@Autowired
	private IProjectService projectService;
	
	@RequestMapping(path="/getProjects",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public String getProjects(@ModelAttribute("json") String json){
		SQLEntity sqlEntity = JsonUtils.jsonToSQLEntity(json);
		List<Map<String,Object>> dataList=projectService.getCommonDataList("project",sqlEntity);
		return JSONArray.fromObject(dataList).toString();
	}	
}
