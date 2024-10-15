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
//import com.example.demo.models.Annadanam;
//import com.example.demo.services.DonationService;
//
//@RestController
//
//@RequestMapping("/donation")
//public class DonationController {
//@Autowired
//DonationService ds;
//
//@PostMapping("/annadanam")
//public ResponseEntity<Map<String,Object>> insertAnnadanamData(@RequestBody Annadanam annadanam) {
//	Map<String,Object> result =  ds.insertAnnadanam(annadanam);
//	if(result.get("status").equals("success")) {
//		return new ResponseEntity<>(result, HttpStatus.CREATED);
//	}
//	else if(result.get("status").equals("failure")) {
//		return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
//		
//	}
//	else {
//		return new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
//	}
//}
//
//}
package com.example.demo.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.AnnadanamTransaction;
//import com.example.demo.models.DonationRequest;  // Import the new composite class
import com.example.demo.services.AnnadanamService;

@CrossOrigin("*")
@RestController
@RequestMapping("/donation")
public class AnnadanamController {
    @Autowired
    AnnadanamService ds;

    @PostMapping("/annadanam")
    public ResponseEntity<Map<String, Object>> insertAnnadanamData(@RequestBody AnnadanamTransaction annadanamTransaction) {
        Map<String, Object> result = ds.insertAnnadanam(annadanamTransaction);
        if ("success".equals(result.get("status"))) {
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } else if ("failure".equals(result.get("status"))) {
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/dates/{templeId}")
    public ResponseEntity<Map<String,Object>> getAnnadanamDates(@PathVariable int templeId){
    	Map<String,Object> result = ds.getDates(templeId);
    	 if ("success".equals(result.get("status"))) {
             return new ResponseEntity<>(result, HttpStatus.OK);
         } else if ("failure".equals(result.get("status"))) {
             return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
         } else {
             return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
         }
    }
    
}
