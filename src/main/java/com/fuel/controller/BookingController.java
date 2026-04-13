package com.fuel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.fuel.model.FuelBooking;
import com.fuel.model.LoginUserBean;
import com.fuel.service.BookingService;

@Controller
@SessionAttributes("LoginUserBean")
public class BookingController {

	@Autowired
	private BookingService bookingService;

	@GetMapping("/bookingdash")
	public String dashboard(Model model, @ModelAttribute("LoginUserBean") LoginUserBean lub) {
		model.addAttribute("defaultLat", 20.2961);
		model.addAttribute("defaultLng", 85.8245);
		return "customer/bookfuel";
	}
	
	@GetMapping("/bookfuel")
	public String bookfuel(Model model, @ModelAttribute("LoginUserBean") LoginUserBean lub) {
		model.addAttribute("defaultLat", 20.2961);
		model.addAttribute("defaultLng", 85.8245);
		return "customer/bookfuel";
	}

	@GetMapping("/payment")
	public String paymentPage(@ModelAttribute("LoginUserBean") LoginUserBean lub) {
		return "order/payment";
	}

	@PostMapping("/confirm-payment")
	@ResponseBody
	public String confirmPayment(@RequestBody FuelBooking order, @ModelAttribute("LoginUserBean") LoginUserBean lub) {

		try {
			order.setCustomerId(lub.getLogincustomerid());
			order.setStatus("CONFIRMED");

			bookingService.saveBooking(order);
			return "Payment successful";

		} catch (Exception e) {
			e.printStackTrace();
			return "Payment failed";
		}
	}

}
