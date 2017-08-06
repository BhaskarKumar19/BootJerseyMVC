package com.example.demo.security.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.demo.security.models.User;
import com.example.demo.security.models.UserCredentials;
import com.example.demo.security.models.UserPrincipal;
import com.example.demo.security.stubs.UserDBStub;

@Component
public class UserService implements UserDetailsService{

	private Map<String, User> users = UserDBStub.users;
	private Map<String, UserCredentials> userCredentials = UserDBStub.userCredentials ;
	//private static Logger logger = Logger.getLogger(UserService.class);

	
	UserService() {
		System.out.println("initalizing userService");
		users.put("bhaskar@gmail.com", new User("bhaskar", "bhaskar@gmail.com"));
		users.put("bhaskar@gmail2.com", new User("bhaskar2", "bhaskar@gmail2.com"));
		users.put("bhaskar@gmail3.com", new User("bhaskar3", "bhaskar@gmail3.com"));
		
		userCredentials.put("bhaskar@gmail.com", new UserCredentials("bhaskar@gmail.com", true, "password"));
		userCredentials.put("bhaskar@gmail2.com", new UserCredentials("bhaskar@gmail2.com", false, "password2"));
		userCredentials.put("bhaskar@gmail3.com", new UserCredentials("bhaskar@gmail3.com", true, "password3"));

	}

	public User getUserByEmail(String email) {
		return UserDBStub.getUserByEmail(email);
	}
	
	public UserCredentials getUserCredentialsByEmail(String email) {
		return UserDBStub.getUserCredentialsByEmail(email); 
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		User user = this.getUserByEmail(username);

		if (user == null) {
			throw new UsernameNotFoundException("User not found: " + username);
		}

		UserCredentials userCredentials = this.getUserCredentialsByEmail(username);
		if (userCredentials == null) {
			throw new UsernameNotFoundException("User credentials not found");
		}

		if (!userCredentials.isEnabled()) {
			throw new DisabledException("User is not active");
		}

		Collection<? extends GrantedAuthority> authorities=null;
		 authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
		 
		return new UserPrincipal(userCredentials.getPassword(), username, true, true, true, userCredentials.isEnabled(), authorities);
					
	}

}
