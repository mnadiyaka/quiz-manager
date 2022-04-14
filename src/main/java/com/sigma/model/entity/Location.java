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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @NotNull
    @Column(name = "city")
    private String city;

    @NotNull
    @Column(name = "street")
    private String street;

    @NotNull
    @Column(name = "house_number")
    private String houseNumber;

    @NotNull
    @Min(value = 10000)
    @Max(value = 99999)
    @Column(name = "zip_code")
    private int zipCode;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "address")
    private List<Quiz> quizzesLoc;

    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "adminLocation")
    private List<User> adminUsers;
}