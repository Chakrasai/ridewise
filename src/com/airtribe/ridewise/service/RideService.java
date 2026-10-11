package com.airtribe.ridewise.service;

import com.airtribe.ridewise.model.Driver;
import com.airtribe.ridewise.model.FareReceipt;
import com.airtribe.ridewise.model.Ride;
import com.airtribe.ridewise.model.Rider;
import com.airtribe.ridewise.strategy.FareStrategy;
import com.airtribe.ridewise.strategy.RideMatchingStrategy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RideService {
    private final DriverProvider driverProvider;
    private final RideMatchingStrategy matchingStrategy;
    private final FareStrategy fareStrategy;
    private final List<Ride> rides = new ArrayList<>();

    public RideService(DriverProvider driverProvider, RideMatchingStrategy matchingStrategy, FareStrategy fareStrategy) {
        if (driverProvider == null || matchingStrategy == null || fareStrategy == null) {
            throw new IllegalArgumentException("Ride service dependencies are required");
        }
        this.driverProvider = driverProvider;
        this.matchingStrategy = matchingStrategy;
        this.fareStrategy = fareStrategy;
    }

    public Ride requestRide(String id, Rider rider, String pickup, String destination, double distanceKm) {
        Ride ride = new Ride(id, rider, pickup, destination, distanceKm);
        Driver driver = matchingStrategy.findDriver(rider, driverProvider.getAvailableDrivers());
        if (driver == null) {
            throw new IllegalStateException("No available driver for ride " + id);
        }
        ride.assignDriver(driver);
        ride.setFare(fareStrategy.calculateFare(ride));
        driver.startRide();
        rides.add(ride);
        return ride;
    }

    public FareReceipt getFareReceipt(Ride ride) {
        return new FareReceipt(ride.getId(), ride.getFare());
    }

    public void completeRide(Ride ride) {
        ride.complete();
        ride.getDriver().finishRide();
    }

    public void cancelRide(Ride ride) {
        ride.cancel();
        if (ride.getDriver() != null) {
            ride.getDriver().finishRide();
        }
    }

    public List<Ride> getAllRides() {
        return Collections.unmodifiableList(rides);
    }

    public Ride findById(String id) {
        return rides.stream().filter(ride -> ride.getId().equals(id)).findFirst().orElse(null);
    }
}