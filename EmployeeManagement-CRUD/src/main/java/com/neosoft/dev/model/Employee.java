package com.neosoft.dev.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "emp_id")
	private long id;

	@Column(name = "emp_name")
	@Size(min = 3, max = 50, message = "name size in between 3 and 50")
	private String name;

	@Column(name = "emp_email")
	@NotBlank
	@Email(message = "Please enter a valid e-mail address")
	private String email;

	@NotBlank(message = "please select gender")
	private String gender;

	@NotBlank(message = "please select marital status")
	private String married;

	@DateTimeFormat(pattern = "yyyy-mm-dd")
	@Column(name = "emp_dob")
	private Date birthday;

	@NotBlank(message = "please select profession")
	private String profession;

	@DateTimeFormat(pattern = "yyyy-mm-dd")
	@Column(name = "date_of_joining")
	private Date dateofjoining;

	@Min(value = 10_000)
	@Max(value = 200_000)
	private long salary;

	private boolean deleted = Boolean.FALSE;

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", email=" + email + ", gender=" + gender + ", married="
				+ married + ", birthday=" + birthday + ", profession=" + profession + ", dateofjoining=" + dateofjoining
				+ ", salary=" + salary + ", deleted=" + deleted + "]";
	}

}
