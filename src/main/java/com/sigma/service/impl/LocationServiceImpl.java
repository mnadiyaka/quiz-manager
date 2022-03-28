package com.sigma.service.impl;

import com.sigma.model.Location;
import com.sigma.repository.LocationRepository;
import com.sigma.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Override
    public List<Location> getAllLocations() {
        log.info("Getting list of location");
        return locationRepository.findAll();
    }

    @Override
    public Location findLocationById(Long locationId) {
        log.info("Searching for location with id {}", locationId);
        return locationRepository.findById(locationId).orElseThrow(() -> new EntityNotFoundException());
    }

    @Override
    public Location createLocation(Location location) {
        log.info("Creating new location {}", location.toString());
        return locationRepository.save(location);
    }

    @Override
    public void updateLocation(Location updatedLocation, Long locationId) {
        Location oldLocation = locationRepository.findById(locationId).orElseThrow(() -> new EntityNotFoundException());
        log.info("Updating location {}", oldLocation.toString());
        oldLocation.setLocationName(updatedLocation.getLocationName());
        oldLocation.setCity(updatedLocation.getCity());
        oldLocation.setStreet(updatedLocation.getStreet());
        oldLocation.setHouseNumber(updatedLocation.getHouseNumber());
        oldLocation.setZipCode(updatedLocation.getZipCode());

        locationRepository.save(oldLocation);
    }

    @Override
    public void deleteLocation(Long locationId) {
        if (!locationRepository.existsById(locationId)) {
            throw new EntityNotFoundException();
        }
        log.info("Deleting location {}", locationRepository.getById(locationId).toString());
        locationRepository.deleteById(locationId);
    }
}