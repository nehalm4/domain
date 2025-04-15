package com.domain.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.domain.dto.EmployeeProjectDTO;
import com.domain.multitasking.Multitasking;
import com.domain.pojo.ApiResponse;
import com.domain.pojo.Employee;
import com.domain.service.EmployeeService;

/**
 * @author Nehal Mahajan
 * @apiNote Employee Data transfer controller class
 */
@RestController
@RequestMapping("/api/v1/employees")  // ✅ Resource-style plural naming
public class EmployeeController {

	private final EmployeeService employeeService;

	private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private Multitasking multitasking;

	@Autowired
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@GetMapping("/multitasking")
	public void startMultiTasking() throws InterruptedException, ExecutionException {
		log.info("Inside startMultiTasking()");
		multitasking.generateReport1();
	}

	@GetMapping("/redirect")
	public RedirectView redirectToGoogle() {
		return new RedirectView("https://www.google.com");
	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<Employee>>> getAllEmployees() {
		log.info("Inside getAllEmployees()");
		return ResponseEntity.ok(employeeService.employeeList());
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<Employee>> getEmployeeById(@PathVariable("id") Integer employeeId) {
		log.info("Inside getEmployeeById()");
		return ResponseEntity.ok(employeeService.getEmployeeById(employeeId));
	}

	@PostMapping
	public ResponseEntity<ApiResponse<String>> createEmployee(@RequestBody Employee employee) {
		log.info("Inside createEmployee()");
		return ResponseEntity.ok(employeeService.saveEmployee(employee));
	}

	@GetMapping("/count")
	public ResponseEntity<ApiResponse<Long>> getTotalEmployeeCount() {
		log.info("Inside getTotalEmployeeCount()");
		return ResponseEntity.ok(employeeService.getEmployeeCount());
	}

	@GetMapping("/filter")
	public ResponseEntity<ApiResponse<List<Employee>>> getActiveEmployeeList(@RequestParam boolean isActive) {
		log.info("Inside getActiveEmployeeList()");
		return ResponseEntity.ok(employeeService.getActiveEmployeeList(isActive));
	}

	@GetMapping("/search")
	public ResponseEntity<ApiResponse<List<Employee>>> searchByExample(
			@RequestParam(required = false) Integer employeeId,
			@RequestParam(required = false) String employeeName,
			@RequestParam(required = false) Boolean isActive) {

		log.info("Inside searchByExample()");
		Employee employee = Employee.builder()
				.employeeId(employeeId)
				.employeeName(employeeName)
				.isActive(isActive)
				.build();

		return ResponseEntity.ok(employeeService.searchByExample(employee));
	}

	@GetMapping("/pageable")
	public Page<Employee> getEmployeesPagable(Pageable pageable) {
		log.info("Inside getEmployeesPagable()");
		return employeeService.getEmployees(pageable);
	}

	@GetMapping("/dto")
	public ResponseEntity<ApiResponse<List<EmployeeProjectDTO>>> getEmployeeDtoList() {
		log.info("Inside getEmployeeDtoList()");
		return ResponseEntity.ok(employeeService.employeeDtoList());
	}
}
