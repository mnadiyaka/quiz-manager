package com.sigma.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "location_id")
    private int locationId;

    @Column(name = "location_name")
    private String locationName;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private Role street;

    @Column(name = "house_number")
    private Role houseNumber;

    @Column(name = "zip_code")
    private Role zipCode;

    @OneToMany(mappedBy = "addressId")
    private List<Quiz> quizzes_loc;

    @ManyToMany(mappedBy = "adminLocation")
    private List<User> admin_users;

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Role getStreet() {
        return street;
    }

    public void setStreet(Role street) {
        this.street = street;
    }

    public Role getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(Role houseNumber) {
        this.houseNumber = houseNumber;
    }

    public Role getZipCode() {
        return zipCode;
    }

    public void setZipCode(Role zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return locationId == location.locationId && Objects.equals(locationName, location.locationName) && Objects.equals(city, location.city) && street == location.street && houseNumber == location.houseNumber && zipCode == location.zipCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationId, locationName, city, street, houseNumber, zipCode);
    }
}
