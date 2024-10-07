package com.domain.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Nehal Mahajan
 * @apiNote Common APi Response for API output
 * @param <T> any type of data
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse<T> {

	private int status;
	private String message;
	private T data;
	private String timestamp = java.time.LocalDateTime.now().toString();

	public ApiResponse(int status, String message, T data) {
		this.status = status;
		this.message = message;
		this.data = data;
		this.timestamp = java.time.LocalDateTime.now().toString();
	}
}
