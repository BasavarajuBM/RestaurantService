package com.hpe.restaurant.service;

import com.hpe.restaurant.domain.dao.MenuRepository;
import com.hpe.restaurant.domain.dao.RestaurantRepository;
import com.hpe.restaurant.domain.entity.Menu;
import com.hpe.restaurant.domain.entity.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    public void publish(Menu menu){
        List<Restaurant> restaurants=this.restaurantRepository.findRestaurants(menu.getRestaurant().getRestaurantName(),menu.getRestaurant().getGeoLocation());
        if(!CollectionUtils.isEmpty(restaurants)){
            menu.setRestaurant(restaurants.get(0));
            this.menuRepository.save(menu);
        }
    }
}
