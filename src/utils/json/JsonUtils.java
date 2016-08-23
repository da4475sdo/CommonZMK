package utils.json;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import entity.sqlEntity.SQLEntity;

import net.sf.json.JSONArray;

public class JsonUtils {
	public static String listMapToJson(List<Map<String,Object>> dataList){
		String json=JSONArray.fromObject(dataList).toString();
		return json;
	}
	
	public static SQLEntity jsonToSQLEntity(String json){
		Gson gson=new Gson();
		SQLEntity sqlEntity=gson.fromJson(json,SQLEntity.class);
		return sqlEntity;
	} 
}
