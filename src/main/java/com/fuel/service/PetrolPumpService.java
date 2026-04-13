package com.fuel.service;

import java.sql.SQLException;
import java.util.List;

import com.fuel.model.PetrolPump;

public interface PetrolPumpService {

	public void registerPump(PetrolPump pump) throws SQLException;

	public List<PetrolPump> findNearbyPumps(double userLat, double userLng, String fuelType);

	public List<PetrolPump> getAllPetrolPumps();
}
