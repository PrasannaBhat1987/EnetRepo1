package com.pras.util;

import java.util.HashMap;
import java.util.Map;

public class AuthUtil {
	private static AuthUtil instance = null;
	private Map<String,String> authMap = new HashMap<String,String>();
	
	private AuthUtil() {
		// Exists only to defeat instantiation.
	}

	public static AuthUtil getInstance() {
		if (instance == null) {
			instance = new AuthUtil();
		}
		return instance;
	}
	
	public static void addAuth(String key, String value){
		getInstance().authMap.put(key, value);
	}
	
	public static String getRole(String key) {
		return getInstance().authMap.get(key);
	}
	
	public static void logoutUser(String key) {
		getInstance().authMap.remove(key);
	}
}
