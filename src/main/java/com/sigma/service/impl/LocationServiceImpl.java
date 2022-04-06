package com.sigma.service.impl;

import com.sigma.model.dto.LocationDto;
import com.sigma.model.entity.Location;
import com.sigma.repository.LocationRepository;
import com.sigma.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Override
    public List<LocationDto> getAllLocations() {
        log.info("Getting list of location");
        return locationRepository.findAll().stream().map(location -> LocationDto.fromLocation(location)).toList();
    }

    @Override
    public LocationDto findLocationById(Long locationId) {
        log.info("Searching for location with id {}", locationId);
        LocationDto locationDto = LocationDto.fromLocation(locationRepository.findById(locationId).get());
        if (locationDto != null) {
            return locationDto;
        }
        throw new EntityNotFoundException();
    }

    @Override
    @Transactional
    public Location createLocation(LocationDto location) {
        log.info("Creating new location {}", location.toString());
        return locationRepository.save(LocationDto.toLocation(location));
    }

    @Override
    @Transactional
    public void updateLocation(LocationDto updatedLocation, Long locationId) {
        LocationDto oldLocation = LocationDto.fromLocation(locationRepository.findById(locationId).get());
        if (oldLocation == null){
            throw new EntityNotFoundException();
        }
        log.info("Updating location {}", oldLocation.toString());
        oldLocation.setLocationName(updatedLocation.getLocationName());
        oldLocation.setCity(updatedLocation.getCity());
        oldLocation.setStreet(updatedLocation.getStreet());
        oldLocation.setHouseNumber(updatedLocation.getHouseNumber());
        oldLocation.setZipCode(updatedLocation.getZipCode());

        locationRepository.save(LocationDto.toLocation(oldLocation));
    }

    @Override
    @Transactional
    public void deleteLocation(Long locationId) {
        if (!locationRepository.existsById(locationId)) {
            throw new EntityNotFoundException();
        }
        log.info("Deleting location {}", locationRepository.getById(locationId).toString());
        locationRepository.deleteById(locationId);
    }
}