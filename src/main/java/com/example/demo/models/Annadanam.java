package com.example.demo.models;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class Annadanam {
int donationId;
String donorName;
String phoneNumber;
String email;
String address;
String templeName;
int templeId;
String amount;
int noOfDays;
int transactionStatus;
private List<String> dates;
String createdAt;
String updatedAt;
public int getDonationId() {
	return donationId;
}
public void setDonationId(int donationId) {
	this.donationId = donationId;
}
public String getDonorName() {
	return donorName;
}
public void setDonorName(String donorName) {
	this.donorName = donorName;
}
public String getPhoneNumber() {
	return phoneNumber;
}
public void setPhoneNumber(String phoneNumber) {
	this.phoneNumber = phoneNumber;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getTempleName() {
	return templeName;
}
public void setTempleName(String templeName) {
	this.templeName = templeName;
}
public int getTempleId() {
	return templeId;
}
public void setTempleId(int templeId) {
	this.templeId = templeId;
}
public String getAmount() {
	return amount;
}
public void setAmount(String amount) {
	this.amount = amount;
}
public int getNoOfDays() {
	return noOfDays;
}
public void setNoOfDays(int noOfDays) {
	this.noOfDays = noOfDays;
}
public int getTransactionStatus() {
	return transactionStatus;
}
public void setTransactionStatus(int transactionStatus) {
	this.transactionStatus = transactionStatus;
}
public List<String> getDates() {
	return dates;
}
public void setDates(List<String> dates) {
	this.dates = dates;
}
public String getCreatedAt() {
	return createdAt;
}
public void setCreatedAt(String createdAt) {
	this.createdAt = createdAt;
}
public String getUpdatedAt() {
	return updatedAt;
}
public void setUpdatedAt(String updatedAt) {
	this.updatedAt = updatedAt;
}



}
