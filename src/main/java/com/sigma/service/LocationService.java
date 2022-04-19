package com.sigma.service;

import com.sigma.model.dto.LocationDto;
import com.sigma.model.entity.Location;

import java.util.Set;

public interface LocationService {

    Set<LocationDto> getAllLocations();

    Location findLocationById(Long locationId);

    Location createLocation(LocationDto location, Long userId);

    void updateLocation(LocationDto updatedLocation, Long userId, Long locationId);

    void deleteLocation(Long userId, Long locationId);
}