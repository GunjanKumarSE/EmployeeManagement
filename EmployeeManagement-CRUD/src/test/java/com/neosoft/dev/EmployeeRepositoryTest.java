package com.neosoft.dev;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.neosoft.dev.model.Employee;
import com.neosoft.dev.repository.EmployeeRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeRepositoryTest {

	@Autowired
	EmployeeRepository employeeRepository;

	@Test
	public void saveEmployeeTest() {
		Employee emp = Employee.builder().id(4l).name("rani").email("rani123@gmail.com").gender("female")
				.birthday(new Date()).profession("Tester").dateofjoining(new Date()).deleted(false).salary(20000)
				.married("unmarried").build();

		employeeRepository.save(emp);

		assertThat(emp.getId()).isGreaterThan(0);

	}

	@Test
	public void findByIdEmploeeTest() {

		Employee emp = employeeRepository.findById(1l).get();

		assertThat(emp.getId()).isEqualTo(1l);

	}

	@Test
	public void findAllEmploeeTest() {

		List<Employee> list = employeeRepository.findAll();

		assertThat(list.size()).isGreaterThan(0);

	}

	@Test
	public void updateEmploeeTest() {

		Employee emp = employeeRepository.findById(1l).get();
		emp.setEmail("rani12345@gmail.com");
		employeeRepository.save(emp);

		assertThat(emp.getEmail()).isEqualTo("rani12345@gmail.com");

	}

	@Test
	public void deleteEmploeeTest() {

		Employee emp = employeeRepository.findById(1l).get();
		employeeRepository.delete(emp);

		Employee e = employeeRepository.findByEmail("gunjan.kumar@neosoftmail.com");

		assertThat(e).isNull();
		;

	}

}
