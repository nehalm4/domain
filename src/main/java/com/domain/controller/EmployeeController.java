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

import com.domain.pojo.Employee;
import com.domain.service.EmployeeService;

@RestController
@RequestMapping("/domain")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@GetMapping("/employeeList")
	public ResponseEntity<List<Employee>> employeeList() {
		return ResponseEntity.ok(employeeService.employeeList());
	}

	@GetMapping("/getEmployee")
	public ResponseEntity<Employee> getEmployeeById(@RequestParam Integer employeeId) {
		return ResponseEntity.ok(employeeService.getEmployeeById(employeeId));
	}

	@PostMapping("/saveEmployee")
	public String saveEmployee(@RequestBody Employee employee) {
		employeeService.saveEmployee(employee);
		return "Employee Save Successfuly";
	}
	
	@GetMapping("/getCount")
	public long getTotalEmployeeCount() {
		return employeeService.getEmployeeCount();
	}
	
	@GetMapping("/getActiveCount")
	public ResponseEntity<List<Employee>> getActiveEmployeeList(@RequestParam boolean isActive) {
		 return ResponseEntity.ok(employeeService.getActiveEmployeeList(isActive));
	}

}
