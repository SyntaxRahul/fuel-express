package com.fuel.service;

import java.sql.SQLException;
import java.util.List;

import com.fuel.model.FuelBooking;

public interface BookingService {

	public FuelBooking saveBooking(FuelBooking booking) throws SQLException;

	public List<FuelBooking> getAllBookings();

}
