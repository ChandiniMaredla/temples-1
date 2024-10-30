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
import com.example.demo.services.LoginService;

@RestController

@RequestMapping("/")
public class LoginController {
@Autowired
LoginService ls;

@PostMapping("/login")
public ResponseEntity<Map<String,Object>> userLogin(@RequestBody Users u){
	Map<String,Object> result = ls.login(u);
	 if ("success".equals(result.get("status"))) {
         return new ResponseEntity<>(result, HttpStatus.OK);
     } else if ("failure".equals(result.get("status"))) {
         return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
     } else {
         return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
     }
}
}
