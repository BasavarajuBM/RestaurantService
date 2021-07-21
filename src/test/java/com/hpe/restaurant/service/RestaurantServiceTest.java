package com.hpe.restaurant.service;

import com.hpe.restaurant.domain.dao.RatingRepository;
import com.hpe.restaurant.domain.dao.RestaurantRepository;
import com.hpe.restaurant.domain.entity.Address;
import com.hpe.restaurant.domain.entity.Rating;
import com.hpe.restaurant.domain.entity.Restaurant;
import com.hpe.restaurant.web.model.AddressDTO;
import com.hpe.restaurant.web.model.SearchDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantServiceTest {

    RestaurantRepository restaurantRepository= mock(RestaurantRepository.class);

    RatingRepository ratingRepository=mock(RatingRepository.class);

    RestaurantService restaurantService=new RestaurantService(restaurantRepository,ratingRepository);


    @Test
    void register() {
        Restaurant restaurant=getRestaurant();
        when(restaurantRepository.save(restaurant)).thenReturn(null);
        restaurantService.register(restaurant);
        verify(restaurantRepository,times(1)).save(restaurant);
    }



    @Test
    void searchRestaurant_by_Name() {
        when(restaurantRepository.findRestaurantsByRestaurantName("Res_1")).thenReturn(Collections.singletonList(getRestaurant()));
        List<Restaurant> restaurantList=restaurantService.searchRestaurant(new SearchDTO().restaurantName("Res_1"));
        Assertions.assertThat(restaurantList).size().isEqualTo(1);
    }

    @Test
    void searchRestaurant_by_GeoLocation() {
        when(restaurantRepository.findRestaurantsByAddressOrGeoLocation(12345678,null,null,null,"Punggol")).thenReturn(Collections.singletonList(getRestaurant()));
        List<Restaurant> restaurantList=restaurantService.searchRestaurant(new SearchDTO().geoLocation("Punggol").address(new AddressDTO().postalCode(12345678)));
        Assertions.assertThat(restaurantList).size().isEqualTo(1);
    }

    @Test
    void searchRestaurantByRating() {
        when(restaurantRepository.findRestaurantsByRating(3,4)).thenReturn(Collections.singletonList(getRestaurant()));
        List<Restaurant> restaurantList=  restaurantService.searchRestaurantByRating(3,4);
        Assertions.assertThat(restaurantList).size().isEqualTo(1);
    }

    @Test
    void rate() {
        Rating rating=new Rating();
        rating.setRestaurantRating(3);;
        rating.setMenuRating(4);
        rating.setRestaurant(getRestaurant());
        when(restaurantRepository.findRestaurants("Res_1","Punggol")).thenReturn(Collections.singletonList(getRestaurant()));
        when(ratingRepository.save(rating)).thenReturn(rating);
        restaurantService.rate(rating);
        verify(ratingRepository,times(1)).save(rating);
    }




    private Restaurant getRestaurant(){
        Restaurant restaurant=new Restaurant();
        restaurant.setRestaurantName("Res_1");
        restaurant.setGeoLocation("Punggol");
        restaurant.setAdressess(Collections.singletonList(new Address("Street_1","Area_1",12345678,"Singapore",restaurant)));
        return restaurant;
    }
}