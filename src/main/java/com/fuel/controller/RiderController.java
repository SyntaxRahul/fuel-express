package com.fuel.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fuel.model.FuelBooking;
import com.fuel.model.LoginUserBean;
import com.fuel.model.OrderFuel;
import com.fuel.model.Rider;
import com.fuel.service.RiderService;

@Controller
@SessionAttributes("LoginUserBean")
public class RiderController {

	@Autowired
	private RiderService riderService;

	public static final double EARNING_PER_ORDER = 50.0;

	@GetMapping("/riderlogin")
	public String login() {
		return "rider/riderlogin";
	}

	@GetMapping("/riderregister")
	public String showRegistrationForm(Model model) {
		model.addAttribute("rider", new Rider());
		model.addAttribute("states", List.of("Andhra Pradesh", "Telangana", "Odisha", "Maharashtra", "Karnataka"));
		return "rider/riderform";
	}

	@PostMapping("/register")
	public String registerRider(@ModelAttribute Rider rider, Model model) throws SQLException, IOException {
		riderService.saveRider(rider);
		model.addAttribute("registrationSuccess", true);
		return "redirect:/riderlogin";
	}

	@GetMapping("/rdashboard")
	public String riderDashboard(Model model, @ModelAttribute("LoginUserBean") LoginUserBean lub) {
		model.addAttribute("orders", List.of());
		return "rider/riderdashboard";
	}

	@GetMapping("/refresh")
	public String refreshOrders(@RequestParam("riderLat") BigDecimal riderLat,
			@RequestParam("riderLng") BigDecimal riderLng, Model model,
			@ModelAttribute("LoginUserBean") LoginUserBean lub) throws Exception {
		List<OrderFuel> orders = riderService.getAvailableOrders(riderLat, riderLng);
		model.addAttribute("orders", orders);
		return "rider/riderdashboard :: orderCards";
	}

	@PostMapping("/acceptorder")
	public String acceptOrder(@RequestParam("bookingId") Long bookingId, RedirectAttributes redirectAttributes,
			@ModelAttribute("LoginUserBean") LoginUserBean lub) {
		int riderId = lub.getLoginriderid();
		riderService.acceptOrder(bookingId, riderId);
		return "redirect:/rdashboard";
	}

	@GetMapping("/orders")
	public String viewRiderOrders(@ModelAttribute("LoginUserBean") LoginUserBean lub, Model model) throws SQLException {
		int loginriderid = lub.getLoginriderid();
		List<FuelBooking> orders = riderService.getOrdersByRiderId(loginriderid);
		model.addAttribute("orders", orders);
		return "rider/myorders";
	}

	@GetMapping("/track/{bookingId}")
	public String trackOrder(@PathVariable("bookingId") Long bookingId, Model model,
			@ModelAttribute("LoginUserBean") LoginUserBean lub) throws Exception {
		OrderFuel order = riderService.getBookingLocation(bookingId);
		if (order == null) {
			return "redirect:/rdashboard";
		}
		model.addAttribute("order", order);
		return "rider/livetrack"; // track.html
	}

	@PostMapping("/verifyotp")
	@ResponseBody
	public Map<String, String> verifyOtp(@RequestParam("bookingId") Long bookingId, @RequestParam("otp") String otp,
			@ModelAttribute("LoginUserBean") LoginUserBean lub) {
		String status = riderService.verifyAndDeliver(bookingId, otp);
		Map<String, String> response = new HashMap<>();
		response.put("status", status);
		return response;
	}

	@GetMapping("/totalearn")
	public String riderEarnings(@ModelAttribute("LoginUserBean") LoginUserBean lub, Model model) throws SQLException {
		int riderId = lub.getLoginriderid();
		List<FuelBooking> orders = riderService.getEarnOrdersByRiderId(riderId);
		double totalEarnings = 0.0;
		for (FuelBooking booking : orders) {
			totalEarnings += booking.getRiderEarning();
		}
		model.addAttribute("orders", orders);
		model.addAttribute("totalEarnings", totalEarnings);
		model.addAttribute("earningPerOrder", 50.0);
		model.addAttribute("riderId", riderId);
		return "rider/earnings";
	}

}
