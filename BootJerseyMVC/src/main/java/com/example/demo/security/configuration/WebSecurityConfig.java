package com.example.demo.security.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.security.filters.RestAuthenticationFilter;
import com.example.demo.security.filters.SkipPathRequestMatcher;
import com.example.demo.security.filters.TokenAuthFilter;
import com.example.demo.security.jwt.JwtTokenAuthServiceImpl;
import com.example.demo.security.providers.RestAuthenticationProvider;
import com.example.demo.security.providers.TokenAuthProvider;
import com.example.demo.security.providers.UserNamePasswordAuthProvider;
import com.example.demo.security.uitls.HeaderTokenExtractor;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private RestAuthenticationProvider restAuthenticationProvider;
	
	@Autowired
	private UserNamePasswordAuthProvider userNamePasswordAuthProvider;
	
	
	@Autowired
	private TokenAuthProvider tokenAuthProvider;
	
	@Autowired
	@Qualifier("userService")
	private UserDetailsService userDetailsService;
	

	@Autowired
	private AuthenticationManager authenticationManager;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	

	// don't add @Bean annotation otherwise additional authentication
	// RestAuthenticationFilter will be registered
	protected RestAuthenticationFilter buildRestAuthenticationFilter() throws Exception {
		RestAuthenticationFilter filter = new RestAuthenticationFilter("/api/login", new JwtTokenAuthServiceImpl()); 
		filter.setAuthenticationManager(this.authenticationManager);
		return filter;
	}
	
	
	protected TokenAuthFilter buildTokenAuthFilter() throws Exception {
		
		List<String> pathsToSkip = new ArrayList(Arrays.asList("/api/login"));
        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, "/api/**");
				
		TokenAuthFilter filter = new TokenAuthFilter(matcher, new HeaderTokenExtractor(), new JwtTokenAuthServiceImpl() );
		filter.setAuthenticationManager(this.authenticationManager);
		return filter;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// configure filters
		http.addFilterBefore(buildRestAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		http.addFilterBefore(buildTokenAuthFilter(), UsernamePasswordAuthenticationFilter.class);

		//http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		// configure authentication providers
		// http.authenticationProvider(restAuthenticationProvider);

		// disable csrf
		http.csrf().disable();

		// http.formLogin();

		http.authorizeRequests().anyRequest().authenticated();

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		    //auth.authenticationProvider(restAuthenticationProvider);
		
			userNamePasswordAuthProvider.setUserDetailsService(userDetailsService);
			auth.authenticationProvider(userNamePasswordAuthProvider);
			auth.authenticationProvider(tokenAuthProvider);
			
		
	}

	/*
	 * @Autowired public void configureGlobal(AuthenticationManagerBuilder auth)
	 * throws Exception { auth .inMemoryAuthentication()
	 * .withUser("user").password("password").roles("USER"); }
	 */
	
	
	
	@Autowired
	public void configureGlobal()  {
		
		System.out.println("in security config");
		System.out.println(userDetailsService);

		

	}
	 
}
