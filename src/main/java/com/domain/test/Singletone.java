package com.domain.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.domain.pojo.Employee;

@Component
@Scope("singleton")
public class Singletone {

	@Autowired
	private Prototype prototype; // Wrong approach (explained later)

	public Prototype getPrototypeInstance() {
		return prototype;
	}

	public Employee getPrototypeBeanInt() {
		return prototype.getInt();
	}

	public Employee getSingletoneInt() {
		Employee obj = new Employee();
		obj.setEmployeeId(2);
		obj.setIsActive(true);
		obj.setEmployeeName("Nehal");
		return obj;
	}
}
