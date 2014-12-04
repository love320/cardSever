package app.util;

import java.util.Map;

public class MapRBean {
	
	private Map map;
	
	
	public MapRBean(Map map){
		this.map = map;
	}
	
	public Integer getInt(String name){
		return Integer.parseInt(map.get(name).toString());
	}
	
	public Object getObj(String name){
		return map.get(name);
	}
	
	public String getStr(String name){
		return (String)map.get(name);
	}
	
	public Map getMap(){
		return map;
	}

}
