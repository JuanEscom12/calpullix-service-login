package com.calpullix.service.login.conf;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenProvider {
	
	private static final String ROLE_ADMIN = "ROLE_ADMIN";

    private static final String AUTH = "auth";
        
    @Value("${jwt.secret-key}")
    private String secretKey;
    
    private long validityInMilliseconds = 3600000;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String username, List<String> roles) {
        final Claims claims = Jwts.claims().setSubject(username);
        claims.put(AUTH,roles);
        
        final Date validity = 
        		new Date(Calendar.getInstance().getTime().getTime() + validityInMilliseconds);

        final String result =  Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return result;
    }
    
    @SuppressWarnings("unchecked")
	public List<String> getRoleList(String token) {
    	try {
    		 return (List<String>) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).
    	                getBody().get(AUTH);
    	} catch (ExpiredJwtException ex) {
    		final List<String> roleAdmnin = new ArrayList<>();
			roleAdmnin.add(ROLE_ADMIN);
			return roleAdmnin;
    	}
    }
    
    public String getUsername(String token) {
    	try {
    		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    	} catch (ExpiredJwtException ex) {
    		log.info(":: SUBJECT {} ", ex.getClaims().getSubject());
    		return ex.getClaims().getSubject();
    	}
    }

}
