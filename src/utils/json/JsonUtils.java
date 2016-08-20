package utils.json;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonUtils {
	public static String listMapToJson(List<Map<String,Object>> dataList){
		String json=JSONArray.fromObject(dataList).toString();
		return json;
	}
	
	public static List<Map<String,Object>> jsonToListMap(String json){
		
	} 
}
