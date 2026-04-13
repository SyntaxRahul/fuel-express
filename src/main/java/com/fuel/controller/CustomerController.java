package com.fuel.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.fuel.model.Customer;
import com.fuel.model.FuelBooking;
import com.fuel.model.LoginUserBean;
import com.fuel.model.PetrolPump;
import com.fuel.service.CustomerService;

@Controller
@SessionAttributes("LoginUserBean")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@GetMapping("/customerform")
	public String showCustomerForm(Model model) {
		model.addAttribute("customer", new Customer());
		model.addAttribute("states", List.of("Odisha", "Delhi", "Maharashtra", "Odisha"));
		model.addAttribute("registrationSuccess", false);
		return "customer/customerform";
	}

	@GetMapping("/logincustomer")
	public String login() {
		return "customer/logincustomer";
	}

	@PostMapping("/registercustomer")
	public String registerCustomer(@ModelAttribute("customer") Customer customer, Model model) {
		customerService.saveCustomer(customer);
		return "customer/logincustomer";
	}

	@GetMapping("/customers")
	public String customerList(Model model,@ModelAttribute("LoginUserBean") LoginUserBean lub) {
		model.addAttribute("customers", customerService.getAllCustomers());
		return "customer-list";
	}

	@GetMapping("/csorders")
	public String viewRiderOrders(@ModelAttribute("LoginUserBean") LoginUserBean lub, Model model) throws SQLException {
		int loginriderid = lub.getLogincustomerid();
		System.out.println("loginriderid "+loginriderid);
		List<FuelBooking> customerorders = customerService.getOrdersByCustomerId(loginriderid);
		model.addAttribute("customerorders", customerorders);
		return "customer/customerorder";
	}
}
