package com.calpullix.service.login.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calpullix.service.login.conf.JwtTokenProvider;
import com.calpullix.service.login.model.Employee;
import com.calpullix.service.login.model.LoginRequest;
import com.calpullix.service.login.model.LoginResponse;
import com.calpullix.service.login.model.Users;
import com.calpullix.service.login.repository.UsersRepository;
import com.calpullix.service.login.service.LoginService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
	
	private static final String ROLE_ADMIN = "ROLE_ADMIN";
	
	@Autowired
	private UsersRepository usersRepository; 
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	
	@Override
	public LoginResponse login(LoginRequest request) {
		log.info(":: Login service {} ", request);
		final LoginResponse result = new LoginResponse();
		final Employee idEmployee = new Employee();
		idEmployee.setId(request.getUser());
		final Optional<Users> user = usersRepository.findByIdemployeeAndPassword(idEmployee, request.getPassword());
		if (user.isPresent()) {
			result.setIsValid(Boolean.TRUE);
			final List<String> roleAdmnin = new ArrayList<>();
			roleAdmnin.add(ROLE_ADMIN);
			final String token = jwtTokenProvider.createToken(user.get().getId().toString(), roleAdmnin);
			result.setToken(token);
		} else {
			result.setIsValid(Boolean.FALSE);
		}
		log.info(":: End Up Login Service {} ", result);
		return result;
	}
	
	
	public String createNewToken(String token) {
        String username = jwtTokenProvider.getUsername(token);
        List<String>roleList = jwtTokenProvider.getRoleList(token);
        String newToken =  jwtTokenProvider.createToken(username, roleList);
        return newToken;
    }
	

}
