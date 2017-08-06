package com.example.demo.security.stubs;

import java.util.HashMap;
import java.util.Map;

import com.example.demo.security.models.User;
import com.example.demo.security.models.UserCredentials;

public class UserDBStub {

	public static Map<String, User> users = new HashMap<String, User>();
	public static Map<String, UserCredentials> userCredentials = new HashMap<String, UserCredentials>();

	public static User getUserByEmail(String email) {
		User user = users.get(email);
		return user;
	}
	
	public static UserCredentials getUserCredentialsByEmail(String email){
		return userCredentials.get(email);
	}
	

}
