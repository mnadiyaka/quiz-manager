package com.sigma.service;

import com.sigma.model.dto.LocationDto;
import com.sigma.model.entity.Location;

import java.util.List;
import java.util.Set;

public interface LocationService {

    public Set<LocationDto> getAllLocations();

    public Location findLocationById(Long locationId);

    public Location createLocation(LocationDto location, Long userId);

    public void updateLocation(LocationDto updatedLocation, Long userId, Long locationId);

    public void deleteLocation(Long userId, Long locationId);
}