package com.hpe.restaurant.service;

import com.hpe.restaurant.domain.dao.RatingRepository;
import com.hpe.restaurant.domain.dao.RestaurantRepository;
import com.hpe.restaurant.domain.entity.Address;
import com.hpe.restaurant.domain.entity.Rating;
import com.hpe.restaurant.domain.entity.Restaurant;
import com.hpe.restaurant.web.model.SearchDTO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    @Autowired
    private final RestaurantRepository restaurantRepository;

    @Autowired
    private final RatingRepository ratingRepository;

    public void register(Restaurant restaurant){
        setBidirectionalRelationForAddress(restaurant);
        setBidirectionalRelationForContactNumbers(restaurant);
        setBidirectionalRelationForOperationHours(restaurant);
        this.restaurantRepository.save(restaurant);
    }

    public List<Restaurant> searchRestaurant(SearchDTO searchDTO){
        Set<Restaurant> restaurants=new HashSet<>();
        if(StringUtils.isNotBlank(searchDTO.getRestaurantName())){
            List<Restaurant> resMatchingName=this.restaurantRepository.findRestaurantsByRestaurantName(searchDTO.getRestaurantName());
            restaurants.addAll(resMatchingName);
        }
        if(StringUtils.isNotBlank(searchDTO.getMenuItem())){
            List<Restaurant> resMatchingMenu=this.restaurantRepository.findRestaurantsByMenuItem(searchDTO.getMenuItem());
            restaurants.addAll(resMatchingMenu);
        }
        if(searchDTO.getAddress()!=null || StringUtils.isNotBlank(searchDTO.getGeoLocation())){
            List<Restaurant> resMatchingAdd=this.restaurantRepository.findRestaurantsByAddressOrGeoLocation(searchDTO.getAddress().getPostalCode(),searchDTO.getAddress().getStreetName(),searchDTO.getAddress().getAreaName(),searchDTO.getAddress().getCountry(),searchDTO.getGeoLocation());
            restaurants.addAll(resMatchingAdd);
        }
        return new ArrayList<>(restaurants);
    }


    public List<Restaurant> searchRestaurantByRating(Integer menuRating, Integer restaurantRating){
        Set<Restaurant> restaurants=new HashSet<>();
        if(menuRating!=null || restaurantRating !=null){
            List<Restaurant> resMatchingRating=this.restaurantRepository.findRestaurantsByRating(menuRating,restaurantRating);
            restaurants.addAll(resMatchingRating);
        }
        return new ArrayList<>(restaurants);
    }

    public void rate(Rating rating){
        List<Restaurant> restaurants=this.restaurantRepository.findRestaurants(rating.getRestaurant().getRestaurantName(),rating.getRestaurant().getGeoLocation());
        if(!CollectionUtils.isEmpty(restaurants)){
            rating.setRestaurant(restaurants.get(0));
            this.ratingRepository.save(rating);
        }
    }

    private void setBidirectionalRelationForOperationHours(Restaurant restaurant) {
        if(!CollectionUtils.isEmpty(restaurant.getOperationHours())){
            restaurant.getOperationHours().stream().forEach(operationHours -> {
                operationHours.setRestaurant(restaurant);
            });
        }
    }
    private void setBidirectionalRelationForContactNumbers(Restaurant restaurant) {
        if(!CollectionUtils.isEmpty(restaurant.getContactNumbers())){
            restaurant.getContactNumbers().stream().forEach(contactNumber -> {
                contactNumber.setRestaurant(restaurant);
            });
        }
    }
    private void setBidirectionalRelationForAddress(Restaurant restaurant) {
        if(!CollectionUtils.isEmpty(restaurant.getAdressess())){
            restaurant.getAdressess().forEach(address -> {
                address.setRestaurant(restaurant);
            });
        }
    }
}
