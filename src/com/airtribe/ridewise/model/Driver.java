package com.airtribe.ridewise.model;

public class Driver {
	private final String id;
	private final String name;
	private String currentLocation;
	private final VehicleType vehicleType;
	private boolean available = true;
	private int activeRideCount;

	public Driver(String id, String name) {
		this(id, name, "", VehicleType.CAR);
	}

	public Driver(String id, String name, String currentLocation, VehicleType vehicleType) {
		if (id == null || id.trim().isEmpty() || name == null || name.trim().isEmpty()) {
			throw new IllegalArgumentException("Driver id and name are required");
		}
		if (currentLocation == null || vehicleType == null) {
			throw new IllegalArgumentException("Driver location and vehicle type are required");
		}
		this.id = id;
		this.name = name;
		this.currentLocation = currentLocation;
		this.vehicleType = vehicleType;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(String currentLocation) {
		if (currentLocation == null) {
			throw new IllegalArgumentException("Current location is required");
		}
		this.currentLocation = currentLocation;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	public boolean isAvailable() {
		return available;
	}

	public int getActiveRideCount() {
		return activeRideCount;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public void startRide() {
		activeRideCount++;
		available = false;
	}

	public void finishRide() {
		if (activeRideCount > 0) {
			activeRideCount--;
		}
		available = true;
	}
}
