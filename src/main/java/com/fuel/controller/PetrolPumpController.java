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

import com.fuel.model.LoginUserBean;
import com.fuel.model.PetrolPump;
import com.fuel.service.PetrolPumpService;

@Controller
@SessionAttributes("LoginUserBean")
public class PetrolPumpController {

	@Autowired
	private PetrolPumpService petrolPumpService;


	

	// API Controller
	@GetMapping("/nearby-pumps")
	@ResponseBody
	public List<PetrolPump> nearbyPumps(
	        @RequestParam(name = "lat") double lat,
	        @RequestParam(name = "lng") double lng,
	        @RequestParam(name = "fuelType", required = false) String fuelType,
	        @ModelAttribute("LoginUserBean") LoginUserBean lub) {
		System.out.println("Hello");
	    return petrolPumpService.findNearbyPumps(lat, lng, fuelType);
	}

}
