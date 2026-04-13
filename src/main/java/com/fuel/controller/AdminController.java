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
import com.fuel.service.BookingService;
import com.fuel.service.CustomerService;
import com.fuel.service.PetrolPumpService;
import com.fuel.service.RiderService;

@Controller
@SessionAttributes("LoginUserBean")
public class AdminController {

	@Autowired
	private PetrolPumpService petrolPumpService;

	@Autowired
	private RiderService riderService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private BookingService bookingService;

	@GetMapping("/adashboard")
	public String showadDashboard(Model model, @ModelAttribute("LoginUserBean") LoginUserBean lub) {
		model.addAttribute("registeredPumps", 5);
		model.addAttribute("pendingApplications", 3);
		model.addAttribute("activeCustomers", 1247);
		model.addAttribute("monthlyRevenue", 284500);
		System.out.println("lub.getLoginname();" + lub.getLoginname());
		return "admin/dashboard";
	}

	@GetMapping("/registerpump")
	public String showForm(Model model, @ModelAttribute("LoginUserBean") LoginUserBean lub) {
		model.addAttribute("petrolPump", new PetrolPump());
		return "admin/petrolform";
	}

	@PostMapping("/savepetrolpump")
	public String registerPump(@ModelAttribute PetrolPump petrolPump,
			@ModelAttribute("LoginUserBean") LoginUserBean lub, Model model) {
		try {
			petrolPumpService.registerPump(petrolPump);
			model.addAttribute("success", true);
		} catch (SQLException e) {
			e.printStackTrace();
			model.addAttribute("error", "Database error: " + e.getMessage());
		}
		return "redirect:/mypumps";
	}

	@GetMapping("/mypumps")
	public String mypumps(Model model, @ModelAttribute("LoginUserBean") LoginUserBean lub) {
		model.addAttribute("pumps", petrolPumpService.getAllPetrolPumps());
		return "admin/mypumps";
	}

	@GetMapping("/riders")
	public String ridersPage(Model model, @ModelAttribute("LoginUserBean") LoginUserBean lub) {
		model.addAttribute("riders", riderService.getAllRiders());
		return "admin/riderlist";
	}

	@PostMapping("/verify")
	@ResponseBody
	public String verifyRider(@RequestParam("id") int id, @ModelAttribute("LoginUserBean") LoginUserBean lub,
			@RequestParam("registrationStatus") String registrationStatus, @RequestParam("verified") int verified) {

		try {
			riderService.updateRiderStatus(id, registrationStatus, verified);
			return "Rider updated successfully";
		} catch (RuntimeException e) {
			return "Failed to update rider: " + e.getMessage();
		}
	}

	@GetMapping("/allcustomers")
	public String listCustomers(Model model, @ModelAttribute("LoginUserBean") LoginUserBean lub) throws SQLException {
		List<Customer> customers = customerService.getAllCustomers();
		model.addAttribute("customers", customers);
		return "admin/customerlist";
	}

	@GetMapping("/allorders")
	public String getAllBookings(Model model, @ModelAttribute("LoginUserBean") LoginUserBean lub) {
		List<FuelBooking> allBookings = bookingService.getAllBookings();
		model.addAttribute("allBookings", allBookings);
		return "admin/orderlist";
	}

}
