package com.sigma.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "locations")
@Data
@RequiredArgsConstructor
@Accessors(chain = true)
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "location_id")
    private Long id;

    @Column(name = "location_name")
    private String locationName;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name = "house_number")
    private String houseNumber;

    @Column(name = "zip_code")
    private int zipCode;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "address")
    private List<Quiz> quizzesLoc;

    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "adminLocation")
    private List<User> adminUsers;
}