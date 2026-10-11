package com.airtribe.ridewise.strategy;

import com.airtribe.ridewise.model.Ride;

public class DefaultFareStrategy implements FareStrategy {
	private final double baseFare;
	private final double perKilometreRate;

	public DefaultFareStrategy() {
		this(50.0, 12.0);
	}

	public DefaultFareStrategy(double baseFare, double perKilometreRate) {
		this.baseFare = baseFare;
		this.perKilometreRate = perKilometreRate;
	}

	@Override
	public double calculateFare(Ride ride) {
		return baseFare + ride.getDistanceKm() * perKilometreRate;
	}
}
