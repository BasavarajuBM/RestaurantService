package com.hpe.restaurant.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(OperationHoursId.class)
public class OperationHours {
    @Id
    private String day;
    @Id
    private String time;

    @ManyToOne
    @JoinColumn(name = "RESTAURANT_ID")
    @Id
    private Restaurant restaurant;
}
