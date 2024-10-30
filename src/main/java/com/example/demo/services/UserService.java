package com.example.demo.services;

import java.util.HashMap;
import java.util.Map;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.models.Users;

@Service
public class UserService {
@Autowired
JdbcTemplate jdbct;

//add users
public Map<String,Object> addUser(Users u){
	Map<String,Object> response = new HashMap<>();
	String templeId = u.getTempleId();
	String templeName = u.getTempleName();
	String userEmail = u.getUserEmail();
	String password = u.getPassword();
	String role = u.getRole();
	try {
		String hashedPassword = hashPassword(password);
	String query = "insert into Users(templeId,templeName,userEmail,password,role) values(?,?,?,?,?)";
	int result = jdbct.update(query,templeId,templeName,userEmail,hashedPassword,role);
	if(result > 0) {
		 response.put("message", "User added successfully");
		response.put("status", "success");	
	}
	else {
		response.put("message", "Insertion failed");
		response.put("status", "failure");		
	}
	}
	catch(Exception e) {
		 response.put("message", "Error occurred: " + e.getMessage());
         response.put("status", "error");
	}
	return response;
}
	

//function to encrypt password
private String hashPassword(String plainTextPassword){
	return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
}



}
