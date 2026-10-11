package com.airtribe.ridewise.model;

public class Rider {
	private final String id;
	private final String name;
	private String location;

	public Rider(String id, String name) {
		this(id, name, "UNKNOWN");
	}

	public Rider(String id, String name, String location) {
		this.id = requireValue(id, "id");
		this.name = requireValue(name, "name");
		this.location = requireValue(location, "location");
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = requireValue(location, "location");
	}

	private static String requireValue(String value, String fieldName) {
		if (value == null || value.trim().isEmpty()) {
			throw new IllegalArgumentException(fieldName + " is required");
		}
		return value;
	}
}
