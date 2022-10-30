package com.getir.readingisgood.user;


import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.getir.readingisgood.common.security.JwtService;
import com.getir.readingisgood.common.security.SecurityConstants;

@Service
public class UserService implements UserDetailsService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	JwtService jwtService;
	@Autowired
	@Lazy
	AuthenticationManager authenticationManager;
	

	
	@PostConstruct
	public void signupAdmin() {
		AppUser user = new AppUser();
		user.setUsername("admin");
		user.setPassword(bCryptPasswordEncoder.encode("pass"));
		user.setRoles("admin");
		userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getRolesAsAuthorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> getRolesAsAuthorities(String roles) {
		return Arrays.asList(roles.split(",")).stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
	}

	public AppUser saveUser(AppUser user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRoles("user");
		return userRepository.save(user);
	}
	
	public AppUser getUser(String username) {
		return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("No user with username:" + username + " found."));
	}

	public UserResponse login(AppUser user) {
		UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.unauthenticated(user.getUsername(), user.getPassword());
		Authentication authentication = authenticationManager.authenticate(authRequest);
		
		if (!authentication.isAuthenticated()) 
			return null;
		
		String authorizationToken = createAuthorizationToken(authentication);
		
		return new UserResponse(((User) authentication.getPrincipal()).getUsername(), authorizationToken);
	}

	private String createAuthorizationToken(Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		
		String username = user.getUsername();
		String authorities = user.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));
		
		Map<String, String> claims = Collections.singletonMap(SecurityConstants.CLAIM_ROLES, authorities);
		Date expiry = Date.from(Instant.now().plus(Duration.ofMinutes(120)));
		return SecurityConstants.TOKEN_PREFIX + jwtService.createJwtForClaims(username, claims, expiry);
	}
}
