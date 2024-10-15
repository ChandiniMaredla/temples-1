package com.example.demo.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.models.OtherDonations;
import com.example.demo.models.OtherDonationsTransaction;
import com.example.demo.models.Transactions;

@Service
public class OtherDonationService {
@Autowired
JdbcTemplate jdbc;


// insert other donation data and transaction data
public Map<String,Object> insertOtherDonation(OtherDonationsTransaction otherDonationsTransaction){
	Map<String,Object> response = new HashMap<>();
	
	OtherDonations otherDonation = otherDonationsTransaction.getOtherDonation();
	Transactions transaction = otherDonationsTransaction.getTransaction();
	
	String donorName = otherDonation.getDonorName();
	String phoneNumber = otherDonation.getPhoneNumber();
	String email = otherDonation.getEmail();
	String address = otherDonation.getAddress();
	String templeName = otherDonation.getTempleName();
	int templeId = otherDonation.getTempleId();
	String amount = otherDonation.getAmount();
	int transactionStatus = otherDonation.getTransactionStatus();
	String transactionId = transaction.getTransactionId();
	String type = transaction.getType();
	
	try {
		//insert other donation data
	String query = "insert into otherDonationDetails (donorName, phoneNumber, email, address, templeName, templeId, amount, transactionStatus) "
			+ "values(?,?,?,?,?,?,?,?)";
	System.out.println("result"+query);
	int result = jdbc.update(query,donorName, phoneNumber, email, address, templeName, templeId, amount, transactionStatus);
	if(result>0) {
		Number otherDonationId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
		 // Insert into Transaction table using the retrieved donationId
        String insertTransactionQuery = "INSERT INTO transactionDetails (transactionId, templeId, otherDonationId, transactionStatus, type) VALUES (?, ?, ?, ?, ?)";
        int transactionResult = jdbc.update(insertTransactionQuery, transactionId, templeId, otherDonationId, transactionStatus, type);
        if (transactionResult > 0) {
            response.put("message", "Donation and transaction added successfully");
            response.put("status", "success");
        } else {
            response.put("message", "Transaction insertion failed");
            response.put("status", "failure");
        }
    } else {
        response.put("message", "Donation insertion failed");
        response.put("status", "failure");
    }
	}
	catch(Exception e) {
		  response.put("message", "Error occurred: " + e.getMessage());
          response.put("status", "error");	
	}

	return response;
	
}

}
