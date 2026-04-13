package com.fuel.serviceImpl;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fuel.model.Customer;
import com.fuel.model.FuelBooking;
import com.fuel.model.OrderFuel;
import com.fuel.model.OrderFuel.OrderStatus;
import com.fuel.model.Rider;
import com.fuel.service.RiderService;

@Service
public class RiderServiceImpl implements RiderService {

	@Autowired
	private DataSource dataSource;

//	private static final String UPLOAD_DIR = "D:/Project/Micro_projects/riderimage/";
	@Override
	public void saveRider(Rider rider) throws SQLException {
		String sql = """
				INSERT INTO riders (
				    full_name, date_of_birth, gender, contact_number, email, emergency_contact, address,
				    city, state, pincode, vehicle_type, vehicle_brand, vehicle_model, vehicle_year,
				    vehicle_number, license_number, license_expiry,
				    service_type, availability, areas_covered, password
				)
				VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
				""";
		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, rider.getFullName());
			ps.setDate(2, Date.valueOf(rider.getDateOfBirth()));
			ps.setString(3, rider.getGender());
			ps.setString(4, rider.getContactNumber());
			ps.setString(5, rider.getEmail());
			ps.setString(6, rider.getEmergencyContact());
			ps.setString(7, rider.getAddress());
			ps.setString(8, rider.getCity());
			ps.setString(9, rider.getState());
			ps.setString(10, rider.getPincode());
			ps.setString(11, rider.getVehicleType());
			ps.setString(12, rider.getVehicleBrand());
			ps.setString(13, rider.getVehicleModel());
			ps.setInt(14, rider.getVehicleYear());
			ps.setString(15, rider.getVehicleNumber());
			ps.setString(16, rider.getLicenseNumber());
			ps.setDate(17, Date.valueOf(rider.getLicenseExpiry()));
//			ps.setBytes(18, rider.getProfileImage());
			ps.setString(18, rider.getServiceType());
			ps.setString(19, rider.getAvailability());
			ps.setString(20, rider.getAreasCovered());
			ps.setString(21, rider.getPassword());

			ps.executeUpdate();
		}
	}

	@Override
	public List<Rider> getAllRiders() {
		List<Rider> riders = new ArrayList<>();
		String sql = """
				SELECT id, full_name, email, contact_number,
				       city, state, vehicle_type, vehicle_number,
				       license_number, registration_status, verified
				FROM riders
				""";
		try (Connection conn = dataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				Rider rider = new Rider();
				rider.setId(rs.getInt("id"));
				rider.setFullName(rs.getString("full_name"));
				rider.setEmail(rs.getString("email"));
				rider.setContactNumber(rs.getString("contact_number"));
				rider.setCity(rs.getString("city"));
				rider.setState(rs.getString("state"));
				rider.setVehicleType(rs.getString("vehicle_type"));
				rider.setVehicleNumber(rs.getString("vehicle_number"));
				rider.setLicenseNumber(rs.getString("license_number"));
				rider.setRegistrationStatus(rs.getString("registration_status"));
				rider.setVerified(rs.getInt("verified"));

				riders.add(rider);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return riders;
	}

	@Override
	public List<OrderFuel> getAvailableOrders(BigDecimal riderLatitude, BigDecimal riderLongitude) throws Exception {
		List<OrderFuel> orders = new ArrayList<>();
		String sql = """
									     SELECT
				    b.booking_id,
				    b.customer_id,
				    b.pump_id,
				    b.pump_name,
				    b.fuel_type,
				    b.quantity,
				    b.total_amount,
				    b.vehicle_number,
				    b.delivery_address,
				    b.status,
				    b.order_date,
				    b.delivery_time_slot,
				    b.payment_method,
				    b.latitude,
				    b.longitude,
				    c.id AS c_id,
				    c.full_name,
				    c.contact_number,
				    (
				      6371 * acos(
				        LEAST(1, GREATEST(-1,
				          cos(radians(?)) *
				          cos(radians(b.latitude)) *
				          cos(radians(b.longitude) - radians(?)) +
				          sin(radians(?)) *
				          sin(radians(b.latitude))
				        ))
				      )
				    ) AS distance
				FROM fuel_bookings b
				JOIN customer c ON b.customer_id = c.id
				WHERE b.latitude IS NOT NULL
				  AND b.longitude IS NOT NULL
				  AND (b.riderstatus IS NULL
				       OR b.riderstatus NOT IN ('CONFIRMED', 'DELIVERED'))
				HAVING distance <= 10
				ORDER BY distance ASC
				LIMIT 1;  """;

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setBigDecimal(1, riderLatitude);
			ps.setBigDecimal(2, riderLongitude);
			ps.setBigDecimal(3, riderLatitude);
			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					OrderFuel order = new OrderFuel();
					order.setId(rs.getLong("booking_id"));
					order.setCustomerId(rs.getLong("customer_id"));
					order.setPumpId(rs.getLong("pump_id"));
					order.setFuelType(rs.getString("fuel_type"));
					order.setQuantity(rs.getBigDecimal("quantity"));
					order.setTotalAmount(rs.getBigDecimal("total_amount"));
					order.setVehicleNumber(rs.getString("vehicle_number"));
					order.setDeliveryAddress(rs.getString("delivery_address"));
					order.setStatus(OrderStatus.valueOf(rs.getString("status")));
					order.setOrderDate(rs.getTimestamp("order_date"));
					order.setPaymentMethod(rs.getString("payment_method"));
					order.setCustomerLatitude(rs.getBigDecimal("latitude"));
					order.setCustomerLongitude(rs.getBigDecimal("longitude"));
					Customer customer = new Customer();
					customer.setId(rs.getInt("c_id"));
					customer.setFullName(rs.getString("full_name"));
					customer.setPhone(rs.getString("contact_number"));
					order.setCustomer(customer);

					System.out.println("customer " + customer.getCity());
					System.out.println("customer " + customer.getFullName());
					System.out.println("customer " + customer.getEmail());

					Double distance = rs.getObject("distance", Double.class);
					if (distance == null) {
						System.out.println("❌ Distance is NULL from DB");
						continue;
					}
					order.setDistance(distance);

					System.out.println("customerdistance " + order.getDistance());

					DecimalFormat df = new DecimalFormat("#.#");
					order.setDistanceFormatted(df.format(distance));

					orders.add(order);
				}
			}
		}

		return orders;
	}

	private int generateOtp() {
		return 100000 + new java.util.Random().nextInt(900000);
	}

	@Override
	public void acceptOrder(Long bookingId, int riderId) {

		String sql = """
				UPDATE fuel_bookings
				SET riderstatus = 'CONFIRMED',
				    rider_id = ?,
				    otp = ?
				WHERE booking_id = ?
				  AND (riderstatus IS NULL OR riderstatus <> 'CONFIRMED')
				""";

		int otp = generateOtp();

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setLong(1, riderId);
			ps.setInt(2, otp);
			ps.setLong(3, bookingId);

			int updated = ps.executeUpdate();
			if (updated == 0) {
				throw new RuntimeException("Order already confirmed or not found");
			}

		} catch (Exception e) {
			throw new RuntimeException("Failed to accept order", e);
		}
	}

	@Override
	public OrderFuel getBookingLocation(Long bookingId) throws Exception {
		String sql = """
					SELECT
						booking_id,
						customer_id,
						latitude,
						longitude
					FROM fuel_bookings
					WHERE booking_id = ?
				""";
		try (Connection con = dataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setLong(1, bookingId);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					OrderFuel order = new OrderFuel();
					order.setId(rs.getLong("booking_id"));
					order.setCustomerId(rs.getLong("customer_id"));
					order.setCustomerLatitude(rs.getBigDecimal("latitude"));
					order.setCustomerLongitude(rs.getBigDecimal("longitude"));
					System.out.println("order.setCustomerLatitude " + order.getCustomerLatitude());
					System.out.println("order.setCustomerLongitude " + order.getCustomerLongitude());

					return order;
				}
			}
		}
		return null;
	}

	@Override
	public List<FuelBooking> getOrdersByRiderId(int riderId) throws SQLException {

		List<FuelBooking> orders = new ArrayList<>();

		String sql = "SELECT * FROM fuel_bookings WHERE rider_id = ? ORDER BY order_date DESC";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, riderId);

			try (ResultSet rs = ps.executeQuery()) {

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
					booking.setOrderDate(rs.getString("order_date"));
					booking.setOtp(rs.getInt("otp"));

					orders.add(booking);
				}
			}
		}
		return orders;
	}

	@Override
	public void updateRiderStatus(int riderId, String registrationStatus, int verified) {
		String sql = "UPDATE riders SET registration_status = ?, verified = ? WHERE id = ?";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, registrationStatus);
			ps.setInt(2, verified);
			ps.setInt(3, riderId);

			ps.executeUpdate(); // No return value needed

		} catch (SQLException e) {
			e.printStackTrace();
			// Optionally, throw a RuntimeException or handle error
			throw new RuntimeException("Error updating rider status", e);
		}
	}

	@Override
	public String verifyAndDeliver(Long bookingId, String otp) {

		String sql = """
				UPDATE fuel_bookings
				SET riderstatus = 'DELIVERED',
				 status='DELIVERED'
				WHERE booking_id = ?
				  AND otp = ?
				  AND riderstatus = 'CONFIRMED'
				""";

		try (Connection con = dataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setLong(1, bookingId);
			ps.setString(2, otp);

			int updated = ps.executeUpdate();

			if (updated > 0) {
				return "DELIVERED";
			} else {
				return "OTP_MISMATCH";
			}

		} catch (Exception e) {
			return "ERROR";
		}
	}

	@Override
	public List<FuelBooking> getEarnOrdersByRiderId(int riderId) throws SQLException {

		List<FuelBooking> orders = new ArrayList<>();

		final double EARNING_PER_ORDER = 50.0;

		String sql = """
				    SELECT *
				    FROM fuel_bookings
				    WHERE rider_id = ?
				    ORDER BY order_date DESC
				""";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, riderId);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {

					FuelBooking booking = new FuelBooking();

					booking.setBookingId(rs.getInt("booking_id"));
					booking.setPumpName(rs.getString("pump_name"));
					booking.setFuelType(rs.getString("fuel_type"));
					booking.setQuantity(rs.getDouble("quantity"));
					booking.setVehicleNumber(rs.getString("vehicle_number"));
					booking.setTotalAmount(rs.getDouble("total_amount"));
					booking.setOrderDate(rs.getString("order_date"));

					booking.setStatus(rs.getString("status"));
					booking.setRiderStatus(rs.getString("riderstatus"));

					// ✅ CORRECT EARNING LOGIC
					if ("DELIVERED".equalsIgnoreCase(rs.getString("riderstatus"))) {
						booking.setRiderEarning(EARNING_PER_ORDER);
					} else {
						booking.setRiderEarning(0.0);
					}

					orders.add(booking);
				}
			}
		}
		return orders;
	}

}
