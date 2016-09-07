package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import service.demoService.Interface.IDemoService;
import utils.file.fineFile.FileUtil;
import utils.file.fineFile.S3Uploads;
import utils.file.fineFile.UploadReceiver;
import utils.json.JsonUtils;
import dao.util.SQLOperatorUtil;
import entity.demo.Demo;
import entity.sqlEntity.ConditionEntity;
import entity.sqlEntity.SQLEntity;

@RestController
public class DemoController {
	@Autowired
	private IDemoService daoService;
	
	@RequestMapping(path="/demo",method=RequestMethod.POST)
	public String demoPut(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		FileUtil.doPost(req, resp);
		/*String json=message;
		SQLEntity sqlEntity = JsonUtils.jsonToSQLEntity(json);
		return JSONArray.fromObject(daoService.getLinkedDataList("Demo","demoQuery", sqlEntity)).toString();*/
		return "{\"success\":true}";
	}
}
