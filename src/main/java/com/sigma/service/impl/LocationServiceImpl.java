package com.sigma.service.impl;

import com.sigma.model.dto.LocationDto;
import com.sigma.model.entity.Location;
import com.sigma.repository.LocationRepository;
import com.sigma.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    @Override
    public List<LocationDto> getAllLocations() {
        log.info("Getting list of location");
        return locationRepository.findAll().stream().map(location -> LocationDto.fromLocation(location)).toList();
    }

    @Override
    public Location findLocationById(Long locationId) {
        log.info("Searching for location with id {}", locationId);
        Location location = locationRepository.findById(locationId).orElseThrow(() -> new EntityNotFoundException());

        return location;
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
        Location oldLocation = findLocationById(locationId);

        log.info("Updating location {}", oldLocation);
        if (updatedLocation.getLocationName()!=null) {
            oldLocation.setLocationName(updatedLocation.getLocationName());
        }
        if (updatedLocation.getCity()!=null) {
            oldLocation.setCity(updatedLocation.getCity());
        }
        if (updatedLocation.getStreet()!=null) {
            oldLocation.setStreet(updatedLocation.getStreet());
        }
        if (updatedLocation.getHouseNumber()!=null) {
            oldLocation.setHouseNumber(updatedLocation.getHouseNumber());
        }
        if (updatedLocation.getZipCode()!=0) {
            oldLocation.setZipCode(updatedLocation.getZipCode());
        }
        locationRepository.save(oldLocation);
    }

    @Override
    @Transactional
    public void deleteLocation(Long locationId) {
        log.info("Deleting location {}", findLocationById(locationId));
        locationRepository.deleteById(locationId);
    }
}