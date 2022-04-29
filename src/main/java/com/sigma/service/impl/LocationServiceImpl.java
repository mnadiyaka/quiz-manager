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
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    @Override
    public List<LocationDto> getAllLocations() {
        log.info("Getting list of location");
        return locationRepository.findAll().stream().map(LocationDto::fromLocation).toList();
    }

    @Override
    public Location findLocationById(final Long locationId) {
        log.info("Searching for location with id {}", locationId);
        return locationRepository.findById(locationId).orElseThrow(() -> new EntityNotFoundException());
    }

    @Override
    @Transactional
    public Location createLocation(final LocationDto location) {
        log.info("Creating new location {}", location.toString());
        return locationRepository.save(LocationDto.toLocation(location));
    }

    @Override
    @Transactional
    public void updateLocation(final LocationDto updatedLocation, final Long locationId) {
        final Location oldLocation = findLocationById(locationId);

        log.info("Updating location {}", oldLocation);

        Optional.ofNullable(updatedLocation.getLocationName()).ifPresent(oldLocation::setLocationName);
        Optional.ofNullable(updatedLocation.getCity()).ifPresent(oldLocation::setCity);
        Optional.ofNullable(updatedLocation.getStreet()).ifPresent(oldLocation::setStreet);
        Optional.ofNullable(updatedLocation.getHouseNumber()).ifPresent(oldLocation::setHouseNumber);
        Optional.ofNullable(updatedLocation.getZipCode()).ifPresent(oldLocation::setZipCode);

        locationRepository.save(oldLocation);
    }

    @Override
    @Transactional
    public void deleteLocation(final Long locationId) {
        log.info("Deleting location {}", findLocationById(locationId));
        locationRepository.deleteById(locationId);
    }
}