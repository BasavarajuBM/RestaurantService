package com.hpe.restaurant.web.Helper;

import com.hpe.restaurant.domain.entity.Menu;
import com.hpe.restaurant.domain.entity.Rating;
import com.hpe.restaurant.domain.entity.Restaurant;
import com.hpe.restaurant.web.model.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class RestaurantHelper {

    public static Menu prepareMenu(PublishDTO publishDTOMenu) {
        Menu menu = new Menu();
        menu.setMenuItem(publishDTOMenu.getMenu().getMenuItem());
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantName(publishDTOMenu.getRestaurant().getRestaurantName());
        restaurant.setGeoLocation(publishDTOMenu.getRestaurant().getGeoLocation());
        menu.setRestaurant(restaurant);
        return menu;
    }

    public static Rating prepareRating(RatingDTO ratingDTO) {
        Rating rating=new Rating();
        rating.setMenuRating(Integer.valueOf(ratingDTO.getMenuRating().toString()));
        rating.setRestaurantRating(Integer.valueOf(ratingDTO.getRestaurantRating().toString()));
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantName(ratingDTO.getRestaurant().getRestaurantName());
        restaurant.setGeoLocation(ratingDTO.getRestaurant().getGeoLocation());
        rating.setRestaurant(restaurant);
        return rating;
    }

    @NotNull
    public static List<AddressDTO> populateAddressDTO(Restaurant restaurant) {
        List<AddressDTO> addressDTOS = new ArrayList<>();
        if(!CollectionUtils.isEmpty(restaurant.getAdressess())){
            restaurant.getAdressess().forEach(address -> {
                AddressDTO addressDTO = new AddressDTO().areaName(address.getAreaName())
                        .streetName(address.getStreetName())
                        .country(address.getCountry())
                        .postalCode(address.getPostalCode());
                addressDTOS.add(addressDTO);

            });
        }

        return addressDTOS;
    }

    public static List<ContactNumbersDTO> populateContactNumbersDTO(Restaurant restaurant) {
        List<ContactNumbersDTO> contactNumbersDTOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(restaurant.getContactNumbers())) {
            restaurant.getContactNumbers().forEach(contactNumber -> {
                ContactNumbersDTO contactNumbersDTO = new ContactNumbersDTO()
                        .contactNumber(contactNumber.getContactNumber())
                        .countryCode(contactNumber.getCountryCode());
                contactNumbersDTOS.add(contactNumbersDTO);
            });
        }

        return contactNumbersDTOS;
    }

    public static List<OperationTime> populateOperationtimeDTO(Restaurant restaurant) {
        List<OperationTime> operationTimes = new ArrayList<>();
        if (!CollectionUtils.isEmpty(restaurant.getOperationHours())) {
            restaurant.getOperationHours().forEach(operationHours -> {
                OperationTime operationTime = new OperationTime()
                        .time(operationHours.getTime())
                        .day(OperationTime.DayEnum.valueOf(operationHours.getDay()));
                operationTimes.add(operationTime);
            });
        }
        return operationTimes;
    }

    public static PublishDTOMenu populateMenuDTO(Restaurant restaurant) {
        PublishDTOMenu publishDTOMenu = new PublishDTOMenu();
        if (restaurant.getMenu() != null) {
            publishDTOMenu.menuItem(restaurant.getMenu().getMenuItem());
        }
        return publishDTOMenu;
    }

    public static void populatesResultDTO(List<Restaurant> restaurants, List<ResultDTO> resultDTOS) {
        restaurants.forEach(restaurant -> {
            ResultDTO resultDTO = new ResultDTO();
            resultDTO.restaurantName(restaurant.getRestaurantName());
            resultDTO.setGeoLocation(restaurant.getGeoLocation());
            resultDTO.adressess(RestaurantHelper.populateAddressDTO(restaurant));
            resultDTO.contactNumbers(RestaurantHelper.populateContactNumbersDTO(restaurant));
            resultDTO.operationHours(RestaurantHelper.populateOperationtimeDTO(restaurant));
            resultDTO.menu(RestaurantHelper.populateMenuDTO(restaurant));
            resultDTOS.add(resultDTO);
        });
    }
}
