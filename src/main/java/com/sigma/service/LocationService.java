package com.sigma.service;

import com.sigma.model.dto.LocationDto;
import com.sigma.model.entity.Location;

import java.util.List;

public interface LocationService {

    public List<LocationDto> getAllLocations();

    public Location findLocationById(Long locationId);

    public Location createLocation(LocationDto location, Long userId);

    public void updateLocation(LocationDto updatedLocation, Long userId, Long locationId);

    public void deleteLocation(Long userId, Long locationId);
}