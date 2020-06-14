package com.calpullix.service.login.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginResponse {
	
	private Boolean isValid;

	private String token;
	
	private String userName;
		
	private String password;
	
}
