package com.airtribe.ridewise;

import com.airtribe.ridewise.model.Driver;
import com.airtribe.ridewise.model.Ride;
import com.airtribe.ridewise.model.Rider;
import com.airtribe.ridewise.model.VehicleType;
import com.airtribe.ridewise.service.DriverService;
import com.airtribe.ridewise.service.RideService;
import com.airtribe.ridewise.service.RiderService;
import com.airtribe.ridewise.strategy.DefaultFareStrategy;
import com.airtribe.ridewise.strategy.LeastActiveDriverStrategy;
import java.util.Scanner;

public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final RiderService RIDER_SERVICE = new RiderService();
    private static final DriverService DRIVER_SERVICE = new DriverService();
    private static final RideService RIDE_SERVICE = new RideService(
            DRIVER_SERVICE, new LeastActiveDriverStrategy(), new DefaultFareStrategy());

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            printMenu();
            String choice = SCANNER.nextLine().trim();
            try {
                switch (choice) {
                    case "1": addRider(); break;
                    case "2": addDriver(); break;
                    case "3": listAvailableDrivers(); break;
                    case "4": requestRide(); break;
                    case "5": completeRide(); break;
                    case "6": listRides(); break;
                    case "7": running = false; break;
                    default: System.out.println("Invalid choice. Select 1 to 7.");
                }
            } catch (IllegalArgumentException | IllegalStateException exception) {
                System.out.println("Error: " + exception.getMessage());
            }
        }
        System.out.println("Goodbye.");
    }

    private static void printMenu() {
        System.out.println("\n===== RideWise =====");
        System.out.println("1. Add Rider");
        System.out.println("2. Add Driver");
        System.out.println("3. View Available Drivers");
        System.out.println("4. Request Ride");
        System.out.println("5. Complete Ride");
        System.out.println("6. View Rides");
        System.out.println("7. Exit");
        System.out.print("Choose an option: ");
    }

    private static void addRider() {
        Rider rider = RIDER_SERVICE.register(read("Rider ID: "), read("Name: "), read("Location: "));
        System.out.println("Rider registered: " + rider.getId());
    }

    private static void addDriver() {
        String vehicle = read("Vehicle type (BIKE/AUTO/CAR): ").toUpperCase();
        Driver driver = DRIVER_SERVICE.register(
                read("Driver ID: "), read("Name: "), read("Current location: "), VehicleType.valueOf(vehicle));
        System.out.println("Driver registered: " + driver.getId());
    }

    private static void listAvailableDrivers() {
        if (DRIVER_SERVICE.getAvailableDrivers().isEmpty()) {
            System.out.println("No available drivers.");
            return;
        }
        for (Driver driver : DRIVER_SERVICE.getAvailableDrivers()) {
            System.out.println(driver.getId() + " - " + driver.getName() + " (" + driver.getVehicleType() + ")");
        }
    }

    private static void requestRide() {
        Ride ride = RIDE_SERVICE.requestRide(
                read("Ride ID: "),
                requireRider(read("Rider ID: ")),
                read("Pickup: "),
                read("Destination: "),
                readPositiveDouble("Distance (km): "));
        System.out.println("Ride assigned to " + ride.getDriver().getName() + ". Fare: " + ride.getFare());
    }

    private static void completeRide() {
        Ride ride = requireRide(read("Ride ID: "));
        RIDE_SERVICE.completeRide(ride);
        System.out.println("Ride completed: " + ride.getId());
    }

    private static void listRides() {
        if (RIDE_SERVICE.getAllRides().isEmpty()) {
            System.out.println("No rides found.");
            return;
        }
        for (Ride ride : RIDE_SERVICE.getAllRides()) {
            System.out.println(ride.getId() + " | " + ride.getStatus() + " | fare=" + ride.getFare());
        }
    }

    private static Rider requireRider(String id) {
        Rider rider = RIDER_SERVICE.findById(id);
        if (rider == null) throw new IllegalArgumentException("Rider not found: " + id);
        return rider;
    }

    private static Ride requireRide(String id) {
        Ride ride = RIDE_SERVICE.findById(id);
        if (ride == null) throw new IllegalArgumentException("Ride not found: " + id);
        return ride;
    }

    private static double readPositiveDouble(String prompt) {
        double value = Double.parseDouble(read(prompt));
        if (value <= 0) throw new IllegalArgumentException("Value must be greater than zero");
        return value;
    }

    private static String read(String prompt) {
        System.out.print(prompt);
        return SCANNER.nextLine().trim();
    }
}
