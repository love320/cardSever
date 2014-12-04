package app.util;

import java.net.URL;

public class AppPath {
	
	private static String OS = System.getProperty("os.name").toLowerCase();
	
	private static URL URL = AppPath.class.getResource("/");
	
	public static String path(){
		return APPath();
	}
	
	//APP
	public static String APPath(){
		String url = URL.toString();
		if(isLinux()){
			return url.substring(5, url.length());
		}else{
			String path = url.substring(6, url.length());
			return path.replace('/','\\');
		}
	}
	
	//WEB
	public static String WebPath(){
		return APPath();
	}
	
	public static boolean isLinux(){  
        return OS.indexOf("linux")>=0;  
    }
	
	public static boolean isWindows(){  
        return OS.indexOf("windows")>=0;  
    }  

}
