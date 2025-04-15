package com.domain.cc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.domain.controller.EmployeeController;
import com.domain.dto.EmployeeProjectDTO;
import com.domain.pojo.ApiResponse;
import com.domain.pojo.Employee;
import com.domain.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

	@Mock
	private EmployeeService employeeService;

	@InjectMocks
	private EmployeeController employeeController;

	private MockMvc mockMvc;
	private ObjectMapper objectMapper;

	@BeforeEach
	void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
		objectMapper = new ObjectMapper();
	}

	@Test
	void testEmployeeList() throws Exception {
		List<Employee> mockEmployees = Arrays.asList(new Employee(1, "John", true, null, null, null),
				new Employee(2, "Jane", false, null, null, null));
		ApiResponse<List<Employee>> mockResponse = new ApiResponse<>(200, "Success", mockEmployees);

		when(employeeService.employeeList()).thenReturn(mockResponse);

		mockMvc.perform(get("/domain/employeeList")).andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200)).andExpect(jsonPath("$.message").value("Success"))
				.andExpect(jsonPath("$.data[0].employeeName").value("John"));
	}

	@Test
	void testGetEmployeeByIdIfPresent() throws Exception {
		Employee mockEmployee = new Employee(1, "John", true, null, null, null);
		ApiResponse<Employee> mockResponse = new ApiResponse<>(200, "Success", mockEmployee);

		when(employeeService.getEmployeeById(1)).thenReturn(mockResponse);

		mockMvc.perform(get("/domain/getEmployee").param("employeeId", "1")).andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200)).andExpect(jsonPath("$.message").value("Success"))
				.andExpect(jsonPath("$.data.employeeName").value("John"));
	}

	@Test
	void testGetEmployeeByIdIfNotPresent() throws Exception {
		ApiResponse<Employee> mockResponse = new ApiResponse<>(404, "Employee not found", null);
		when(employeeService.getEmployeeById(999)).thenReturn(mockResponse);

		mockMvc.perform(get("/domain/getEmployee").param("employeeId", "999")).andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(404)).andExpect(jsonPath("$.message").value("Employee not found"))
				.andExpect(jsonPath("$.data").isEmpty());
	}

	@Test
	void testSaveEmployeeSuccess() throws Exception {
		Employee mockEmployee = new Employee(1, "John", true, null, null, null);
		ApiResponse<String> mockResponse = new ApiResponse<>(200, "Employee Save Successfuly", null);

		when(employeeService.saveEmployee(any(Employee.class))).thenReturn(mockResponse);

		mockMvc.perform(post("/domain/saveEmployee").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(mockEmployee))).andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.message").value("Employee Save Successfuly"));
	}

	@Test
	void testSaveEmployeeFailure() throws Exception {
		Employee mockEmployee = new Employee(1, "John", true, null, null, null);
		ApiResponse<String> mockResponse = new ApiResponse<>(500, "Something went wrong try again.", null);

		when(employeeService.saveEmployee(any(Employee.class))).thenReturn(mockResponse);

		mockMvc.perform(post("/domain/saveEmployee").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(mockEmployee))).andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(500))
				.andExpect(jsonPath("$.message").value("Something went wrong try again."));
	}

	@Test
	void testGetTotalEmployeeCount() throws Exception {
		ApiResponse<Long> mockResponse = new ApiResponse<>(200, "Success", 10L);

		when(employeeService.getEmployeeCount()).thenReturn(mockResponse);

		mockMvc.perform(get("/domain/getCount")).andExpect(status().isOk()).andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").value(10));
	}

	@Test
	void testGetActiveEmployeeList() throws Exception {
		List<Employee> mockEmployees = Arrays.asList(new Employee(1, "John", true, null, null, null),
				new Employee(2, "Jane", false, null, null, null));
		ApiResponse<List<Employee>> mockResponse = new ApiResponse<>(200, "Suc" + "cess", mockEmployees);

		when(employeeService.getActiveEmployeeList(true)).thenReturn(mockResponse);

		mockMvc.perform(get("/domain/getByActive").param("isActive", "true")).andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200)).andExpect(jsonPath("$.data[0].employeeName").value("John"));
	}

	@Test
	void testSearchByExample() throws Exception {
		List<Employee> mockEmployees = Arrays.asList(new Employee(1, "John", true, null, null, null));
		ApiResponse<List<Employee>> mockResponse = new ApiResponse<>(200, "Success", mockEmployees);

		when(employeeService.searchByExample(any(Employee.class))).thenReturn(mockResponse);

		mockMvc.perform(get("/domain/searchEmployee").param("employeeName", "John").param("isActive", "true"))
				.andExpect(status().isOk()).andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data[0].employeeName").value("John"));
	}

	@Test
	void testEmployeeDtoList() throws Exception {
		List<EmployeeProjectDTO> mockDtoList = Arrays.asList(new EmployeeProjectDTO("John", "HR", "Project1", null));
		ApiResponse<List<EmployeeProjectDTO>> mockResponse = new ApiResponse<>(200, "Success", mockDtoList);

		when(employeeService.employeeDtoList()).thenReturn(mockResponse);

		mockMvc.perform(get("/domain/employeeDtoList")).andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200)).andExpect(jsonPath("$.data[0].employeeName").value("John"));
	}

//	@Test
//	@Disabled
//	void testGetEmployeesPagable() throws Exception {
//		// Prepare mock data for paginated response
//		Employee employee1 = new Employee(1, "John", true, null, null, null);
//		Employee employee2 = new Employee(2, "Jane", false, null, null, null);
//		Page<Employee> mockPage = new PageImpl<>(List.of(employee1, employee2));
//
//		// Mock the service layer to return the mock page
//		when(employeeService.getEmployees(any(Pageable.class))).thenReturn(mockPage);
//
//		// Perform GET request to the endpoint
//		mockMvc.perform(get("/domain/employeeListPagable").param("page", "0") // Specify page number
//				.param("size", "2") // Specify page size
//				.param("sort", "employeeName,asc")) // Specify sort criteria
//				.andExpect(status().isOk()) // Verify HTTP 200 response
//				.andExpect(jsonPath("$.content[0].employeeId").value(1)) // Verify first employee's ID
//				.andExpect(jsonPath("$.content[0].employeeName").value("John")) // Verify first employee's name
//				.andExpect(jsonPath("$.content[1].employeeId").value(2)) // Verify second employee's ID
//				.andExpect(jsonPath("$.content[1].employeeName").value("Jane")) // Verify second employee's name
//				.andExpect(jsonPath("$.totalElements").value(2)) // Verify total number of elements
//				.andExpect(jsonPath("$.totalPages").value(1)); // Verify total number of pages
//	}

}
