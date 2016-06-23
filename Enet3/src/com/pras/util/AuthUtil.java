package com.pras.util;

import java.util.HashMap;
import java.util.Map;

import com.pras.constant.Constants;
import com.pras.model.User;

public class AuthUtil {
	private static AuthUtil instance = null;
	private Map<String,User> authMap = new HashMap<String,User>();
	
	private AuthUtil() {
		// Exists only to defeat instantiation.
	}

	public static AuthUtil getInstance() {
		if (instance == null) {
			instance = new AuthUtil();
		}
		return instance;
	}
	
	public static void addAuth(String key, User u){
		getInstance().authMap.put(key, u);
	}
	
	public static User getLoggedInUser(String key) {
		return getInstance().authMap.get(key);
	}
	
	public static void logoutUser(String key) {
		getInstance().authMap.remove(key);
	}
	
	public static boolean isValid(String auth) {
		User user = getLoggedInUser(auth);
		if(user != null) {
			if(user.getRole().equals(Constants.REP)) {
				return false;
			}
			return true;
		}
		return false;
	}
	
	public static boolean isValidForAll(String auth) {
		if(getLoggedInUser(auth) != null) {
			return true;
		}
		return false;
	}
}
