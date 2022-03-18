package com.sigma.repository;

import com.sigma.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    public List<Location> getAllLocations();

    public List<Location> getLocationsByCity(String city);
}
