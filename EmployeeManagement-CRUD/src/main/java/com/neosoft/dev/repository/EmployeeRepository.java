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

	public Employee findByName(String name);

	List<Employee> findByNameIs(String name);

}
