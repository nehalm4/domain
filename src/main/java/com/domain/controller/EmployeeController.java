package com.domain.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.domain.dto.EmployeeProjectDTO;
import com.domain.pojo.ApiResponse;
import com.domain.pojo.Employee;
import com.domain.service.EmployeeService;

/**
 * @author Nehal Mahajan
 * @apiNote Employee Data transfer controller class
 */
@RestController
@RequestMapping("/domain")
public class EmployeeController {

	private EmployeeService employeeService;

	private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

//	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/employeeList")
	public ResponseEntity<ApiResponse<List<Employee>>> employeeList() {
		log.info("Inside employeeList():::");
		return ResponseEntity.ok(employeeService.employeeList());
	}

	@GetMapping("/getEmployee")
	public ResponseEntity<ApiResponse<Employee>> getEmployeeById(@RequestParam Integer employeeId) {
		log.info("Inside getEmployeeById():::");
		return ResponseEntity.ok(employeeService.getEmployeeById(employeeId));
	}

	@PostMapping("/saveEmployee")
	public ResponseEntity<ApiResponse<String>> saveEmployee(@RequestBody Employee employee) {
		log.info("Inside saveEmployee():::");
		return ResponseEntity.ok(employeeService.saveEmployee(employee));
	}

	@GetMapping("/getCount")
	public ResponseEntity<ApiResponse<Long>> getTotalEmployeeCount() {
		log.info("Inside getTotalEmployeeCount():::");
		return ResponseEntity.ok(employeeService.getEmployeeCount());
	}

	@GetMapping("/getByActive")
	public ResponseEntity<ApiResponse<List<Employee>>> getActiveEmployeeList(@RequestParam boolean isActive) {
		log.info("Inside getActiveEmployeeList():::");
		return ResponseEntity.ok(employeeService.getActiveEmployeeList(isActive));
	}

	@GetMapping("/searchEmployee")
	public ResponseEntity<ApiResponse<List<Employee>>> searchByExample(
			@RequestParam(required = false) Integer employeeId, @RequestParam(required = false) String employeeName,
			@RequestParam(required = false) Boolean isActive) {
		log.info("Inside searchByExample():::");
		Employee employee = Employee.builder().employeeId(employeeId).employeeName(employeeName).isActive(isActive)
				.build();
		return ResponseEntity.ok(employeeService.searchByExample(employee));
	}

	@GetMapping("/employeeListPagable")
	public Page<Employee> getEmployeesPagable(Pageable pageable) {
		log.info("Inside getEmployeesPagable():::");
		return employeeService.getEmployees(pageable);
	}

	@GetMapping("/employeeDtoList")
	public ResponseEntity<ApiResponse<List<EmployeeProjectDTO>>> employeeDtoList() {
		log.info("Inside employeeDtoList():::");
		return ResponseEntity.ok(employeeService.employeeDtoList());
	}

}
