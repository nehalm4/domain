package com.domain.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.domain.pojo.Employee;

@Component
@Scope("prototype")
public class Prototype {

	public Employee getInt() {
		Employee obj = new Employee();
		obj.setEmployeeId(1);
		obj.setIsActive(false);
		obj.setEmployeeName("Manish");
		return obj;
	}
}
