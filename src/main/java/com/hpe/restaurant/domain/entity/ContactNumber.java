package com.hpe.restaurant.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
@IdClass(ContactNumberId.class)
public class ContactNumber {

    @Id
    private String countryCode;
    @Id
    private String contactNumber;

    @ManyToOne
    @JoinColumn(name = "RESTAURANT_ID")
    @Id
    private Restaurant restaurant;
}
