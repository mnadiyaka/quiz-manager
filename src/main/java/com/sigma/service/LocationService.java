package com.sigma.service;

import com.sigma.model.dto.LocationDto;
import com.sigma.model.entity.Location;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface LocationService {

    /**
     * Finds all possible Locations for future or current quiz
     *
     * @return All Locations
     */
    List<LocationDto> getAllLocations();

    /**
     * Finds location by entered existing id
     *
     * @param locationId Possible existing location
     * @return Found Location
     * @throws EntityNotFoundException If entered id does not exist
     */
    Location findLocationById(Long locationId);

    /**
     * Creates new location from entered Data
     *
     * @param location Entered data
     * @return Created new Location
     */
    Location createLocation(LocationDto location);

    /**
     * Tries to update existing Location, if it doesn't exist - creates new one
     *
     * @param updatedLocation Updated data
     * @param locationId      Location's id
     * @return Updated Location
     */
    Location updateLocation(LocationDto updatedLocation, Long locationId);

    /**
     * Deletes Location by id
     *
     * @param locationId Location's id
     * @throws EntityNotFoundException If Location is not found
     */
    void deleteLocation(Long locationId);
}