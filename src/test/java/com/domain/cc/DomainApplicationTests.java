package com.domain.cc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.domain.pojo.Address;
import com.domain.pojo.ApiResponse;
import com.domain.pojo.Department;
import com.domain.pojo.Employee;
import com.domain.pojo.Project;
import com.domain.service.EmployeeService;
import com.domain.utility.Constants;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
class DomainApplicationTests {

	@Mock
	private EmployeeService employeeService;

	@InjectMocks
	private DomainApplicationTests testClass;

	@Test
	public void testEmployeeList() {
		// Mock departments
		Department department1 = new Department();
		department1.setDepartmentName("HR");

		Department department2 = new Department();
		department2.setDepartmentName("Finance");

		// Mock projects
		Project project1 = new Project();
		project1.setProjectUId("P001NX");
		project1.setProjectName("RESOURCE FULFILLMENT");

		Project project2 = new Project();
		project2.setProjectUId("NGINX2187");
		project2.setProjectName("BMW");

		// Mock employees
		Employee employee1 = new Employee();
		employee1.setEmployeeName("Nehal");
		employee1.setIsActive(true);
		employee1.setAddress(Address.builder().buildingNumber(201).area("Mahal").city("Nagpur").pincode(440032L)
				.country("India").state("Maharashtra").build());
		employee1.setDepartment(department1);
		employee1.setProject(Collections.singletonList(project1));

		Employee employee2 = new Employee();
		employee2.setEmployeeName("Swati");
		employee2.setIsActive(false);
		employee2.setAddress(Address.builder().buildingNumber(301).area("Nandavan").city("Nagpur").pincode(430032L)
				.country("India").state("Maharashtra").build());
		employee2.setDepartment(department2);

		// Mock employee list
		List<Employee> mockEmployeeList = new LinkedList<>();
		mockEmployeeList.add(employee1);
		mockEmployeeList.add(employee2);

		// Mock the service method
		when(employeeService.employeeList())
				.thenReturn(new ApiResponse<>(200, Constants.SUCCESS.toString(), mockEmployeeList));

		// Call the method and validate
		ApiResponse<List<Employee>> response = employeeService.employeeList();
		assertNotNull(response, "Response should not be null");
		assertNotNull(response.getData(), "Employee list should not be null");
		assertEquals(2, response.getData().size(), "Employee list size should match the mock data");

		// Validate individual employee details
		Employee firstEmployee = response.getData().get(0);
		assertEquals("Nehal", firstEmployee.getEmployeeName(), "First employee name should match");
		assertEquals("HR", firstEmployee.getDepartment().getDepartmentName(), "First employee department should match");

		Employee secondEmployee = response.getData().get(1);
		assertEquals("Swati", secondEmployee.getEmployeeName(), "Second employee name should match");
		assertEquals("Finance", secondEmployee.getDepartment().getDepartmentName(),
				"Second employee department should match");
	}

	@Disabled
	@ParameterizedTest
	@ValueSource(ints = { 1, 2 }) // IDs that are present in the database
	public void testGetEmployeeById_Existing(Integer employeeId) {
		ApiResponse<Employee> testObj = employeeService.getEmployeeById(employeeId);
		assertNotNull(testObj.getData(), "Employee should be present for ID: " + employeeId);
	}

	@Disabled
	@ParameterizedTest
	@ValueSource(ints = { -1, 33 }) // IDs that are not present in the database
	public void testGetEmployeeById_NonExisting(Integer employeeId) {
		ApiResponse<Employee> testObj = employeeService.getEmployeeById(employeeId);
		assertNull(testObj.getData(), "Employee should not be present for ID: " + employeeId);
	}

}
