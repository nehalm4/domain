package com.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.domain.pojo.Employee;

/**
 * @author Nehal Mahajan
 * @apiNote Employee Repository Interface
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	public List<Employee> getEmployeeByIsActive(boolean isActive);
}
