package com.hpe.restaurant.domain.dao;

import com.hpe.restaurant.domain.entity.Restaurant;
import com.hpe.restaurant.web.model.AddressDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query(value = "select a from Restaurant a where a.restaurantName=:restaurantName and a.geoLocation=:geoLocation")
    public List<Restaurant> findRestaurants(String restaurantName, String geoLocation);

    @Query(value = "select a from Restaurant a where a.restaurantName=:restaurantName")
    public List<Restaurant> findRestaurantsByRestaurantName(String restaurantName);

    @Query(value = "select a from Restaurant a where a.id in (select b.restaurant from Menu b where b.menuItem=:menuItem)")
    public List<Restaurant> findRestaurantsByMenuItem(String menuItem);

    @Query(value = "select a from Restaurant a where a.id in (select b.restaurant from Address b where b.streetName=:streetName and b.postalCode=:postalCode" +
            " and b.areaName=:areaName and b.country=:country" +
            ") or a.geoLocation=:geoLocation")
    public List<Restaurant> findRestaurantsByAddressOrGeoLocation(Integer postalCode,String streetName,String areaName,String country, String geoLocation);

    @Query(value = "select a from Restaurant a where a.id in (select b.restaurant from Rating b where b.menuRating=:menuRating or b.restaurantRating=:restaurantRating)")
    public List<Restaurant> findRestaurantsByRating(Integer menuRating, Integer restaurantRating);
}
