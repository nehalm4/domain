package com.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.domain.pojo.Employee;
import com.domain.repository.EmployeeRepository;

@Service
public class KafkaConsumerService {

	private EmployeeRepository employeeRepository;

	@Autowired
	KafkaConsumerService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@KafkaListener(topics = "testing-topic", groupId = "testing-topic")
	public void consume(Employee employee) {
		employeeRepository.save(employee);
	}

}
