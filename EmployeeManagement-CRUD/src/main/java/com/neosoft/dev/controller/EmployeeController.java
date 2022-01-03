package com.neosoft.dev.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.neosoft.dev.model.Employee;
import com.neosoft.dev.repository.EmployeeRepository;
import com.neosoft.dev.service.EmployeeService;

@Controller
public class EmployeeController {

	private EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		super();
		this.employeeService = employeeService;
	}

	@Autowired
	private EmployeeRepository employeeRepository;

	@GetMapping("/employees")
	public ResponseEntity<List<Employee>> getAllEmployees(Model model) {
		try {
			List<Employee> list = employeeService.getAllEmployees();
			if (list != null) {
				return new ResponseEntity<>(list, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@GetMapping("/")
	public String viewHomePage() {
		return "index";
	}

	@GetMapping("/registerForm")
	public String registerForm(Model model) {
		Employee emp = new Employee();
		model.addAttribute("employee", emp);
		List<String> listProfession = Arrays.asList("Developer", "Tester", "Architect", "HR");
		model.addAttribute("listProfession", listProfession);
		return "new_employee";
	}

	@PostMapping("/registerEmployee")
	public String registerEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult bindingResult,
			Model model) {
		System.out.println(employee.getEmail());
		if (bindingResult.hasErrors()) {
			List<String> listProfession = Arrays.asList("Developer", "Tester", "Architect", "HR");
			model.addAttribute("listProfession", listProfession);
			return "new_employee";
		} else {
			employeeService.saveEmployee(employee);
		}
		model.addAttribute("Success", "SuccessFully Register");
		return "redirect:/";
	}

	@GetMapping(value = "/showFormForUpdate/{id}")
	public String showUpdateForm(@PathVariable(value = "id") long id, Model model) {

		// get employee from service layer
		Employee employee = employeeService.getEmployeeById(id);

		// set employee as a model attribute to pre-populate the form
		model.addAttribute("employee", employee);

		return "update_employee";
	}

	@GetMapping(value = "/harddelete/employee/")
	public String employeeHardDelete(@RequestParam(name = "id") long id) {
		System.out.println("id:::::" + id);
		employeeService.deleteEmployeeById(id);
		return "redirect:/";
	}

	@GetMapping(value = "/softdelete/employee/")
	public String employeeSoftDelete(@RequestParam(name = "id") long id) {
		System.out.println("id:::::" + id);
		this.employeeRepository.softdeleteEmployee(id);
		return "redirect:/";
	}

	@GetMapping("/employee/{id}")
	public ResponseEntity<Employee> findEmployeeById(@PathVariable(name = "id") long id) {
		Optional<Employee> employee = this.employeeRepository.findById(id);
		if (employee.isPresent()) {
			return new ResponseEntity<>(employee.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
