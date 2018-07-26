package com.example.demo.security.filters;

import java.io.IOException;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.example.demo.security.constants.SecurityConstants;
import com.example.demo.security.jwt.JwtToken;
import com.example.demo.security.jwt.TokenAuthService;
import com.example.demo.security.models.UserPrincipal;
import com.example.demo.security.uitls.TokenExtractor;

public class TokenAuthFilter extends AbstractAuthenticationProcessingFilter {

	TokenExtractor headerTokenExtractor;
	TokenAuthService JwtTokenAuthServiceImpl;

	public TokenAuthFilter(RequestMatcher requiresAuthenticationRequestMatcher,TokenExtractor headerTokenExtractor,
	TokenAuthService JwtTokenAuthServiceImpl) {
		super(requiresAuthenticationRequestMatcher);
		this.headerTokenExtractor=headerTokenExtractor;
		this.JwtTokenAuthServiceImpl=JwtTokenAuthServiceImpl;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		// extract token from header
		String header = request.getHeader(SecurityConstants.JWT_TOKEN_HEADER_PARAM);
		String jwtToken = headerTokenExtractor.extract(header);
		// token expiration will be checked here
		Map<String, String> tokenClaims = JwtTokenAuthServiceImpl.validateToken(jwtToken);
		if (tokenClaims == null) {
			throw new AuthenticationServiceException("tokenClaims cannot be blank!");

		}
		// create Authentication
		UserPrincipal userPrincipal = new UserPrincipal(tokenClaims.get("Subject"), tokenClaims.get("ID"), false);
		JwtToken authRequest = new JwtToken(userPrincipal, tokenClaims);

		// call authmanager to validate token
		return this.getAuthenticationManager().authenticate(authRequest);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(authResult);
		SecurityContextHolder.setContext(context);
		chain.doFilter(request, response);
		System.out.println("token successfulAuthentication");
		return;

	}
	
	
	public TokenExtractor getHeaderTokenExtractor() {
		return headerTokenExtractor;
	}

	public void setHeaderTokenExtractor(TokenExtractor headerTokenExtractor) {
		this.headerTokenExtractor = headerTokenExtractor;
	}

	public TokenAuthService getJwtTokenAuthServiceImpl() {
		return JwtTokenAuthServiceImpl;
	}

	public void setJwtTokenAuthServiceImpl(TokenAuthService jwtTokenAuthServiceImpl) {
		JwtTokenAuthServiceImpl = jwtTokenAuthServiceImpl;
	}
}
