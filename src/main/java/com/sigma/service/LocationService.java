package com.sigma.service;

import com.sigma.model.Location;

import java.util.List;

public interface LocationService {

    public List<Location> getAllLocations();

    public Location findLocationById(Long locationId);

    public Location createLocation(Location location);

    public void updateLocation(Location updatedLocation, Long locationId);

    public void deleteLocation(Long locationId);
}