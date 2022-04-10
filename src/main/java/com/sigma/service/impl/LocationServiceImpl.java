package com.sigma.service.impl;

import com.sigma.model.entity.LocationDto;
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
    public List<com.sigma.model.dto.LocationDto> getAllLocations() {
        log.info("Getting list of location");
        return locationRepository.findAll().stream().map(location -> com.sigma.model.dto.LocationDto.fromLocation(location)).toList();
    }

    @Override
    public com.sigma.model.dto.LocationDto findLocationById(Long locationId) {
        log.info("Searching for location with id {}", locationId);
        com.sigma.model.dto.LocationDto locationDto = com.sigma.model.dto.LocationDto.fromLocation(locationRepository.findById(locationId).get());
        if (locationDto == null) {
            throw new EntityNotFoundException();
        }
        return locationDto;
    }

    @Override
    @Transactional
    public LocationDto createLocation(com.sigma.model.dto.LocationDto location) {
        log.info("Creating new location {}", location.toString());
        return locationRepository.save(com.sigma.model.dto.LocationDto.toLocation(location));
    }

    @Override
    @Transactional
    public void updateLocation(com.sigma.model.dto.LocationDto updatedLocation, Long locationId) {
        com.sigma.model.dto.LocationDto oldLocation = com.sigma.model.dto.LocationDto.fromLocation(locationRepository.findById(locationId).get());
        if (oldLocation == null){
            throw new EntityNotFoundException();
        }
        log.info("Updating location {}", oldLocation);
        oldLocation.setLocationName(updatedLocation.getLocationName());
        oldLocation.setCity(updatedLocation.getCity());
        oldLocation.setStreet(updatedLocation.getStreet());
        oldLocation.setHouseNumber(updatedLocation.getHouseNumber());
        oldLocation.setZipCode(updatedLocation.getZipCode());

        locationRepository.save(com.sigma.model.dto.LocationDto.toLocation(oldLocation));
    }

    @Override
    @Transactional
    public void deleteLocation(Long locationId) {
        if (!locationRepository.existsById(locationId)) {
            throw new EntityNotFoundException();
        }
        log.info("Deleting location {}", locationRepository.getById(locationId));
        locationRepository.deleteById(locationId);
    }
}