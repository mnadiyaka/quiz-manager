package com.sigma.service;

import com.sigma.dto.LocationDto;
import com.sigma.model.Location;

import java.util.List;

public interface LocationService {

    public List<LocationDto> getAllLocations();

    public LocationDto findLocationById(Long locationId);

    public Location createLocation(LocationDto location);

    public void updateLocation(LocationDto updatedLocation, Long locationId);

    public void deleteLocation(Long locationId);
}