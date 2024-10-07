package com.domain.pojo;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author Nehal Mahajan
 * @apiNote Role Enum for Security
 */
public enum Role implements GrantedAuthority {
	ROLE_USER("USER"), ROLE_ADMIN("ADMIN");

	private String value;

	Role(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	@Override
	public String getAuthority() {
		return name();
	}
}
