package com.fuel.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fuel.model.FuelBooking;
import com.fuel.model.OrderFuel;
import com.fuel.model.Rider;

public interface RiderService {

	void saveRider(Rider rider) throws SQLException;

	List<OrderFuel> getAvailableOrders(BigDecimal riderLatitude, BigDecimal riderLongitude) throws Exception;

	void acceptOrder(Long bookingId, int riderId);

	List<FuelBooking> getOrdersByRiderId(int riderId) throws SQLException;

	List<Rider> getAllRiders();

	void updateRiderStatus(int riderId, String registrationStatus, int verified);

	OrderFuel getBookingLocation(Long bookingId) throws Exception;

	String verifyAndDeliver(Long bookingId, String otp);

	List<FuelBooking> getEarnOrdersByRiderId(int riderId) throws SQLException;
}
