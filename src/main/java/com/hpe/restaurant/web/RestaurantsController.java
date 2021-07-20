package com.hpe.restaurant.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hpe.restaurant.domain.entity.Restaurant;
import com.hpe.restaurant.service.MenuService;
import com.hpe.restaurant.service.RestaurantService;
import com.hpe.restaurant.web.Helper.RestaurantHelper;
import com.hpe.restaurant.web.api.RestaurantsApi;
import com.hpe.restaurant.web.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RestaurantsController implements RestaurantsApi {

    private final RestaurantService restaurantService;

    private final ObjectMapper objectMapper;

    private final MenuService menuService;

    @Override
    public ResponseEntity<Void> register(@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody RestaurantDTO body) {
        try {
            restaurantService.register(objectMapper.convertValue(body, new TypeReference<Restaurant>() {
            }));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> publish(@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody PublishDTO body) {
        try {
            menuService.publish(RestaurantHelper.prepareMenu(body));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<ResultDTO>> search(@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody SearchDTO body) {
        List<Restaurant> restaurants=this.restaurantService.searchRestaurant(body);
        List<ResultDTO> resultDTOS=new ArrayList<>();
        restaurants.forEach(restaurant -> {
            ResultDTO resultDTO=new ResultDTO();
            resultDTO.restaurantName(restaurant.getRestaurantName());
            resultDTO.setGeoLocation(restaurant.getGeoLocation());
            resultDTO.adressess(RestaurantHelper.populateAddressDTO(restaurant));
            resultDTO.contactNumbers(RestaurantHelper.populateContactNumbersDTO(restaurant));
            resultDTO.operationHours(RestaurantHelper.populateOperationtimeDTO(restaurant));
            resultDTO.menu(RestaurantHelper.populateMenuDTO(restaurant));
            resultDTOS.add(resultDTO);
        });
        return ResponseEntity.ok(resultDTOS);
    }



}
