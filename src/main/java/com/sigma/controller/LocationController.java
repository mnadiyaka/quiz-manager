package com.sigma.controller;

import com.sigma.model.dto.LocationDto;
import com.sigma.model.entity.Location;
import com.sigma.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("location")
@Slf4j
@PreAuthorize("hasRole('ADMIN')")
public class LocationController {

    private final LocationService locationService;

    @GetMapping("/all")
    public List<LocationDto> getLocation() {
        return locationService.getAllLocations();
    }

    @PostMapping("")
    public Location createQuiz(@RequestBody LocationDto locationDto) {
        return locationService.createLocation(locationDto);
    }

    @PatchMapping("/{locationId}")
    public String updateQuiz(@RequestBody LocationDto locationDto, @PathVariable("locationId") Long locationId) {
        locationService.updateLocation(locationDto, locationId);
        return "team updated";
    }

    @DeleteMapping("/{locationId}")
    public String deleteLocation(@PathVariable("locationId") Long locationId) {
        locationService.deleteLocation(locationId);
        return "deleted team";
    }
}