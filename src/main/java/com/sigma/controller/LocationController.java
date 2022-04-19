package com.sigma.controller;

import com.sigma.model.dto.LocationDto;
import com.sigma.model.entity.Location;
import com.sigma.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("user/{userId}/location")
@Slf4j
public class LocationController {

    private final LocationService locationService;

    @GetMapping("/all")
    public Set<LocationDto> getLocation(@PathVariable Long userId) {
        return locationService.getAllLocations();
    }

    @PostMapping("")
    public Location createQuiz(@RequestBody LocationDto locationDto, @PathVariable Long userId) {
        return locationService.createLocation(locationDto, userId);
    }

    @PatchMapping("/{locationId}")
    public String updateQuiz(@RequestBody LocationDto locationDto, @PathVariable("userId") Long userId, @PathVariable("locationId") Long locationId) {
        locationService.updateLocation(locationDto, userId, locationId);
        return "team updated";
    }

    @DeleteMapping("/{locationId}")
    public String deleteLocation(@PathVariable("userId") Long userId, @PathVariable("locationId") Long locationId) {
        locationService.deleteLocation(userId, locationId);
        return "deleted team";
    }
}