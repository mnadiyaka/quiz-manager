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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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

    @NotBlank(message = "Enter city")
    @Size(min = 3, max = 30)
    @Column(name = "city")
    private String city;

    @NotBlank(message = "Enter street")
    @Size(min = 3, max = 30)
    @Column(name = "street")
    private String street;

    @NotBlank(message = "Enter number")
    @Size(min = 1, max = 12)
    @Column(name = "house_number")
    private String houseNumber;

    @NotBlank(message = "Enter code")
    @Size(min = 3, max = 10)
    @Column(name = "zip_code")
    private String zipCode;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "address")
    private List<Quiz> quizzesLoc;

    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "adminLocation")
    private List<User> adminUsers;
}