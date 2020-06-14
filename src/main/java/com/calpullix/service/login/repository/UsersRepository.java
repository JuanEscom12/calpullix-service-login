package com.calpullix.service.login.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.calpullix.service.login.model.Employee;
import com.calpullix.service.login.model.Users;


@Repository
public interface UsersRepository extends JpaRepository<Users, Integer>  {

	Optional<Users> findByIdemployee(Employee idEmployee);
	
	Optional<Users> findByIdemployeeAndPassword(Employee idEmployee, String password);
	
	
}
