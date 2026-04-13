package com.fuel.service;

import java.sql.SQLException;
import java.util.List;

import com.fuel.model.Customer;
import com.fuel.model.FuelBooking;

public interface CustomerService {

	void saveCustomer(Customer customer);

	List<Customer> getAllCustomers();

	List<FuelBooking> getOrdersByCustomerId(int customerId) throws SQLException;

}
