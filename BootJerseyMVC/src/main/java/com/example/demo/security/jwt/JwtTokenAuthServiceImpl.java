package com.example.demo.security.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenAuthServiceImpl implements TokenAuthService {

	private static final String ISSUER = "JwtTokenAuthServiceImpl";
	private static final String signingKey = "secretKeyInitial";

	@Override
	public String createToken(String userName, String role, long timeToLive) {
		// TODO Auto-generated method stub

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		// Let's set the JWT Claims
		JwtBuilder builder = Jwts.builder().setId(userName).setIssuedAt(now).setSubject(role).setIssuer(ISSUER)
				.signWith(SignatureAlgorithm.HS256, signingKey);

		if (timeToLive >= 0) {
			long expMillis = nowMillis + timeToLive;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
		}

		return builder.compact();
	}

	@Override
	public Map<String, String> validateToken(String jwtToken) {
		Claims claims;
		Map<String, String> tokenClaims = new HashMap();
		try {
			claims = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(jwtToken).getBody();

			tokenClaims.put("ID", claims.getId());
			tokenClaims.put("Subject", claims.getSubject());
			tokenClaims.put("Issuer", claims.getIssuer());
			tokenClaims.put("Expiration", claims.getExpiration().toString());

			System.out.println("ID: " + claims.getId());
			System.out.println("Subject: " + claims.getSubject());
			System.out.println("Issuer: " + claims.getIssuer());
			System.out.println("Expiration: " + claims.getExpiration());
		} catch (JwtException e) {
			System.out.println("JwtException: " + e);
			if (e instanceof ExpiredJwtException) {
				System.out.println("ExpiredJwtException: Token Expired:::" + e);
			}
			return null;
		}

		return tokenClaims;
	}

	@Override
	public String updateToken() {
		// TODO Auto-generated method stub
		return null;
	}

}
