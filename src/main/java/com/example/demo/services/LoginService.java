package com.example.demo.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;


import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import io.jsonwebtoken.security.Keys;
import java.security.Key;
import com.example.demo.models.Users;

@Service
public class LoginService {

	
	 // Accessing the jwt.secret property from application.properties
    @Value("${jwt.secret}")
    private String SECRET_KEY;
 

	//private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Generates a secure key for HS256

    
	@Autowired
	JdbcTemplate jdbc;
	public Map<String,Object> login(Users u){
		String templeName = u.getTempleName();
		String email = u.getUserEmail();
		String password = u.getPassword();
		String query1 = "select * from Users where userEmail = ?";
		Map<String,Object> response = new HashMap<>();
		try {
			List<Map<String,Object>> result = jdbc.queryForList(query1,email);
			if(result.size() <= 0) {
				response.put("message", "User with this email doesn't exists");
				response.put("status", "failure");	
				return response;
			}
			String hashedPassword = (String) result.get(0).get("password");
			String temple = (String)result.get(0).get("templeName");
			int userId = (int)result.get(0).get("userId");
			String role = (String)result.get(0).get("role");
			System.out.println(result);
			boolean passwordCheck = checkPass(password,hashedPassword);
			if(passwordCheck == true && temple.equals(templeName)){
				String jwt = generateToken(userId,role);
				response.put("message", "Login successful");
				response.put("token",jwt );
				response.put("status", "success");
			}
			else {
				response.put("message", "Login failed");
				response.put("status", "failure");
			}
			 
		}
		catch(Exception e) {
			response.put("message", "Error occurred: " + e.getMessage());
	         response.put("status", "error");
		}
		return response;
	}
	
	// function to verify the password
	private boolean checkPass(String plainPassword, String hashedPassword) {
		if (BCrypt.checkpw(plainPassword, hashedPassword))
			{
			System.out.println("The password matches.");
			return true;
			
			}
		else {
			System.out.println("The password does not match.");
			return false;
		}
	}




// Method to create the JWT token
	 private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 hrs

	   
		public String generateToken(int userId, String role) {
	        // Create claims
	        Map<String, Object> claims = new HashMap<>();
	        claims.put("userId", userId);
	        claims.put("role", role);

	        // Generate the token
	        return Jwts.builder()
	                .setClaims(claims)
	                .setSubject(String.valueOf(userId)) // Set the subject to userId or username
	                .setIssuedAt(new Date(System.currentTimeMillis())) // Set issue date
	                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Set expiration date
	                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // New usage with Key and algorithm specified
	                .compact();
	    }
		

		
}
