package com.example.demo.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.OtherDonationsTransaction;
import com.example.demo.services.OtherDonationService;
@CrossOrigin("*")
@RestController

@RequestMapping("/donation")
public class OtherDonationController {
@Autowired 
OtherDonationService os;

@PostMapping("/otherdonations")
public ResponseEntity<Map<String,Object>> insertOtherDonationData(@RequestBody OtherDonationsTransaction otherDonationsTransaction){
	Map<String,Object> result = os.insertOtherDonation(otherDonationsTransaction);
	if(result.get("status").equals("success")) {
	    return new ResponseEntity<>(result, HttpStatus.CREATED);
    } else if ("failure".equals(result.get("status"))) {
        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    } else {
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }
}
}




