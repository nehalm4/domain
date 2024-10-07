package com.domain.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Nehal Mahajan
 * @apiNote Employee pojo class
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@Getter
@ToString
@Builder
@Table(name = "Employee")
public class Employee {

	@Id
	@Column(name = "EMPLOYEE_ID")
	private Integer employeeId;
	@Column(name = "EMPLOYEE_NAME")
	private String employeeName;
	@Column(name = "ACTIVE")
	private Boolean isActive;

}
