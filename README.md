# RideWise

RideWise is a small modular ride-sharing system implemented in plain Java. It
supports rider and driver registration, driver availability, strategy-based
driver matching, strategy-based fare calculation, and ride lifecycle tracking.

## Features

- Register riders and drivers
- Store rider and driver locations
- Track driver availability and vehicle type
- View available drivers
- Request and assign rides
- Calculate fares through a replaceable pricing strategy
- Match drivers through a replaceable matching strategy
- Complete or cancel rides
- Track `REQUESTED`, `ASSIGNED`, `COMPLETED`, and `CANCELLED` states
- Generate fare receipts with their creation timestamp
- Use the interactive console menu in `Main.java`

## Requirements

- Java Development Kit 8 or newer
- PowerShell on Windows, or a shell with equivalent commands on macOS/Linux

No external libraries or build tools are required.

## Run the Application

Open a terminal at the repository root:

```text
C:\Users\chakr\OneDrive\Desktop\Airtribe\ridewise
```

### Windows PowerShell

```powershell
Remove-Item out -Recurse -Force -ErrorAction SilentlyContinue
New-Item -ItemType Directory out | Out-Null
javac -d out (Get-ChildItem src -Recurse -Filter *.java).FullName
java -cp out com.airtribe.ridewise.Main
```

### macOS/Linux

```bash
rm -rf out
mkdir out
javac -d out $(find src -name '*.java')
java -cp out com.airtribe.ridewise.Main
```

Compile the complete source tree. Running `javac Main.java` alone will fail
because `Main` depends on classes in the model, service, and strategy packages.

## Console Menu

```text
1. Add Rider
2. Add Driver
3. View Available Drivers
4. Request Ride
5. Complete Ride
6. View Rides
7. Exit
```

The application catches invalid menu choices, duplicate IDs, invalid vehicle
types, missing riders or rides, invalid distances, and invalid ride state
transitions without terminating the menu loop.

## Architecture

```text
src/com/airtribe/ridewise/
├── Main.java
├── model/
│   ├── Driver.java
│   ├── FareReceipt.java
│   ├── Ride.java
│   ├── RideStatus.java
│   ├── Rider.java
│   └── VehicleType.java
├── service/
│   ├── DriverProvider.java
│   ├── DriverService.java
│   ├── RideService.java
│   └── RiderService.java
└── strategy/
    ├── DefaultFareStrategy.java
    ├── FareStrategy.java
    ├── LeastActiveDriverStrategy.java
    ├── NearestDriverStrategy.java
    ├── PeakHourFareStrategy.java
    └── RideMatchingStrategy.java
```

### Service Layer

- `RiderService` registers riders and finds riders by ID.
- `DriverService` registers drivers, updates availability, and lists available
  drivers.
- `RideService` coordinates ride requests, matching, fare calculation, and
  completion.
- `DriverProvider` keeps `RideService` independent from the concrete driver
  storage implementation.

### Strategy Layer

`RideService` receives its strategies through constructor injection:

```java
RideService rideService = new RideService(
        driverService,
        new LeastActiveDriverStrategy(),
        new DefaultFareStrategy());
```

Matching strategies implement:

```java
Driver findDriver(Rider rider, List<Driver> drivers);
```

Fare strategies implement:

```java
double calculateFare(Ride ride);
```

To add a new pricing or matching rule, implement the relevant interface and
pass the new implementation to the `RideService` constructor. The ride
workflow does not need to change.

## Design Notes

See [SOLID_Reflection.md](SOLID_Reflection.md) for the SOLID principles used
in the project and [Object_Relationships.md](Object_Relationships.md) for the
domain relationships.

## Scope

This is an in-memory console application intended to demonstrate modular Java
design. Data is lost when the process exits. Persistence, authentication,
geospatial distance calculation, and concurrent ride handling are outside the
current scope.

## Validation

The project has been validated by compiling all Java files and running the
console workflow for registration, driver listing, ride request, fare
calculation, ride completion, and invalid input handling.