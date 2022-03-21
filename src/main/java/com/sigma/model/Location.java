package com.sigma.model;

import lombok.AllArgsConstructor;
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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
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

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "addressId")
    private List<Quiz> quizzes_loc;

    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "adminLocation")
    private List<User> admin_users;
}
