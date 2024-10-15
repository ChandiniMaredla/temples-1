package com.example.demo.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Annadanam;
import com.example.demo.models.OtherDonations;
import com.example.demo.services.DonationService;

@CrossOrigin("*")
@RestController
@RequestMapping("/donation")
public class DonationController {
@Autowired 
DonationService ds;



@GetMapping("/data/{type}")
public ResponseEntity<Map<String,Object>> getDonationData(@PathVariable String type){
	Map<String,Object> result = ds.getDonations(type);
	 if ("success".equals(result.get("status"))) {
         return new ResponseEntity<>(result, HttpStatus.OK);
     } else if ("failure".equals(result.get("status"))) {
         return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
     } else {
         return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
     }
}



}
