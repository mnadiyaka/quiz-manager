package com.sigma.dto;

import com.sigma.model.Location;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LocationDto {

    private Long id;

    private String locationName;

    private String street;

    private String city;

    private String houseNumber;

    private int zipCode;

    public static LocationDto fromLocation(Location location) {
        return new LocationDto()
                .setId(location.getId())
                .setLocationName(location.getLocationName())
                .setStreet(location.getStreet())
                .setCity(location.getCity())
                .setHouseNumber(location.getHouseNumber())
                .setZipCode(location.getZipCode());
    }

    public Location toLocation() {
        return Location.builder()
                .id(id)
                .locationName(locationName)
                .street(street)
                .city(city)
                .houseNumber(houseNumber)
                .zipCode(zipCode)
                .build();
    }
}
