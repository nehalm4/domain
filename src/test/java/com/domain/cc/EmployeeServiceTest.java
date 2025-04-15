package com.domain.cc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.domain.pojo.Address;
import com.domain.pojo.ApiResponse;
import com.domain.pojo.Department;
import com.domain.pojo.Employee;
import com.domain.pojo.Project;
import com.domain.repository.EmployeeRepository;
import com.domain.service.EmployeeService;
import com.domain.utility.Constants;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

	@Mock
	private EmployeeRepository employeeRepository;

	@InjectMocks
	private EmployeeService employeeService;

	private Employee employee;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		Department department1 = new Department();
		department1.setDepartmentId(1l);
		department1.setDepartmentName("HR");

		Project project1 = new Project();
		project1.setProjectUId("P001NX");
		project1.setProjectName("RESOURCE FULLFILMENT");

		employee = new Employee();

		employee.setEmployeeId(1);
		employee.setEmployeeName("Nehal");
		employee.setIsActive(true);
		Address address = Address.builder().buildingNumber(201).area("Mahal").city("Nagpur").pincode(440032L)
				.country("India").state("Maharashtra").build();
		address.setBuildingNumber(201);
		address.setArea("Mahal");
		address.setCity("Nagpur");
		address.setPincode(440032L);
		address.setCountry("India");
		address.setState("Maharashtra");
		employee.setAddress(address);
		employee.setDepartment(department1);
		employee.setProject(Collections.singletonList(project1));
		employeeRepository.save(employee);

	}

	@Test
	void testSaveEmployee() {
		when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
		ApiResponse<String> saved = employeeService.saveEmployee(employee);
		assertNotNull(saved);
		assertEquals(Constants.SUCCESS.toString(), saved.getMessage());
		assertEquals(200, saved.getStatus());
		verify(employeeRepository, times(2)).save(employee);
	}

	@Test
	void testEmployeeList() {
		List<Employee> employees = Arrays.asList(new Employee(1, "John", true, null, null, null),
				new Employee(2, "Jane", false, null, null, null));

		when(employeeRepository.findAll()).thenReturn(employees);

		ApiResponse<List<Employee>> response = employeeService.employeeList();

		assertEquals(200, response.getStatus());
		assertEquals(Constants.SUCCESS.toString(), response.getMessage());
		assertEquals(employees, response.getData());
	}

	@Test
	void testGetEmployeeById() {
		Employee employee = new Employee(1, "John", true, null, null, null);
		when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));

		ApiResponse<Employee> response = employeeService.getEmployeeById(1);

		assertEquals(200, response.getStatus());
		assertEquals(Constants.SUCCESS.toString(), response.getMessage());
		assertEquals(employee, response.getData());
	}

	@Test
	void testGetEmployeeByIdNotFound() {
		when(employeeRepository.findById(1)).thenReturn(Optional.empty());

		ApiResponse<Employee> response = employeeService.getEmployeeById(1);

		assertEquals(200, response.getStatus());
		assertEquals(Constants.SUCCESS.toString(), response.getMessage());
		assertEquals(null, response.getData());
		verify(employeeRepository, times(1)).findById(1); // Verify the method was called
	}

	@Test
	void testGetEmployeeCount() {
		when(employeeRepository.count()).thenReturn((long) 2);
		ApiResponse<Long> count = employeeService.getEmployeeCount();
		assertNotNull(count);
		assertEquals(2, count.getData());
		assertEquals(200, count.getStatus());
		assertEquals(Constants.SUCCESS.toString(), count.getMessage().toString());
	}

}
