package com.sigma.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
