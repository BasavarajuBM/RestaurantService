package com.hpe.restaurant.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressId implements Serializable {
    private String streetName;
    private String areaName;
    private Integer postalCode;
    private String country;
    private Restaurant restaurant;
}
