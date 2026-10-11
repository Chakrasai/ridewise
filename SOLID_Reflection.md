# SOLID Reflection

## Single Responsibility

Models hold domain state, services coordinate use cases, and strategies contain
replaceable matching or pricing rules.

## Open/Closed and Dependency Inversion

`RideService` depends on `RideMatchingStrategy`, `FareStrategy`, and
`DriverProvider` interfaces. A new pricing algorithm or driver matching rule
can be added without modifying the ride workflow. A different driver source
can also implement `DriverProvider` without changing `RideService`.

## Maintainability

The in-memory services are small and focused. Constructor injection makes each
dependency visible and keeps the orchestration code easy to test with fakes or
alternative strategies.

