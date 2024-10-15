package com.example.demo.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DonationService {
@Autowired
JdbcTemplate jdbc;

public Map<String,Object> getDonations(String type){
	Map<String,Object> response = new HashMap<>();
	String query;
	if(type.equals("annadanam")) {
		query = "select * from annadanamDetails";
	}
	else {
		query = "select * from otherDonationDetails";
	}
	try {
		List<Map<String,Object>> list = jdbc.queryForList(query);
		if(list.size()!=0) {
			response.put("message","Details fetched successfully");
			response.put("data", list);
			response.put("status", "success");
		}
		else {
		   response.put("message", "No donation details found for the specified type");
           response.put("status", "failure");
       }
   } catch (Exception e) {
       response.put("error", e.getMessage());
       response.put("status", "error");
   }

   return response;
}
}
