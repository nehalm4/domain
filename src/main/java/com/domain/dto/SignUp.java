package com.domain.dto;

import lombok.Data;
import lombok.Getter;

/**
 * @author Nehal Mahajan
 * @apiNote Signup Dto Class
 */
@Data
@Getter
public class SignUp {

	private String username;
	private String email;
	private String password;
}
