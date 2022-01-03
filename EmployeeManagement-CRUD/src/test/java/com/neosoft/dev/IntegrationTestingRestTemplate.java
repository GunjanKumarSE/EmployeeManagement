package com.neosoft.dev;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neosoft.dev.controller.EmployeAPIForTestingController;
import com.neosoft.dev.model.Employee;
import com.neosoft.dev.repository.EmployeeRepository;
import com.neosoft.dev.service.EmployeeService;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeAPIForTestingController.class)
public class IntegrationTestingRestTemplate {

	@Mock
	private RestTemplate restTemplate;

	@MockBean
	public EmployeeService service;

	@MockBean
	public EmployeeRepository employeeRepository;

	@Autowired
	ObjectMapper mapper;

	@Test
	public void getAllEmployeeTest() throws Exception {
		RestTemplate restTemplate = new RestTemplate();

		final String baseUrl = "http://localhost:8080/getAllEmployees";
		URI uri = new URI(baseUrl);

		ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
		Assert.assertEquals(200, result.getStatusCodeValue());
		String res = result.getBody();
		// System.out.println(res);
		List<Employee> list = mapper.readValue(res, new TypeReference<List<Employee>>() {
		});
		// System.out.println(list.size());
		Assert.assertEquals(2, list.size());

	}

	@Test
	public void getEmployeeByIdTest() throws URISyntaxException {
		try {
			RestTemplate restTemplate = new RestTemplate();

			final String baseUrl = "http://localhost:8080/getEmployeeById/1";
			URI uri = new URI(baseUrl);

			ResponseEntity<Employee> result = restTemplate.getForEntity(uri, Employee.class);

			// Verify request succeed
			Assert.assertEquals(200, result.getStatusCodeValue());
			Employee emp = result.getBody();
			// System.out.println(emp.getEmail());
			Assert.assertEquals("suresh123.kumar@gmail.com", emp.getEmail());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void saveEmployeeTest() throws URISyntaxException {
		try {
			RestTemplate restTemplate = new RestTemplate();

			final String baseUrl = "http://localhost:8080/createEmployee";
			URI uri = new URI(baseUrl);

			Employee emp1 = new Employee(2, "anjali", "anjali123@gmail.com", "female", "Unmarried", new Date(),
					"Tester", new Date(), 35000, false);

			HttpHeaders headers = new HttpHeaders();

			HttpEntity<Employee> request = new HttpEntity<>(emp1, headers);

			ResponseEntity<String> e = restTemplate.postForEntity(uri, request, String.class);
			// System.out.println(e.getStatusCodeValue());

			// Verify request succeed
			Assert.assertEquals(201, e.getStatusCodeValue());

		} catch (HttpClientErrorException ex) {
			// Verify bad request and missing header
			Assert.assertEquals(400, ex.getRawStatusCode());
			Assert.assertEquals(true, ex.getResponseBodyAsString().contains("Missing request header"));
		}
	}

	@Test
	public void deleteEmployeeTest() throws URISyntaxException {
		try {
			RestTemplate restTemplate = new RestTemplate();

			final String baseUrl = "http://localhost:8080/hardDelete/";
			// URI uri = new URI(baseUrl);

			Map<String, String> params = new HashMap<String, String>();
			params.put("id", "1");
			restTemplate.delete(baseUrl, params);

		} catch (HttpClientErrorException ex) {
			ex.getMessage();
		}
	}

	@Test
	public void updateEmployeeTest() throws URISyntaxException {
		try {
			RestTemplate restTemplate = new RestTemplate();

			final String baseUrl = "http://localhost:8080/updateEmployee/";
			// URI uri = new URI(baseUrl);

			Map<String, String> params = new HashMap<String, String>();
			params.put("id", "2");

			Employee e = new Employee(2, "anjali", "anjali123@gmail.com", "female", "Unmarried", new Date(), "Tester",
					new Date(), 35000, false);

			restTemplate.put(baseUrl, e, params);

		} catch (HttpClientErrorException ex) {
			ex.getMessage();
		}
	}
}
