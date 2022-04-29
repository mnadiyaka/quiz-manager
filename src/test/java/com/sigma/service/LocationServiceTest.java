package com.sigma.service;

import com.sigma.model.entity.Location;
import com.sigma.repository.LocationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
public class LocationServiceTest {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private LocationService locationService;

    @Test
    @Transactional
    public void findLocationByIdTest() {
//        Location location = locationService.findLocationById(1L);
//
//        Assertions.assertEquals(location.getCity(), "IF");
    }

    @Test
    @Transactional
    public void createLocationTest() {
        Location location = new Location();
        location.setLocationName("Kyiv");
        location.setCity("Kyiv");
        location.setZipCode("12345");
        location.setHouseNumber("4");
        location.setStreet("qwerty");

//        locationService.createLocation(LocationDto.location);
//
//        Assertions.assertEquals(location, locationService.findLocationById(location.getId()));
    }

    @Test
    @Transactional
    public void updateLocationTest() {
//        Location location = locationService.findLocationById(1L);
//        location.setStreet("new street");
//
//        locationService.updateLocation(location, location.getId());
//        Assertions.assertEquals(location, locationService.findLocationById(1L));
    }

    @Test
    @Transactional
    public void deleteLocationTest() {
        int expected = locationService.getAllLocations().size();
        locationService.deleteLocation(1L);
        int actual = locationService.getAllLocations().size();
        Assertions.assertEquals(expected - 1, actual);
    }

    @Test
    @Transactional
    public void getAllLocation() {
        int expected = locationRepository.findAll().size();

        Location location = new Location();
        location.setLocationName("Kyiv");
        location.setCity("Kyiv");
        location.setZipCode("12345");
        location.setHouseNumber("4");
        location.setStreet("qwerty");

//        locationService.createLocation(location);
//
//        int actual = locationService.getAllLocations().size();
//
//        Assertions.assertEquals(expected + 1, actual);
    }
}