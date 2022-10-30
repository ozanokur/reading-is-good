package com.getir.readingisgood.common.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JWTAuthorizationFilter extends OncePerRequestFilter {
	JwtService jwtService;

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JwtService jwtService) {
		super();
		setJwtService(jwtService);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		String bearerToken = req.getHeader(SecurityConstants.HEADER_AUTHORIZATION);

		if (bearerToken == null || !bearerToken.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			chain.doFilter(req, res);
			return;
		}
		UsernamePasswordAuthenticationToken authentication = null;
		try {
			authentication = getAuthentication(bearerToken);
		} catch (TokenExpiredException e) {
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, res);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(String bearerToken) {
		bearerToken = bearerToken.substring(7);
		DecodedJWT jwt;
		jwt = jwtService.parseJwt(bearerToken);
		List<GrantedAuthority> authorizations = Arrays.asList((jwt.getClaim(SecurityConstants.CLAIM_ROLES).asString()).split(","))
				.stream().filter(StringUtils::hasLength).map(authority -> new SimpleGrantedAuthority(authority))
				.collect(Collectors.toList());

		return new UsernamePasswordAuthenticationToken(jwt.getSubject(), null, authorizations);
	}
}