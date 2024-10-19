package com.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.domain.dto.EmployeeProjectDTO;
import com.domain.pojo.Employee;

/**
 * @author Nehal Mahajan
 * @apiNote Employee Repository Interface
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	
	public List<Employee> getEmployeeByIsActive(boolean isActive);
	
	@Query("SELECT new com.domain.dto.EmployeeProjectDTO(e.employeeName, d.departmentName, p.projectUId, p.projectName) " +
	           "FROM Employee e " +
	           "JOIN e.department d " +
	           "JOIN e.project p")
	    List<EmployeeProjectDTO> findEmployeeProjectDetails();
}
