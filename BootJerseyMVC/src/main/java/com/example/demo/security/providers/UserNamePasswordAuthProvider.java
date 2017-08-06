package com.example.demo.security.providers;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class UserNamePasswordAuthProvider  extends AbstractUserDetailsAuthenticationProvider{

	private UserDetailsService userDetailsService;
	
	public UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}


	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		// TODO Auto-generated method stub
		
		return this.getUserDetailsService().loadUserByUsername(username);
	}
		
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		
		if (!userDetails.getPassword().equals(authentication.getCredentials())) {
			throw new BadCredentialsException("Authentication Failed. Username or Password not valid.");
		}
		authentication.setDetails(userDetails.getUsername()); 
		
	}
	
	
	
}
