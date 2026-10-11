package com.airtribe.ridewise.service;

import com.airtribe.ridewise.model.Rider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RiderService {
	private final List<Rider> riders = new ArrayList<>();

	public Rider register(String id, String name) {
		return register(id, name, "UNKNOWN");
	}

	public Rider register(String id, String name, String location) {
		if (findById(id) != null) {
			throw new IllegalArgumentException("Rider already exists: " + id);
		}
		Rider rider = new Rider(id, name, location);
		riders.add(rider);
		return rider;
	}

	public Rider findById(String id) {
		return riders.stream().filter(rider -> rider.getId().equals(id)).findFirst().orElse(null);
	}

	public List<Rider> getAll() {
		return Collections.unmodifiableList(riders);
	}
}
