package com.fuel.service;

import java.sql.SQLException;

import com.fuel.model.Admin;
import com.fuel.model.Customer;
import com.fuel.model.Rider;

public interface LoginService {
	
	public Admin checkUserLogin(String username, String password) throws SQLException;
	
	public Customer checkUserLoginbyCustomer(String username, String password) throws SQLException;
	
	public Admin getAdminbyId(int userId) throws SQLException;
	
	public Customer getCustomerById(int customerId) throws SQLException;
	
	public Rider checkUserLoginbyRider(String email, String password) throws SQLException;
}
