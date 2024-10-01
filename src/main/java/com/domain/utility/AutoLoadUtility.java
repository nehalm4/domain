package com.domain.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.domain.pojo.Employee;
import com.domain.repository.EmployeeRepository;

/**
 * @author Nehal Mahajan
 * @apiNote This class is responsible to load data in h2 data base on
 *          application start
 */
@Component
public class AutoLoadUtility {

	@Autowired
	private EmployeeRepository employeeRepository;

	public void autoLoad() {
		Employee employee = new Employee();

		employee.setEmployeeId(1);
		employee.setEmployeeName("Nehal");
		employee.setActive(true);

		employeeRepository.save(employee);

		employee.setEmployeeId(2);
		employee.setEmployeeName("Swati");
		employee.setActive(false);

		employeeRepository.save(employee);

	}

}
