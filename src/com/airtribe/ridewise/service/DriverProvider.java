package com.airtribe.ridewise.service;

import com.airtribe.ridewise.model.Driver;
import java.util.List;

public interface DriverProvider {
    List<Driver> getAvailableDrivers();
}