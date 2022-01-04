package com.neosoft.dev.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.neosoft.dev.model.Employee;

@Transactional
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	@Modifying
	@Query("update Employee e set e.deleted = true  where e.id = :id")
	void softdeleteEmployee(@Param("id") long id);

	public Employee findByEmail(String email);

	public Employee findByFirstName(String name);

	List<Employee> findByFirstNameIs(String name);

	// , ' ', e.madein

	@Query("SELECT e FROM Employee e WHERE CONCAT(e.firstName, ' ', e.lastName, ' ', e.pinCode) LIKE %?1%")
	List<Employee> search(String keyword);

}
