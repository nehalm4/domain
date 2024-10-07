package com.domain.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.domain.dto.LoginUserDto;
import com.domain.dto.SignUp;
import com.domain.pojo.ApiResponse;
import com.domain.pojo.User;
import com.domain.repository.UserRepository;

/**
 * @author Nehal Mahajan
 * @apiNote Custom User details service for Spring Security
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtService jwtService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found....."));
		Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

		return org.springframework.security.core.userdetails.User.builder().username(user.getUsername())
				.password(user.getPassword()).authorities(authorities).build();
	}

	public String authenticateUser(LoginUserDto userDto) throws UsernameNotFoundException {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserDetails authenticatedUser = loadUserByUsername(userDto.getUsername());
		String jwtToken = jwtService.generateToken(authenticatedUser);
		return jwtToken;
	}

	public ApiResponse<String> saveUser(SignUp signUp) {
		if (userRepository.findByUsername(signUp.getUsername()).isPresent()) {
			return ApiResponse.<String>builder().status(HttpStatus.CONFLICT.value()).message("Username Already Exists")
					.data(null).timestamp(java.time.LocalDateTime.now().toString()).build();
		} else if (userRepository.findByEmail(signUp.getEmail()).isPresent()) {
			return ApiResponse.<String>builder().status(HttpStatus.CONFLICT.value()).message("Email Already Exists")
					.data(null).timestamp(java.time.LocalDateTime.now().toString()).build();
		} else {
			User userObj = User.builder().username(signUp.getUsername()).email(signUp.getEmail())
					.password(passwordEncoder.encode(signUp.getPassword())).build();
			userRepository.save(userObj);

			return ApiResponse.<String>builder().status(HttpStatus.CREATED.value()).message("User Saved Successfully")
					.data(null).timestamp(java.time.LocalDateTime.now().toString()).build();
		}
	}

}
