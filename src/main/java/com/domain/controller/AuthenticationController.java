package com.domain.controller;

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
@RequestMapping("/auth")
@RestController
public class AuthenticationController {

	@Autowired
	private CustomUserDetailsService detailsService;

	@PostMapping("/addUser")
	public ResponseEntity<ApiResponse<String>> addUser(@RequestBody SignUp signUp) {
		return ResponseEntity.ok(detailsService.saveUser(signUp));
	}

	@PostMapping("/token")
	public ResponseEntity<String> authenticate(@RequestBody LoginUserDto loginUserDto) {
		return ResponseEntity.ok(detailsService.authenticateUser(loginUserDto));

	}

}
