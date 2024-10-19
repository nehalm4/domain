package com.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.domain.dto.EmployeeProjectDTO;
import com.domain.pojo.ApiResponse;
import com.domain.pojo.Employee;
import com.domain.repository.EmployeeRepository;
import com.domain.utility.Constants;

/**
 * @author Nehal Mahajan
 * @apiNote Employee Service class for employee data
 */
@Service
public class EmployeeService {

	private EmployeeRepository employeeRepository;

	@Autowired
	public EmployeeService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	public ApiResponse<List<Employee>> employeeList() {
		return new ApiResponse<>(200, Constants.SUCCESS.toString(), employeeRepository.findAll());
	}

	public ApiResponse<Employee> getEmployeeById(Integer employeeId) {
		return new ApiResponse<>(200, Constants.SUCCESS.toString(),
				employeeRepository.findById(employeeId).orElse(null));
	}

	public ApiResponse<String> saveEmployee(Employee employee) {
		try {
			employeeRepository.save(employee);
			return new ApiResponse<>(200, Constants.SUCCESS.toString(), "Employee Save Successfuly");
		} catch (Exception e) {
			return new ApiResponse<>(500, "Failed", "Something went wrong try again.");
		}
	}

	public ApiResponse<Long> getEmployeeCount() {
		return new ApiResponse<>(200, Constants.SUCCESS.toString(), employeeRepository.count());
	}

	public ApiResponse<List<Employee>> getActiveEmployeeList(boolean isActive) {
		return new ApiResponse<>(200, Constants.SUCCESS.toString(), employeeRepository.getEmployeeByIsActive(isActive));
	}

	public ApiResponse<List<Employee>> searchByExample(Employee employee) {
		ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase()
				.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

		Example<Employee> emplExample = Example.of(employee, matcher);
		List<Employee> employees = employeeRepository.findAll(emplExample);

		return new ApiResponse<>(200, Constants.SUCCESS.toString(), employees);
	}

	public Page<Employee> getEmployees(Pageable pageable) {
		return employeeRepository.findAll(pageable);
	}

	public ApiResponse<List<EmployeeProjectDTO>> employeeDtoList() {
		return new ApiResponse<>(200, Constants.SUCCESS.toString(), employeeRepository.findEmployeeProjectDetails());
	}

}
