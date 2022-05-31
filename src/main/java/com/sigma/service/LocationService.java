package com.sigma.service;

import com.sigma.model.dto.LocationDto;
import com.sigma.model.entity.Location;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface LocationService {

    /**
     * Finds all possible Locations for future or current quiz
     * @return All Locations
     */
    public List<LocationDto> getAllLocations();

    /**
     * Finds location by entered existing id
     * @param locationId possible existing location
     * @return found Location
     * @throws EntityNotFoundException if entered id is not correct
     */
    public Location findLocationById(Long locationId);

    /**
     * Creates new location from entered Data
     * @param location Entered data
     * @return Created new Location
     */
    public Location createLocation(LocationDto location);

    /**
     * Tries to update existing Location, if it doesn't exist - creates new one
     * @param updatedLocation Updated data
     * @param locationId Location's id
     * @return Updated Location
     */
    public Location updateLocation(LocationDto updatedLocation, Long locationId);

    /**
     * Deletes Location by id
     * @param locationId Location's id
     * @throws EntityNotFoundException if Location is not found
     */
    public void deleteLocation(Long locationId);
}