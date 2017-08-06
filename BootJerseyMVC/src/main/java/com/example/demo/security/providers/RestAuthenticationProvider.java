package com.example.demo.security.providers;


import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.demo.security.models.SecureUser;
import com.example.demo.security.models.User;
import com.example.demo.security.models.UserCredentials;
import com.example.demo.security.models.UserPrincipal;
import com.example.demo.security.service.UserService;

@Component
public class RestAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	UserService userService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// TODO Auto-generated method stub

		Object principal = authentication.getPrincipal();
		if (principal instanceof UserPrincipal) {

			com.example.demo.security.models.UserPrincipal userPrincipal = (UserPrincipal) principal;

			return authenticateByUsernameAndPassword(userPrincipal, userPrincipal.getValue(),
					(String) authentication.getCredentials());
		}

		return null;
	}

	private Authentication authenticateByUsernameAndPassword(UserPrincipal userPrincipal, String email,
			String password) {

		User user = userService.getUserByEmail(email);

		if (user == null) {
			throw new UsernameNotFoundException("User not found: " + email);
		}

		UserCredentials userCredentials = userService.getUserCredentialsByEmail(email);
		if (userCredentials == null) {
			throw new UsernameNotFoundException("User credentials not found");
		}

		if (!userCredentials.isEnabled()) {
			throw new DisabledException("User is not active");
		}

		if (!password.equals(userCredentials.getPassword())) {
			throw new BadCredentialsException("Authentication Failed. Username or Password not valid.");
		}

		// if (user.getAuthority() == null) throw new
		// InsufficientAuthenticationException("User has no authority
		// assigned");

		SecureUser secureUser = new SecureUser(user.getEmail(), userCredentials.isEnabled());

		Collection<? extends GrantedAuthority> authorities=null;
		if (secureUser != null) {
		 authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
		}
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(secureUser,password, authorities);
				
		return authenticationToken;
		
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
