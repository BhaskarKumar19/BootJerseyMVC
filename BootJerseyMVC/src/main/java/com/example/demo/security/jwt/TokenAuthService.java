package com.example.demo.security.jwt;

import java.util.Map;

public interface TokenAuthService {

	public String createToken(String userName, String role, long timeToLive);
	public Map<String,String> validateToken(String jwtToken);
	public String updateToken();
	
}
