package com.example.demo.security.uitls;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.util.StringUtils;

public class HeaderTokenExtractor implements TokenExtractor {
	public static String HEADER_PREFIX = "Bearer ";

	@Override
	public String extract(String header) {
		// TODO Auto-generated method stub

		if (StringUtils.isEmpty(header)) {
			throw new AuthenticationServiceException("Authorization header cannot be blank!");

		}

		if (header.length() < HEADER_PREFIX.length()) {
			throw new AuthenticationServiceException("Invalid authorization header size.");
		}
		return header.substring(HEADER_PREFIX.length(), header.length());

	}

}
