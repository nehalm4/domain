package com.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domain.pojo.Employee;
import com.domain.repository.EmployeeRepository;

/**
 * @author Nehal Mahajan
 * @apiNote This is Employee Service Class
 */
@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	public List<Employee> employeeList() {
		return employeeRepository.findAll();
	}

	public Employee getEmployeeById(Integer employeeId) {
		return employeeRepository.findById(employeeId).get();
	}

	public void saveEmployee(Employee employee) {
		employeeRepository.save(employee);
	}

	public long getEmployeeCount() {
		return employeeRepository.count();
	}

	public List<Employee> getActiveEmployeeList(boolean isActive) {
		return employeeRepository.getEmployeeByIsActive(isActive);
	}

}
