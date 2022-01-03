package com.neosoft.dev;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.neosoft.dev.model.Employee;
import com.neosoft.dev.repository.EmployeeRepository;

@SpringBootTest
class EmployeeManagementCrudApplicationTests {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Test
	@Order(1)
	public void testSaveEmployee() {
		Employee emp = new Employee();
		emp.setId(1l);
		emp.setName("ramesh");
		emp.setEmail("ramesh.kumar@gmail.com");
		emp.setGender("male");
		emp.setMarried("Unmarried");
		Date d = new Date();
		emp.setBirthday(d);
		emp.setDateofjoining(d);
		emp.setDeleted(Boolean.FALSE);
		emp.setProfession("Developer");
		emp.setSalary(30000L);

		this.employeeRepository.save(emp);
		assertNotNull(this.employeeRepository.findById(1l).get());

	}

	@Test

	@Order(2)
	public void testGetAllEmployees() {
		List<Employee> list = this.employeeRepository.findAll();
		assertThat(list.size()).isGreaterThan(0);
	}

	@Test

	@Order(3)
	public void testGetEmployeeById() {
		Employee emp = this.employeeRepository.findById(1l).get();
		assertEquals("ramesh", emp.getName());
	}

	@Test

	@Order(4)
	public void testUpdateEmployee() {
		Employee emp = this.employeeRepository.findById(1l).get();
		emp.setProfession("Tester");
		this.employeeRepository.save(emp);
		assertNotEquals("Developer", this.employeeRepository.findById(1l).get().getProfession());
	}

	@Test

	@Order(5)
	public void testDeleteEmployee() {
		this.employeeRepository.deleteById(6l);
		assertThat(this.employeeRepository.existsById(5l));
	}

}
