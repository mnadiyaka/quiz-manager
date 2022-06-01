package com.sigma.service;

import com.sigma.model.dto.LocationDto;
import com.sigma.model.entity.Location;
import com.sigma.model.entity.Quiz;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface LocationService {

    /**
     * Finds all possible {@link Location}s for future or current {@link Quiz}
     *
     * @return All Locations
     */
    List<LocationDto> getAllLocations();

    /**
     * Finds {@link Location} by entered existing id
     *
     * @param locationId Possible existing location
     * @return Found Location
     * @throws EntityNotFoundException If entered id does not exist
     */
    Location findLocationById(Long locationId);

    /**
     * Creates new {@link Location} from entered data in {@link LocationDto}
     *
     * @param location Entered data
     * @return Created new Location
     */
    Location createLocation(LocationDto location);

    /**
     * Tries to update existing {@link Location} from entered data in {@link LocationDto}, if it doesn't exist - creates new one
     *
     * @param updatedLocation Updated data
     * @param locationId      Location's id
     * @return Updated Location
     */
    Location updateLocation(LocationDto updatedLocation, Long locationId);

    /**
     * Deletes {@link Location} by id
     *
     * @param locationId Location's id
     * @throws EntityNotFoundException If Location is not found
     */
    void deleteLocation(Long locationId);
}