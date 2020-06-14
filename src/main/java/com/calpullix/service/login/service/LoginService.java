package com.calpullix.service.login.service;

import com.calpullix.service.login.model.LoginRequest;
import com.calpullix.service.login.model.LoginResponse;

public interface LoginService {

	LoginResponse login(LoginRequest request);
	
	String createNewToken(String token);
	
}
