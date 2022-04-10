package com.sigma.model.dto;

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

    public static LocationDto fromLocation(com.sigma.model.entity.LocationDto location) {
        return new LocationDto()
                .setId(location.getId())
                .setLocationName(location.getLocationName())
                .setStreet(location.getStreet())
                .setCity(location.getCity())
                .setHouseNumber(location.getHouseNumber())
                .setZipCode(location.getZipCode());
    }

    public static com.sigma.model.entity.LocationDto toLocation(LocationDto locationDto) {
        return new com.sigma.model.entity.LocationDto()
                .setId(locationDto.id)
                .setLocationName(locationDto.locationName)
                .setStreet(locationDto.street)
                .setCity(locationDto.city)
                .setHouseNumber(locationDto.houseNumber)
                .setZipCode(locationDto.zipCode);
    }
}