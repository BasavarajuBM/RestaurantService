package com.hpe.restaurant.service;

import com.hpe.restaurant.domain.dao.RestaurantRepository;
import com.hpe.restaurant.domain.entity.Address;
import com.hpe.restaurant.domain.entity.Restaurant;
import com.hpe.restaurant.web.model.SearchDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

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


    private void setBidirectionalRelationForOperationHours(Restaurant restaurant) {
        restaurant.getOperationHours().stream().forEach(operationHours -> {
            operationHours.setRestaurant(restaurant);
        });
    }
    private void setBidirectionalRelationForContactNumbers(Restaurant restaurant) {
        restaurant.getContactNumbers().stream().forEach(contactNumber -> {
            contactNumber.setRestaurant(restaurant);
        });
    }
    private void setBidirectionalRelationForAddress(Restaurant restaurant) {
        restaurant.getAdressess().forEach(address -> {
            address.setRestaurant(restaurant);
        });
    }
}
