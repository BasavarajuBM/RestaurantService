package com.hpe.restaurant.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@IdClass(AddressId.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    private String streetName;
    @Id
    private String areaName;
    @Id
    private Integer postalCode;
    @Id
    private String country;

    @ManyToOne
    @JoinColumn(name = "RESTAURANT_ID")
    @Id
    private Restaurant restaurant;
}
