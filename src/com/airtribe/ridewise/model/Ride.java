package com.airtribe.ridewise.model;

public class Ride {
	private final String id;
	private final Rider rider;
	private final String pickup;
	private final String destination;
	private final double distanceKm;
	private RideStatus status = RideStatus.REQUESTED;
	private Driver driver;
	private double fare;

	public Ride(String id, Rider rider, String pickup, String destination, double distanceKm) {
		if (id == null || id.trim().isEmpty() || rider == null) {
			throw new IllegalArgumentException("Ride id and rider are required");
		}
		if (pickup == null || pickup.trim().isEmpty() || destination == null || destination.trim().isEmpty()) {
			throw new IllegalArgumentException("Pickup and destination are required");
		}
		if (distanceKm <= 0) {
			throw new IllegalArgumentException("Distance must be greater than zero");
		}
		this.id = id;
		this.rider = rider;
		this.pickup = pickup;
		this.destination = destination;
		this.distanceKm = distanceKm;
	}

	public String getId() { return id; }
	public Rider getRider() { return rider; }
	public String getPickup() { return pickup; }
	public String getDestination() { return destination; }
	public double getDistanceKm() { return distanceKm; }
	public double getDistance() { return distanceKm; }
	public RideStatus getStatus() { return status; }
	public Driver getDriver() { return driver; }
	public double getFare() { return fare; }

	public void assignDriver(Driver driver) {
		if (status != RideStatus.REQUESTED) {
			throw new IllegalStateException("Only requested rides can be assigned");
		}
		this.driver = driver;
		this.status = RideStatus.ASSIGNED;
	}

	public void setFare(double fare) {
		if (fare < 0) {
			throw new IllegalArgumentException("Fare cannot be negative");
		}
		this.fare = fare;
	}

	public void complete() {
		requireStatus(RideStatus.ASSIGNED);
		status = RideStatus.COMPLETED;
	}

	public void cancel() {
		if (status == RideStatus.COMPLETED || status == RideStatus.CANCELLED) {
			throw new IllegalStateException("Ride cannot be cancelled in its current state");
		}
		status = RideStatus.CANCELLED;
	}

	private void requireStatus(RideStatus expected) {
		if (status != expected) {
			throw new IllegalStateException("Expected status " + expected + " but was " + status);
		}
	}
}
