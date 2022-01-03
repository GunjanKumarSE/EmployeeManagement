package com.neosoft.dev.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neosoft.dev.exception.EmployeeNotFoundException;
import com.neosoft.dev.model.Employee;
import com.neosoft.dev.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public void saveEmployee(Employee employee) {
		this.employeeRepository.save(employee);
	}

	@Override
	public List<Employee> getAllEmployees() {
		return this.employeeRepository.findAll();

	}

	@Override
	public Employee getEmployeeById(long id) {
		Optional<Employee> optional = employeeRepository.findById(id);

		Employee employee = null;
		if (optional.isPresent()) {
			employee = optional.get();
		} else {
			throw new EmployeeNotFoundException("employee is not found in DB for id - " + String.valueOf(id));
		}
		return employee;
	}

	@Override
	public void deleteEmployeeById(long id) {
		this.employeeRepository.deleteById(id);

	}

}
