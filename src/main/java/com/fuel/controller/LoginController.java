package com.fuel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fuel.model.Admin;
import com.fuel.model.Customer;
import com.fuel.model.LoginUserBean;
import com.fuel.model.Rider;
import com.fuel.service.CustomerService;
import com.fuel.service.LoginService;

import jakarta.servlet.http.HttpSession;

@Controller
@SessionAttributes("LoginUserBean")
public class LoginController {

	@Autowired
	private LoginService loginService;

	@GetMapping("/index")
	public String home() {
		return "util/home";
	}

	@GetMapping("/login")
	public String login() {
		return "util/login";
	}

	@PostMapping("/loginreq")
	public String doLogin(@RequestParam("email") String username, @RequestParam("password") String password,
			@RequestParam("userType") String userType, Model model, HttpSession session,
			RedirectAttributes redirectAttributes) {
		try {

			if ("ADMIN".equalsIgnoreCase(userType)) {
				System.out.println("Hello");
				Admin user = loginService.checkUserLogin(username, password);

				if (user == null) {
					redirectAttributes.addFlashAttribute("errorMsg", "Invalid username or password");
					return "redirect:/login";
				}

				Admin admin = loginService.getAdminbyId(user.getAdminId());

				LoginUserBean lub = new LoginUserBean();
				lub.setLoginname(admin.getName());
				lub.setLoginemail(admin.getEmail());
				lub.setLoginadminId(admin.getAdminId());

				model.addAttribute("users", admin);
				model.addAttribute("LoginUserBean", lub);

				return "redirect:/adashboard";

			} else if ("CUSTOMER".equalsIgnoreCase(userType)) {
				System.out.println("Hello2");
				Customer customer = loginService.checkUserLoginbyCustomer(username, password);
				if (customer == null) {
					redirectAttributes.addFlashAttribute("errorMsg", "Invalid username or password");
					return "redirect:/logincustomer";
				}
				LoginUserBean lub = new LoginUserBean();
				Customer custId = loginService.getCustomerById(customer.getId());
				lub.setLogincustomerid(custId.getId());
				lub.setLogincustomername(custId.getFullName());
				lub.setLoginemail(custId.getEmail());
				model.addAttribute("customer", customer);
				model.addAttribute("LoginUserBean", lub);
				return "redirect:/bookingdash";

			} else if ("RIDER".equalsIgnoreCase(userType)) {
				System.out.println("Hello3");
				Rider rider = loginService.checkUserLoginbyRider(username, password);
				if (rider == null) {
					redirectAttributes.addFlashAttribute("errorMsg", "Invalid username or password");
					return "redirect:/riderlogin";
				}

				LoginUserBean lub = new LoginUserBean();
				lub.setLoginriderid(rider.getId());
				lub.setLoginriderfullName(rider.getFullName());
				lub.setLoginriderdateOfBirth(rider.getDateOfBirth());
				lub.setLoginridercontactNumber(rider.getContactNumber());
				lub.setLoginrideremail(rider.getEmail());
				lub.setLoginrideremergencyContact(rider.getEmergencyContact());
				lub.setLoginridergender(rider.getGender());
				lub.setLoginrideraddress(rider.getAddress());
				lub.setLoginridercity(rider.getCity());
				lub.setLoginriderstate(rider.getState());
				lub.setLoginriderpincode(rider.getPincode());

				lub.setLoginridervehicleType(rider.getVehicleType());
				lub.setLoginridervehicleBrand(rider.getVehicleBrand());
				lub.setLoginridervehicleModel(rider.getVehicleModel());
				lub.setLoginridervehicleYear(rider.getVehicleYear());
				lub.setLoginridervehicleNumber(rider.getVehicleNumber());

				lub.setLoginriderlicenseNumber(rider.getLicenseNumber());
				lub.setLoginriderlicenseExpiry(rider.getLicenseExpiry());
				model.addAttribute("rider", rider);
				model.addAttribute("LoginUserBean", lub);
				return "redirect:/rdashboard";

			}
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("errorMsg", "An error occurred. Please try again.");
			return "redirect:/login";
		}

		return "redirect:/login";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}

}
