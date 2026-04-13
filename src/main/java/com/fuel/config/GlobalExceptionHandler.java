package com.fuel.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	 @ExceptionHandler(IllegalStateException.class)
	    public String handleSessionExpired(IllegalStateException ex) {
	        if (ex.getMessage().contains("Expected session attribute 'LoginUserBean'")) {
	            return "redirect:/index";
	        }
	        throw ex; // rethrow other IllegalStateExceptions
	    }

}
