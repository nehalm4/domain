package com.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.domain.pojo.ApiResponse;
import com.domain.pojo.Employee;
import com.domain.repository.EmployeeRepository;

/**
 * @author Nehal Mahajan
 * @apiNote Employee Service class for employee data
 */
@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	public ApiResponse<List<Employee>> employeeList() {
		ApiResponse<List<Employee>> response = new ApiResponse<>(200, "Success", employeeRepository.findAll());
		return response;
	}

	public ApiResponse<Employee> getEmployeeById(Integer employeeId) {
		ApiResponse<Employee> apiResponse = new ApiResponse<>(200, "Success",
				employeeRepository.findById(employeeId).get());
		return apiResponse;
	}

	public ApiResponse<String> saveEmployee(Employee employee) {
		try {
			employeeRepository.save(employee);
			ApiResponse<String> apiResponse = new ApiResponse<>(200, "Success", "Employee Save Successfuly");
			return apiResponse;
		} catch (Exception e) {
			ApiResponse<String> apiResponse = new ApiResponse<>(500, "Failed", "Something went wrong try again.");
			return apiResponse;
		}
	}

	public ApiResponse<Long> getEmployeeCount() {
		ApiResponse<Long> apiResponse = new ApiResponse<>(200, "Success", employeeRepository.count());
		return apiResponse;
	}

	public ApiResponse<List<Employee>> getActiveEmployeeList(boolean isActive) {
		ApiResponse<List<Employee>> apiResponse = new ApiResponse<>(200, "Success",
				employeeRepository.getEmployeeByIsActive(isActive));
		return apiResponse;
	}

	public ApiResponse<List<Employee>> searchByExample(Employee employee) {
		ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase()
				.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

		Example<Employee> emplExample = Example.of(employee, matcher);
		List<Employee> employees = employeeRepository.findAll(emplExample);

		return new ApiResponse<>(200, "Success", employees);
	}

	public Page<Employee> getEmployees(Pageable pageable) {
		return employeeRepository.findAll(pageable);
	}

}
