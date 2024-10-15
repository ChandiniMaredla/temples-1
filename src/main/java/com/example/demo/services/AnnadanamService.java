//package com.example.demo.services;
//
//import java.sql.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Service;
//
//import com.example.demo.models.Annadanam;
//
//@Service
//public class DonationService {
//@Autowired
//JdbcTemplate jdbc;
//
//public Map<String,Object> insertAnnadanam(Annadanam annadanam) {
//	Map<String,Object> response = new HashMap<>();
// String donorName = annadanam.getDonorName();
//	String email = annadanam.getEmail();
//	String phoneNumber = annadanam.getPhoneNumber();
//	String address = annadanam.getAddress();
//	String templeName = annadanam.getTempleName();
//	int templeId = annadanam.getTempleId();
//	String amount = annadanam.getAmount();
//	int noOfDays = annadanam.getNoOfDays();
//	int transactionStatus = annadanam.getTransactionStatus();
//	Date startDate = annadanam.getStartDate();
//	Date endDate = annadanam.getEndDate();
//	try {
//	String query = "insert into annadanamDetails (donorName,phoneNumber,email,address, templeName, templeId, amount, noOfDays, transactionStatus, startDate, endDate) values(?,?,?,?,?,?,?,?,?,?,?)";
//	System.out.println("query"+query);
//	int result = jdbc.update(query,donorName,phoneNumber,email,address, templeName, templeId, amount, noOfDays, transactionStatus, startDate, endDate);
//	System.out.println("result"+result);
//    if (result > 0) {
//        response.put("message", "Donation added successfully");
//        response.put("status", "success");
//        response.put("data", annadanam);
//    } else {
//        response.put("message", "Insertion failed");
//        response.put("status", "failure");
//    }
//} catch (Exception e) {
//    response.put("message", "Error occurred: " + e.getMessage());
//    response.put("status", "error");
//}
//return response;
//	
//}
//	
//}

package com.example.demo.services;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.models.Annadanam;
import com.example.demo.models.Transactions;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.demo.models.AnnadanamTransaction;  // Import the new composite class

@Service
public class AnnadanamService {
    @Autowired
    JdbcTemplate jdbc;

    public Map<String, Object> insertAnnadanam(AnnadanamTransaction annadanamTransaction) {
        Map<String, Object> response = new HashMap<>();

        Annadanam annadanam = annadanamTransaction.getAnnadanam();
        Transactions transaction = annadanamTransaction.getTransaction();

        // Extracting fields
        String donorName = annadanam.getDonorName();
        String email = annadanam.getEmail();
        String phoneNumber = annadanam.getPhoneNumber();
        String address = annadanam.getAddress();
        String templeName = annadanam.getTempleName();
        int templeId = annadanam.getTempleId();
        String amount = annadanam.getAmount();
        int noOfDays = annadanam.getNoOfDays();
        int transactionStatus = annadanam.getTransactionStatus();
       List<String> dates= annadanam.getDates();
        String transactionId = transaction.getTransactionId();
        String type = transaction.getType();
        //convert list to JSON
        String datesJson = "";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
			datesJson = objectMapper.writeValueAsString(dates);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//keyholder
        try {
            // Insert into Annadanam table
            String query = "INSERT INTO annadanamDetails (donorName, phoneNumber, email, address, templeName, templeId, amount, noOfDays, transactionStatus, dates) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            int result = jdbc.update(query, donorName, phoneNumber, email, address, templeName, templeId, amount, noOfDays, transactionStatus, datesJson);

            if (result > 0) {
                // Retrieve the generated donationId
                Number annadanamDonationId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Long.class);

                // Insert into Transaction table using the retrieved donationId
                String insertTransactionQuery = "INSERT INTO transactionDetails (transactionId, templeId, annadanamDonationId, transactionStatus, type) VALUES (?, ?, ?, ?, ?)";
                int transactionResult = jdbc.update(insertTransactionQuery, transactionId, templeId, annadanamDonationId, transactionStatus, type);

                if (transactionResult > 0) {
                    response.put("message", "Donation and transaction added successfully");
                    response.put("status", "success");
                } else {
                    response.put("message", "Transaction insertion failed");
                    response.put("status", "failure");
                }
            } else {
                response.put("message", "Annadanam insertion failed");
                response.put("status", "failure");
            }
        } catch (Exception e) {
            response.put("message", "Error occurred: " + e.getMessage());
            response.put("status", "error");
        }

        return response;
    }
    
    
    
    // getting annadanam dates
    public Map<String,Object> getDates(int templeId){
    	String query = "select dates from annadanamDetails where templeId = ?";
    	Map<String,Object> response = new HashMap<>();
    	try {
    		List<String> bookedDates = new ArrayList<>(); 
    		List<Map<String,Object>> dates = jdbc.queryForList(query,templeId);
    		if(dates.size()!=0) {
    			  for (Map<String, Object> m : dates) {
    				 String s =  (String)m.get("dates");
    				  String ans = s.substring(1,s.length()-1);
    				  String finalDates[] = ans.split(",");
    				  for (String date : finalDates) {
    					    bookedDates.add(date.trim().replaceAll("\"", ""));  // Trim spaces and remove quotes
    					}
    	            }
    			  response.put("dates", bookedDates);
    			  response.put("status", "success");
    		}
    		else {
    			 response.put("message", "No dates found for the provided temple ID.");
    			 response.put("status", "failure");
    		}
    		
    	}
    	catch(Exception e) {
    		 response.put("error", e.getMessage());
    		 response.put("status", "error");
    	}
    	return response;
    }
}

