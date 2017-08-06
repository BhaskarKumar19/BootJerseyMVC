package com.example.demo.security.providers;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.example.demo.security.jwt.JwtToken;
import com.example.demo.security.models.SecureUser;
import com.example.demo.security.models.UserPrincipal;
@Component
public class TokenAuthProvider implements AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Object principal = authentication.getPrincipal();
		SecureUser secureUser = null;
		Map<String, String> claims = (Map<String, String>) authentication.getCredentials();
		if (principal instanceof UserPrincipal && claims != null) {
			UserPrincipal userPrincipal = (UserPrincipal) principal;
			Collection<? extends GrantedAuthority> authorities = null;
			if (!StringUtils.isEmpty(claims.get("Subject"))) {
				secureUser = new SecureUser(userPrincipal.getUsername(), true);
				authorities = Collections.singleton(new SimpleGrantedAuthority(claims.get("Subject")));
				// below constructor created an authenticated user
				return new JwtToken(secureUser, authentication.getCredentials(), authorities);
			}
		}
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return JwtToken.class.isAssignableFrom(authentication);
	}
}
