package com.fuel.serviceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fuel.model.PetrolPump;
import com.fuel.service.PetrolPumpService;

@Service
public class PetrolPumpServiceImpl implements PetrolPumpService {

	@Autowired
	private DataSource dataSource;

	@Override
	public void registerPump(PetrolPump pump) throws SQLException {
		String sql = "INSERT INTO petrol_pump (name, owner_name, contact_number, email, address, city, state, pincode, fuel_types, latitude, longitude) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, pump.getName());
			ps.setString(2, pump.getOwnerName());
			ps.setString(3, pump.getContactNumber());
			ps.setString(4, pump.getEmail());
			ps.setString(5, pump.getAddress());
			ps.setString(6, pump.getCity());
			ps.setString(7, pump.getState());
			ps.setString(8, pump.getPincode());
			ps.setString(9, String.join(",", pump.getFuelTypes())); // storing multiple types as CSV
			ps.setDouble(10, pump.getLatitude());
			ps.setDouble(11, pump.getLongitude());

			ps.executeUpdate();
		}
	}

	@Override
	public List<PetrolPump> findNearbyPumps(double userLat, double userLng, String fuelType) {
		List<PetrolPump> list = new ArrayList<>();

		String sql = "SELECT id, name, latitude, longitude, " + "(6371 * acos( "
				+ "cos(radians(?)) * cos(radians(latitude)) * " + "cos(radians(longitude) - radians(?)) + "
				+ "sin(radians(?)) * sin(radians(latitude)) )) AS distance " + "FROM petrol_pump "
				+ "WHERE (? IS NULL OR fuel_types LIKE ?) " + // handle null fuelType
				"HAVING distance <= 20 " + "ORDER BY distance";

		try (Connection con = dataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setDouble(1, userLat);
			ps.setDouble(2, userLng);
			ps.setDouble(3, userLat);

			if (fuelType != null && !fuelType.isEmpty()) {
				ps.setString(4, fuelType);
				ps.setString(5, "%" + fuelType + "%");
			} else {
				ps.setNull(4, java.sql.Types.VARCHAR);
				ps.setString(5, "%");
			}

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				PetrolPump p = new PetrolPump();
				p.setId(rs.getInt("id"));
				p.setName(rs.getString("name"));
				p.setLatitude(rs.getDouble("latitude"));
				p.setLongitude(rs.getDouble("longitude"));
				p.setDistance(rs.getDouble("distance"));
				list.add(p);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<PetrolPump> getAllPetrolPumps() {

		List<PetrolPump> pumps = new ArrayList<>();

		String sql = "SELECT * FROM petrol_pump ";

		try (Connection con = dataSource.getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {

				PetrolPump pump = new PetrolPump();
				pump.setId(rs.getInt("id"));
				pump.setName(rs.getString("name"));
				pump.setOwnerName(rs.getString("owner_name"));
				pump.setContactNumber(rs.getString("contact_number"));
				pump.setEmail(rs.getString("email"));
				pump.setAddress(rs.getString("address"));
				pump.setCity(rs.getString("city"));
				pump.setState(rs.getString("state"));
				pump.setPincode(rs.getString("pincode"));
				pump.setFuelTypes(rs.getString("fuel_types"));
				pump.setLatitude(rs.getDouble("latitude"));
				pump.setLongitude(rs.getDouble("longitude"));
				Timestamp ts = rs.getTimestamp("created_at");
				if (ts != null) {
					pump.setCreatedAt(ts.toLocalDateTime());
				}
				pumps.add(pump);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return pumps;
	}

}
