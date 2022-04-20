package com.sigma.model.dto;

import com.sigma.model.entity.Location;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Accessors(chain = true)
public class LocationDto {

    private Long id;

    private String locationName;

    @NotBlank(message = "Enter city")
    @Size(min = 3, max = 30)
    private String city;

    @NotBlank(message = "Enter street")
    @Size(min = 3, max = 30)
    private String street;

    @NotBlank(message = "Enter number")
    @Size(min = 1, max = 12)
    private String houseNumber;

    @NotBlank(message = "Enter code")
    @Size(min = 3, max = 10)
    private String zipCode;

    public static LocationDto fromLocation(Location location) {
        return new LocationDto()
                .setId(location.getId())
                .setLocationName(location.getLocationName())
                .setStreet(location.getStreet())
                .setCity(location.getCity())
                .setHouseNumber(location.getHouseNumber())
                .setZipCode(location.getZipCode());
    }

    public static Location toLocation(LocationDto locationDto) {
        return new Location()
                .setId(locationDto.id)
                .setLocationName(locationDto.locationName)
                .setStreet(locationDto.street)
                .setCity(locationDto.city)
                .setHouseNumber(locationDto.houseNumber)
                .setZipCode(locationDto.zipCode);
    }
}