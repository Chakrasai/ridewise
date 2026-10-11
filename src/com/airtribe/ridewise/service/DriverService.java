package com.airtribe.ridewise.service;

import com.airtribe.ridewise.model.Driver;
import com.airtribe.ridewise.model.VehicleType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DriverService implements DriverProvider {
	private final List<Driver> drivers = new ArrayList<>();

	public Driver register(String id, String name) {
		return register(id, name, "UNKNOWN", VehicleType.CAR);
	}

	public Driver register(String id, String name, String currentLocation, VehicleType vehicleType) {
		if (findById(id) != null) {
			throw new IllegalArgumentException("Driver already exists: " + id);
		}
		Driver driver = new Driver(id, name, currentLocation, vehicleType);
		drivers.add(driver);
		return driver;
	}

	public Driver findById(String id) {
		return drivers.stream().filter(driver -> driver.getId().equals(id)).findFirst().orElse(null);
	}

	@Override
	public List<Driver> getAvailableDrivers() {
		return drivers.stream().filter(Driver::isAvailable).collect(Collectors.toList());
	}

	public void updateAvailability(String id, boolean available) {
		Driver driver = findById(id);
		if (driver == null) {
			throw new IllegalArgumentException("Driver not found: " + id);
		}
		if (available && driver.getActiveRideCount() > 0) {
			throw new IllegalStateException("Driver cannot become available during an active ride");
		}
		driver.setAvailable(available);
	}

	public List<Driver> getAll() {
		return Collections.unmodifiableList(drivers);
	}
}
