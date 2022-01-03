package com.neosoft.dev.service;

import java.util.List;

import com.neosoft.dev.model.Employee;

public interface EmployeeService {
	void saveEmployee(Employee employee);

	List<Employee> getAllEmployees();

	Employee getEmployeeById(long id);

	void deleteEmployeeById(long id);
}
