package com.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeProjectDTO {

	private String employeeName;
	private String departmentName;
	private String projectUId;
	private String projectName;

}
