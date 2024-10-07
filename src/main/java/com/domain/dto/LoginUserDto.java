package com.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Nehal Mahajan
 * @apiNote Login Dto Class
 */
@Getter
@Setter
@ToString
public class LoginUserDto {

	private String username;
	private String password;
}
