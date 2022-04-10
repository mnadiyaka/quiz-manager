package com.sigma.service;

import com.sigma.model.entity.LocationDto;

import java.util.List;

public interface LocationService {

    public List<com.sigma.model.dto.LocationDto> getAllLocations();

    public com.sigma.model.dto.LocationDto findLocationById(Long locationId);

    public LocationDto createLocation(com.sigma.model.dto.LocationDto location);

    public void updateLocation(com.sigma.model.dto.LocationDto updatedLocation, Long locationId);

    public void deleteLocation(Long locationId);
}