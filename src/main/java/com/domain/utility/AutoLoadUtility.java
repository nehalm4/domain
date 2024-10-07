package com.domain.utility;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.domain.pojo.Employee;
import com.domain.pojo.Role;
import com.domain.pojo.User;
import com.domain.repository.EmployeeRepository;
import com.domain.repository.UserRepository;

/**
 * @author Nehal Mahajan
 * @apiNote Auto Load Utility class to load the data on application start
 */
@Component
public class AutoLoadUtility {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private UserRepository userRepository;

	private final PasswordEncoder encoder = new BCryptPasswordEncoder();

	public void autoLoad() {
		Employee employee = new Employee();

		employee.setEmployeeId(1);
		employee.setEmployeeName("Nehal");
		employee.setIsActive(true);
		employeeRepository.save(employee);

		employee.setEmployeeId(2);
		employee.setEmployeeName("Swati");
		employee.setIsActive(false);
		employeeRepository.save(employee);

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
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		threadPoolTaskExecutor.setThreadNamePrefix("Async-");
		threadPoolTaskExecutor.setCorePoolSize(8);
		threadPoolTaskExecutor.setMaxPoolSize(32);
		threadPoolTaskExecutor.setQueueCapacity(500);
		threadPoolTaskExecutor.afterPropertiesSet();
		return threadPoolTaskExecutor;
	}

}
