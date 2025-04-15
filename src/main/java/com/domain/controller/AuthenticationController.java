package com.domain.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.domain.dto.LoginUserDto;
import com.domain.dto.SignUp;
import com.domain.pojo.ApiResponse;
import com.domain.service.CustomUserDetailsService;

/**
 * @author Nehal Mahajan
 * @apiNote Authentication Controller
 */
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	private final CustomUserDetailsService detailsService;

	private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);

	@Autowired
	public AuthenticationController(CustomUserDetailsService detailsService) {
		this.detailsService = detailsService;
	}

	/**
	 * Register a new user
	 */
	@PostMapping("/users")
	public ResponseEntity<ApiResponse<String>> registerUser(@RequestBody SignUp signUp) {
		log.info("Inside registerUser() :::");
		return ResponseEntity.ok(detailsService.saveUser(signUp));
	}

	/**
	 * Authenticate existing user and return token
	 */
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginUserDto loginUserDto) {
		log.info("Inside login() :::");
		return ResponseEntity.ok(detailsService.authenticateUser(loginUserDto));
	}
}
