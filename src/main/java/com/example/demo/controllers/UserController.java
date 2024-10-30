//package com.example.demo.controllers;
//
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.example.demo.models.Users;
//import com.example.demo.services.UserService;
//
//@RestController
//
//@RequestMapping("/users")
//public class UserController {
//	@Autowired
//	UserService us;
//	
//@PostMapping("/insertUser")
//	public ResponseEntity<Map<String,Object>> insertUser(@RequestBody Users u){
//	Map<String,Object> result = us.addUser(u);
//	  if ("success".equals(result.get("status"))) {
//          return new ResponseEntity<>(result, HttpStatus.CREATED);
//      } else if ("failure".equals(result.get("status"))) {
//          return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
//      } else {
//          return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
//      }
//}
//}


package com.example.demo.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Users;
import com.example.demo.services.UserService;
import com.example.demo.utilities.JwtUtil;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

@RestController

@RequestMapping("/users")
public class UserController {
	@Autowired
	UserService us;
	
	@Autowired 
	JwtUtil jwtutil;
@PostMapping("/insertUser")
	public ResponseEntity<Map<String,Object>> insertUser(HttpServletRequest request, @RequestBody Users u){

	try {
		Claims claims = jwtutil.getClaimsFromRequest(request);
		int userId = (int) claims.get("userId");
		String role = (String) claims.get("role");
		if(!("superAdmin".equals(role))) {
			return new ResponseEntity<>(Map.of("message", "Access denied"), HttpStatus.FORBIDDEN);
		}
	Map<String,Object> result = us.addUser(u);
	  if ("success".equals(result.get("status"))) {
          return new ResponseEntity<>(result, HttpStatus.CREATED);
      } else if ("failure".equals(result.get("status"))) {
          return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
      } else {
          return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
      }
}
	catch(Exception e) {
		// Handle any token parsing/validation failure
        return new ResponseEntity<>(Map.of("message", "Invalid or expired token"), HttpStatus.UNAUTHORIZED);
	}
}
}
