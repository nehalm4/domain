package com.domain.controller;

import java.util.List;

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

	@Autowired
	private EmployeeService employeeService;

//	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/employeeList")
	public ResponseEntity<ApiResponse<List<Employee>>> employeeList() {
		return ResponseEntity.ok(employeeService.employeeList());
	}

	@GetMapping("/getEmployee")
	public ResponseEntity<ApiResponse<Employee>> getEmployeeById(@RequestParam Integer employeeId) {
		return ResponseEntity.ok(employeeService.getEmployeeById(employeeId));
	}

	@PostMapping("/saveEmployee")
	public ResponseEntity<ApiResponse<String>> saveEmployee(@RequestBody Employee employee) {
		return ResponseEntity.ok(employeeService.saveEmployee(employee));
	}

	@GetMapping("/getCount")
	public ResponseEntity<ApiResponse<Long>> getTotalEmployeeCount() {
		return ResponseEntity.ok(employeeService.getEmployeeCount());
	}

	@GetMapping("/getActiveCount")
	public ResponseEntity<ApiResponse<List<Employee>>> getActiveEmployeeList(@RequestParam boolean isActive) {
		return ResponseEntity.ok(employeeService.getActiveEmployeeList(isActive));
	}

	@GetMapping("/searchEmployee")
	public ResponseEntity<ApiResponse<List<Employee>>> searchByExample(
			@RequestParam(required = false) Integer employeeId, @RequestParam(required = false) String employeeName,
			@RequestParam(required = false) Boolean isActive) {
		Employee employee = Employee.builder().employeeId(employeeId).employeeName(employeeName).isActive(isActive)
				.build();
		return ResponseEntity.ok(employeeService.searchByExample(employee));
	}
	
    @GetMapping("/employeeListPagable")
    public Page<Employee> getEmployeesPagable(Pageable pageable) {
        return employeeService.getEmployees(pageable);
    }

}
