package utils.json;

import java.util.List;
import java.util.Map;
import entity.sqlEntity.SQLEntity;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonUtils {
	public static String listMapToJson(List<Map<String,Object>> dataList){
		String json=JSONArray.fromObject(dataList).toString();
		return json;
	}
	
	public static SQLEntity jsonToSQLEntity(String json){
		JSONObject jsonObj=JSONObject.fromObject(json);
		SQLEntity sqlEntity=(SQLEntity)JSONObject.toBean(jsonObj,SQLEntity.class);
		return sqlEntity;
	} 
}
