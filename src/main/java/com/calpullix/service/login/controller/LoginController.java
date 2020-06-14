package com.calpullix.service.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.calpullix.service.login.model.LoginRequest;
import com.calpullix.service.login.model.LoginResponse;
import com.calpullix.service.login.service.LoginService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/calpullix")
public class LoginController {

	private static final String PATH_LOGIN = "${app.path-login}";

	private static final String PATH_LOGIN_TOKEN = "${app.path-login-token}";
	
	@Autowired
	private LoginService loginService;
	
	
	@PostMapping(value = PATH_LOGIN, produces = "application/json")
	public ResponseEntity<LoginResponse> getPurchaseOrder(@RequestBody LoginRequest request) {
		log.info(":: Retrieve Purchase Order Controller {} ", request);
		final LoginResponse result = loginService.login(request);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping(value = PATH_LOGIN_TOKEN, produces = "application/json")
	@CrossOrigin("*")
	@ResponseBody
	public ResponseEntity<LoginResponse> createNewToken(
			@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token) {
		log.info(":: Retrieve new token Controller {} ", token);
		final String newToken = loginService.createNewToken(token);
		log.info(":: New token {} ", newToken);
		final HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.AUTHORIZATION, newToken);
		return new ResponseEntity<LoginResponse>(headers, HttpStatus.CREATED);
	}

	@GetMapping(value = "/hello")
	public ResponseEntity<LoginResponse> getPurchaseOrder() {
		log.info("::::: HELLO CONTROLLER ");
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
