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
import java.util.Objects;
import java.util.Optional;

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
        return locationRepository.findById(locationId).orElseThrow(() -> new EntityNotFoundException("Location with id = " + locationId + " not found"));
    }

    @Override
    @Transactional
    public Location createLocation(LocationDto location, Long userId) {
        log.info("Creating new location {}", location.toString());
        return locationRepository.save(LocationDto.toLocation(location));
    }

    @Override
    @Transactional
    public void updateLocation(LocationDto updatedLocation, Long userId, Long locationId) {
        final LocationDto oldLocation = LocationDto.fromLocation(findLocationById(locationId));

        log.info("Updating location {}", oldLocation);
        Optional.ofNullable(oldLocation.getLocationName()).ifPresent(updatedLocation::setLocationName);
        Optional.ofNullable(oldLocation.getCity()).ifPresent(updatedLocation::setCity);
        Optional.ofNullable(oldLocation.getStreet()).ifPresent(updatedLocation::setStreet);
        Optional.ofNullable(oldLocation.getHouseNumber()).ifPresent(updatedLocation::setHouseNumber);
        Optional.ofNullable(oldLocation.getZipCode()).ifPresent(updatedLocation::setZipCode);

        locationRepository.save(LocationDto.toLocation(oldLocation));
    }

    @Override
    @Transactional
    public void deleteLocation(Long userId, Long locationId) {
        log.info("Deleting location {}", findLocationById(locationId));
        locationRepository.deleteById(locationId);
    }
}