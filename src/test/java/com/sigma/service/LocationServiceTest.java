package com.sigma.service;

import com.sigma.model.dto.LocationDto;
import com.sigma.model.entity.Location;
import com.sigma.repository.LocationRepository;
import com.sigma.service.impl.LocationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LocationServiceTest {

    private LocationRepository locationRepository;

    private LocationService locationService;

    @BeforeEach
    void setUp() {
        locationRepository = mock(LocationRepository.class);
        locationService = new LocationServiceImpl(locationRepository);
    }

    @Test
    public void findLocationById_WithExistingId_ThenReturnLocation() {
        final Location expectedResult = new Location();
        when(locationRepository.findById(anyLong())).thenReturn(Optional.of(expectedResult));
        final Location result = locationService.findLocationById(anyLong());

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    public void findLocationById_WithNonExistingId_ThenThrowException() {
        when(locationRepository.findById(anyLong())).thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class, () -> locationService.findLocationById(anyLong()));
    }

    @Test
    @Transactional
    public void createLocationTest() {
        Location expected = new Location();

        when(locationRepository.save(expected)).thenReturn(expected);

        Location actual = locationService.createLocation(LocationDto.fromLocation(expected));
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Transactional
    public void updateLocationTest() {
        Location location = new Location();
        location.setId(1L);
        when(locationRepository.findById(anyLong())).thenReturn(Optional.of(location));

        Location oldLoc = locationService.findLocationById(anyLong());
        oldLoc.setLocationName("new");

        when(locationRepository.save(oldLoc)).thenReturn(oldLoc);

        Location actual = locationService.updateLocation(LocationDto.fromLocation(location), location.getId());
        Assertions.assertEquals(oldLoc, actual);
    }

    @Test
    @Transactional
    public void deleteLocation() {//TODO: correct
        Location expected = new Location();
        expected.setId(1L);
        when(locationRepository.findById(anyLong())).thenReturn(Optional.of(expected));

        locationService.deleteLocation(expected.getId());
        verify(locationRepository).delete(expected);
    }

    @Test
    public void getAllLocation() {
        List<Location> locations = new ArrayList<>();
        locations.add(new Location());
        locations.add(new Location());
        locations.add(new Location());
        when(locationRepository.findAll()).thenReturn(locations);

        List<LocationDto> actual = locationService.getAllLocations();
        Assertions.assertEquals(locations.stream().map(LocationDto::fromLocation).collect(Collectors.toList()), actual);
    }
}