package com.domain.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Nehal Mahajan
 * @apiNote Common Error response API class
 */
@Data
@Builder
@AllArgsConstructor
public class ErrorResponse {
	private LocalDateTime timestamp;
	private String message;
	private String details;
	private int statusCode;
}
