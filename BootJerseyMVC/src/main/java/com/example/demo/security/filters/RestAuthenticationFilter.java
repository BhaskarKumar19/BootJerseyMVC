package com.example.demo.security.filters;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.glassfish.jersey.internal.util.Base64;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.example.demo.security.jwt.TokenAuthService;
import com.example.demo.security.models.UserPrincipal;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RestAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private static Logger logger = Logger.getLogger(RestAuthenticationFilter.class);
	private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
	private static final String AUTHORIZATION_HEADER_PREIX = "Basic ";
	
	TokenAuthService jwtTokenAuthServiceImpl;

	

	public RestAuthenticationFilter(String defaultFilterProcessesUrl,TokenAuthService jwtTokenAuthServiceImpl) {
		super(defaultFilterProcessesUrl);
		this.jwtTokenAuthServiceImpl=jwtTokenAuthServiceImpl;
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse resp)
			throws AuthenticationException, IOException, ServletException {

		if (!HttpMethod.POST.name().equals(request.getMethod())) {

			// logger.debug("Authentication method not supported. Request
			// method: " + request.getMethod());

			// throw new AuthMethodNotSupportedException("Authentication method
			// not supported");
		}

		/*
		 * String userName= (String) request.getParameter("userName"); String
		 * pwd= (String) request.getParameter("pwd"); String type= (String)
		 * request.getParameter("type"); if(StringUtils.isEmpty(userName) ||
		 * StringUtils.isEmpty(pwd) || StringUtils.isEmpty(type)){
		 * userName="dummy"; pwd="dummy"; type="dummy"; }
		 */

			String authHeader = request.getHeader(AUTHORIZATION_HEADER_KEY);
	
			String authToken = authHeader;
			System.out.println(authToken);
			authToken = authToken.replaceFirst(AUTHORIZATION_HEADER_PREIX, "");
			System.out.println(authToken);
			String decodedString = Base64.decodeAsString(authToken);
			System.out.println(decodedString);
			StringTokenizer tokenizer = new StringTokenizer(decodedString, ":");
			String type="user";
			String username = tokenizer.nextToken();
			String password = tokenizer.nextToken();
			
			
			/*String type="user";
			String username = "bhaskar@gmail3.com";
			String password = "password3";*/
			
			
			System.out.println("Username= " + username + "  password= " + password);

			UserPrincipal userPrincipal = new UserPrincipal(password, username, false);
			logger.debug("user= " + username + " pwd= " + password);

			//return new UsernamePasswordAuthenticationToken(userPrincipal, password);
			
			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
					userPrincipal, password);
			
			return this.getAuthenticationManager().authenticate(authRequest);
		
	}
	

	 	@Override
	    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
	                                            Authentication authResult) throws IOException, ServletException {
	        
	 		SecurityContext context = SecurityContextHolder.createEmptyContext();
	        context.setAuthentication(authResult);
	        SecurityContextHolder.setContext(context);
	       
	        
	        //chain.doFilter(request, response);   // why is this important
	        
	        UserPrincipal userPrincipal=(UserPrincipal) authResult.getPrincipal();
	                                  
	                                     
	        GrantedAuthority  role=  userPrincipal.getAuthorities().iterator().next();               
	        Long timeToLive=666666L;
	        String generatedToken= getJwtTokenAuthServiceImpl().createToken(userPrincipal.getUsername(), role.getAuthority(), timeToLive);
	        
	        response.setStatus(HttpStatus.OK.value());
	        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
	        //mapper.writeValue(response.getWriter(), tokenMap);
	        
	        ObjectMapper mapper=new ObjectMapper();
	        Map<String,String>map=new HashMap<>();
	        map.put("X-Authorization: ", "Bearer "+generatedToken);
	        mapper.writeValue(response.getWriter(), map);
	        //response.getWriter().write("X-Authorization: Bearer "+generatedToken);
	        
			System.out.println("successfulAuthentication");
			return;
	 	
	 	}
	 	
	 	public TokenAuthService getJwtTokenAuthServiceImpl() {
			return jwtTokenAuthServiceImpl;
		}

		public void setJwtTokenAuthServiceImpl(TokenAuthService jwtTokenAuthServiceImpl) {
			this.jwtTokenAuthServiceImpl = jwtTokenAuthServiceImpl;
		}
	
}
