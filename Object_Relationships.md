# Object Relationships

```text
Rider 1 ---- * Ride * ---- 0..1 Driver

RideService --> DriverProvider
RideService --> RideMatchingStrategy
RideService --> FareStrategy
DriverService ..|> DriverProvider
```

`RideService` coordinates the ride lifecycle. It does not know how drivers are
stored, how a driver is selected, or how a fare is calculated. Those decisions
are supplied through interfaces, keeping the system loosely coupled and easy
to extend.

