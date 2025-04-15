package com.domain.utility;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.domain.pojo.Address;
import com.domain.pojo.Department;
import com.domain.pojo.Employee;
import com.domain.pojo.Project;
import com.domain.pojo.Role;
import com.domain.pojo.User;
import com.domain.repository.EmployeeRepository;
import com.domain.repository.UserRepository;

/**
 * @author Nehal Mahajan
 * @apiNote Auto Load Utility class to load the data on application start
 */
@Component
@EnableAsync
public class AutoLoadUtility {

	private EmployeeRepository employeeRepository;

	private UserRepository userRepository;

	@Autowired
	public AutoLoadUtility(EmployeeRepository employeeRepository, UserRepository userRepository) {
		this.employeeRepository = employeeRepository;
		this.userRepository = userRepository;
	}

	private final PasswordEncoder encoder = new BCryptPasswordEncoder();

	public void autoLoad() {

		Department department1 = new Department();
		department1.setDepartmentName("HR");
		Department department2 = new Department();
		department2.setDepartmentName("Finance");

		Project project1 = new Project();
		project1.setProjectUId("P001NX");
		project1.setProjectName("RESOURCE FULLFILMENT");

		Project project2 = new Project();
		project2.setProjectUId("NGINX2187");
		project2.setProjectName("BMW");

		Project project3 = new Project();
		project3.setProjectUId("NGI875B07");
		project3.setProjectName("MASTERCARD");

		Employee employee = new Employee();
		Employee employee1 = new Employee();

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

		employee1.setEmployeeName("Swati");
		employee1.setIsActive(false);
		Address address1 = Address.builder().buildingNumber(301).area("Nandavan").city("Nagpur").pincode(430032L)
				.country("India").state("Maharashtra").build();
		employee1.setAddress(address1);
		employee1.setDepartment(department2);
		employee1.setProject(Arrays.asList(project2, project3));
		employeeRepository.save(employee1);

		User user1 = new User();
		user1.setId(1L);
		user1.setUsername("nehal");
		user1.setPassword(encoder.encode("pass"));
		user1.setEmail("nehalm4@gmail.com");
		Set<Role> roles1 = new HashSet<>();
		roles1.add(Role.ROLE_ADMIN);
		user1.setAuthorities(roles1);
		userRepository.save(user1);

		User user2 = new User();
		user2.setId(2L);
		user2.setUsername("swati");
		user2.setPassword(encoder.encode("pass"));
		user2.setEmail("swati@gmail.com");
		Set<Role> roles2 = new HashSet<>();
		roles2.add(Role.ROLE_USER);
		user2.setAuthorities(roles2);
		userRepository.save(user2);
	}

//	@Async("processExecutor") USE THIS ON METHOD FOR ASYNC EXECUTION

	@Bean(name = "processExecutor")
	public TaskExecutor workExecutor() {
		int cores = Runtime.getRuntime().availableProcessors();
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		threadPoolTaskExecutor.setThreadNamePrefix("Async-");
		threadPoolTaskExecutor.setCorePoolSize(20);
		threadPoolTaskExecutor.setMaxPoolSize(40);
		threadPoolTaskExecutor.setQueueCapacity(500);
		threadPoolTaskExecutor.afterPropertiesSet();
		 System.out.println("ThreadPool initialized with core pool size: " + threadPoolTaskExecutor.getCorePoolSize());
		return threadPoolTaskExecutor;
	}

}
