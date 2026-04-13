package com.fuel.serviceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fuel.model.Admin;
import com.fuel.model.Customer;
import com.fuel.model.Rider;
import com.fuel.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private DataSource dataSource;

	@Override
	public Admin checkUserLogin(String username, String password) throws SQLException {
		String sql = "SELECT * FROM admin WHERE email = ? AND password = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, username);
			ps.setString(2, password);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					Admin user = new Admin();
					user.setAdminId(rs.getInt("admin_id"));
					user.setEmail(rs.getString("email"));
					return user;
				}
			}
		}
		return null;
	}

	@Override
	public Admin getAdminbyId(int userId) throws SQLException {
		String sql = "SELECT * FROM admin  where admin_id=?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, userId);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					Admin ad = new Admin();
					ad.setAdminId(rs.getInt("admin_id"));
					ad.setEmail(rs.getString("email"));
					ad.setName(rs.getString("name"));
					return ad;
				}
			}
		}
		return null;
	}

	@Override
	public Customer checkUserLoginbyCustomer(String username, String password) throws SQLException {
		String sql = "SELECT * FROM customer WHERE email = ? AND password = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, username);
			ps.setString(2, password);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					Customer user = new Customer();
					user.setId(rs.getInt("id"));
					user.setFullName(rs.getString("full_name"));
					user.setEmail(rs.getString("email"));
					user.setPhone(rs.getString("contact_number"));
					user.setState(rs.getString("state"));
					return user;
				}
			}
		}
		return null;
	}

	@Override
	public Rider checkUserLoginbyRider(String email, String password) throws SQLException {

		String sql = "SELECT * FROM riders WHERE email = ? AND password = ?";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, email);
			ps.setString(2, password);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {

					Rider rider = new Rider();
					rider.setId(rs.getInt("id"));
					rider.setFullName(rs.getString("full_name"));
					rider.setEmail(rs.getString("email"));
					rider.setContactNumber(rs.getString("contact_number"));
					rider.setState(rs.getString("state"));
					rider.setCity(rs.getString("city"));
					rider.setVehicleType(rs.getString("vehicle_type"));
					rider.setVehicleNumber(rs.getString("vehicle_number"));
					rider.setServiceType(rs.getString("service_type"));
					rider.setAvailability(rs.getString("availability"));
					rider.setVerified(rs.getInt("verified"));

					return rider;
				}
			}
		}
		return null;
	}

	@Override
	public Customer getCustomerById(int customerId) throws SQLException {
		String sql = "SELECT * FROM customer  where id=?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, customerId);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					Customer ad = new Customer();
					ad.setId(rs.getInt("id"));
					ad.setEmail(rs.getString("email"));
					ad.setFullName(rs.getString("full_name"));
					return ad;
				}
			}
		}
		return null;
	}

}
