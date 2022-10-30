package com.getir.readingisgood.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {
	@Autowired
	UserService userService;
	
	@PostMapping("/signup")
	public UserResponse signup(@RequestBody AppUser user) {
		userService.saveUser(user);
		return userService.login(user);
	}
	
	@PostMapping("/login")
	public UserResponse login(@RequestBody AppUser user) {
		return userService.login(user);
	}
}
