package entity;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AbstractEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -54178036849378832L;
	public static final short SUCCESS = 1;
	public static final short NOTFOUND = 0;
	
	private Map<String, Object> properties = null;
	
	public Map<String,Object> getProperties(){
		if(properties == null){
			properties = new LinkedHashMap<String, Object>();
		}
		return properties;
	}
}
