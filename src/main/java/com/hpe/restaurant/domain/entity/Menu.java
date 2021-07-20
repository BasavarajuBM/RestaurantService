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
public class Menu {

    @Id
    @Column(name = "menu_id")
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private long id;

    private String menuItem;

    @OneToOne
    @JoinColumn(name = "RESTAURANT_ID")
    private Restaurant restaurant;
}
