package com.neosoft.dev.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.neosoft.dev.exception.EmployeeCustomExceptions;
import com.neosoft.dev.model.Employee;
import com.neosoft.dev.repository.EmployeeRepository;
import com.neosoft.dev.service.EmployeeService;

@RestController
public class EmployeAPIForTestingController {

	private EmployeeService employeeService;

	public EmployeAPIForTestingController(EmployeeService employeeService) {
		super();
		this.employeeService = employeeService;
	}

	@Autowired
	private EmployeeRepository employeeRepository;

	@GetMapping(value = "/getAllEmployees", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Employee>> getAllEmployees(Model model) {
		List<Employee> list = employeeService.getAllEmployees();
		try {
			if (list.isEmpty()) {
				throw new EmployeeCustomExceptions("Employee list is not found");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<Employee>>(list, HttpStatus.OK);

	}

	@GetMapping("/getEmployeeById/{id}")
	public ResponseEntity<Employee> findEmployeeById(@PathVariable(name = "id") long id) {
		Employee employee = this.employeeService.getEmployeeById(id);
		if (employee != null) {
			return new ResponseEntity<>(employee, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/createEmployee")
	public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee, BindingResult bindingResult) {
		try {
			if (employee.getEmail() != null) {
				employeeService.saveEmployee(employee);
			}
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/updateEmployee/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable("id") long id, @Valid @RequestBody Employee employee) {
		Employee emp = employeeService.getEmployeeById(id);

		if (emp != null) {
			emp.setName(employee.getName());
			emp.setEmail(employee.getEmail());
			emp.setGender(employee.getGender());
			emp.setMarried(employee.getMarried());
			emp.setBirthday(employee.getBirthday());
			emp.setProfession(employee.getProfession());
			emp.setDateofjoining(employee.getDateofjoining());
			emp.setSalary(employee.getSalary());
			return new ResponseEntity<>(employeeRepository.save(emp), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/hardDelete/{id}")
	public ResponseEntity<HttpStatus> hardDeleteEmployee(@PathVariable(value = "id") long id) {
		try {

			employeeService.deleteEmployeeById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@DeleteMapping("/softDelete/{id}")
	public ResponseEntity<HttpStatus> softDeleteEmployee(@PathVariable(value = "id") long id) {
		try {
			// System.out.println("id===========" + id);
			this.employeeRepository.softdeleteEmployee(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/sortEmployeeBasedOnDOJ")
	public ResponseEntity<List<Employee>> sortedEmployee() {
		List<Employee> empList = this.employeeRepository.findAll();
		try {
			if (empList.isEmpty()) {
				throw new EmployeeCustomExceptions("list is empty");
			}
			System.out.println(empList.size());
			Collections.sort(empList, new Comparator<Employee>() {
				public int compare(Employee o1, Employee o2) {
					return o1.getDateofjoining().compareTo(o2.getDateofjoining());
				}
			});
		} catch (EmployeeCustomExceptions e) {
			e.getMessage();
		}
		return new ResponseEntity<List<Employee>>(empList, HttpStatus.OK);
	}

	@GetMapping("/findByName/{name}")
	public List<Employee> findEmployeeByName(@PathVariable("name") String name) {
		return name != null ? this.employeeRepository.findByNameIs(name) : null;

	}

}
