package com.airtribe.ridewise.model;

import java.time.Instant;

public class FareReceipt {
	private final String rideId;
	private final double amount;
	private final Instant generatedAt;

	public FareReceipt(String rideId, double amount) {
		this(rideId, amount, Instant.now());
	}

	public FareReceipt(String rideId, double amount, Instant generatedAt) {
		this.rideId = rideId;
		this.amount = amount;
		this.generatedAt = generatedAt;
	}

	public String getRideId() { return rideId; }
	public double getAmount() { return amount; }
	public Instant getGeneratedAt() { return generatedAt; }

	@Override
	public String toString() {
		return "FareReceipt{rideId='" + rideId + "', amount=" + amount + ", generatedAt=" + generatedAt + "}";
	}
}
