package com.fuel.serviceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fuel.model.FuelBooking;
import com.fuel.service.BookingService;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	private DataSource dataSource;

	@Override
	public FuelBooking saveBooking(FuelBooking booking) throws SQLException {

		String sql = "INSERT INTO fuel_bookings "
				+ "(customer_id, pump_id, pump_name, fuel_type, quantity, vehicle_number, "
				+ "delivery_time_slot, delivery_address, payment_method, base_cost, "
				+ "delivery_charge, tax, total_amount, status, latitude, longitude) "
				+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		try (Connection conn = dataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			ps.setInt(1, booking.getCustomerId());
			ps.setInt(2, booking.getPumpId());
			ps.setString(3, booking.getPumpName());
			ps.setString(4, booking.getFuelType());

			if (booking.getQuantity() != null)
				ps.setDouble(5, booking.getQuantity());
			else
				ps.setNull(5, Types.DOUBLE);

			ps.setString(6, booking.getVehicleNumber());
			ps.setString(7, booking.getDeliveryTimeSlot());
			ps.setString(8, booking.getDeliveryAddress());
			ps.setString(9, booking.getPaymentMethod());

			ps.setDouble(10, booking.getBaseCost());
			ps.setDouble(11, booking.getDeliveryCharge());
			ps.setDouble(12, booking.getTax());
			ps.setDouble(13, booking.getTotalAmount());
			ps.setString(14, booking.getStatus());

			if (booking.getLatitude() != null)
				ps.setDouble(15, booking.getLatitude());
			else
				ps.setNull(15, Types.DOUBLE);

			if (booking.getLongitude() != null)
				ps.setDouble(16, booking.getLongitude());
			else
				ps.setNull(16, Types.DOUBLE);

			ps.executeUpdate();

			try (ResultSet rs = ps.getGeneratedKeys()) {
				if (rs.next()) {
					booking.setBookingId(rs.getInt(1));
				}
			}
		}

		return booking;
	}

	@Override
	public List<FuelBooking> getAllBookings() {

		List<FuelBooking> bookings = new ArrayList<>();

		String sql = "SELECT * FROM fuel_bookings ORDER BY order_date DESC";

		try (Connection con = dataSource.getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				FuelBooking booking = new FuelBooking();

				booking.setBookingId(rs.getInt("booking_id"));
				booking.setCustomerId(rs.getInt("customer_id"));
				booking.setPumpId(rs.getInt("pump_id"));
				booking.setPumpName(rs.getString("pump_name"));
				booking.setFuelType(rs.getString("fuel_type"));
				booking.setQuantity(rs.getDouble("quantity"));
				booking.setVehicleNumber(rs.getString("vehicle_number"));
				booking.setDeliveryTimeSlot(rs.getString("delivery_time_slot"));
				booking.setDeliveryAddress(rs.getString("delivery_address"));
				booking.setPaymentMethod(rs.getString("payment_method"));
				booking.setBaseCost(rs.getDouble("base_cost"));
				booking.setDeliveryCharge(rs.getDouble("delivery_charge"));
				booking.setTax(rs.getDouble("tax"));
				booking.setTotalAmount(rs.getDouble("total_amount"));
				booking.setStatus(rs.getString("status"));
				booking.setRiderStatus(rs.getString("riderstatus"));
				booking.setRiderId(rs.getString("rider_id"));

				bookings.add(booking);
			}

		} catch (SQLException e) {
			throw new RuntimeException("Error fetching bookings", e);
		}

		return bookings;
	}

}
