package com.hpe.restaurant.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {

    @Column(name = "RESTAURANT_ID")
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private long id;
    private String restaurantName;
    private String geoLocation;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<Address> adressess;
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<ContactNumber> contactNumbers;
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<OperationHours> operationHours;
    @OneToOne(mappedBy = "restaurant")
    private Menu menu;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant that = (Restaurant) o;
        return id == that.id && restaurantName.equals(that.restaurantName) && geoLocation.equals(that.geoLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, restaurantName, geoLocation);
    }
}
