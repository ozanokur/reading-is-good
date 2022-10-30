package com.getir.readingisgood.common.security;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class JwtService {
	
	@Value("${jwt.secret:secret}")
	private String SECRET_KEY;
	
	public String createJwtForClaims(String subject, Map<String, String> claims, Date expiry) {
		
		JWTCreator.Builder jwtBuilder = JWT.create().withSubject(subject);
		
		claims.forEach(jwtBuilder::withClaim);
		
		return jwtBuilder
				.withExpiresAt(expiry)
				.sign(Algorithm.HMAC256(SECRET_KEY));
	}
	
	public DecodedJWT parseJwt(String token) {
		return JWT.require(Algorithm.HMAC256(SECRET_KEY)).build().verify(token);
	}
	
}
