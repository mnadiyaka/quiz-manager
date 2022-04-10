package com.sigma.repository;

import com.sigma.model.entity.LocationDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<LocationDto, Long> {

    public List<LocationDto> findLocationsByCity(String city);
}