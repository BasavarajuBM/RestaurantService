package com.hpe.restaurant.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hpe.restaurant.domain.entity.Restaurant;
import com.hpe.restaurant.service.MenuService;
import com.hpe.restaurant.service.RestaurantService;
import com.hpe.restaurant.web.Helper.RestaurantHelper;
import com.hpe.restaurant.web.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.Mockito.*;

@AutoConfigureMockMvc
@WebMvcTest(RestaurantsController.class)
class RestaurantsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RestaurantService restaurantService;

    @MockBean
    MenuService menuService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void Test_register_withoutIssues() throws Exception{
        doNothing().when(restaurantService).register(objectMapper
                .convertValue(getRestaurantDTOFull(), new TypeReference<Restaurant>() {
        }));
        mockMvc.perform(MockMvcRequestBuilders.post("/restaurants/register")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(getValueAsString(getRestaurantDTOFull())))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(restaurantService,times(1)).register(objectMapper
                .convertValue(getRestaurantDTOFull(), new TypeReference<Restaurant>() {
                }));
    }

    @Test
    void Test_register_missingparams() throws Exception{
        RestaurantDTO restaurantDTO=getRestaurantDTOPartial();
        Restaurant restaurant = objectMapper
                .convertValue(restaurantDTO, new TypeReference<Restaurant>() {
                });
        doThrow(RuntimeException.class).when(restaurantService).register(any());
        mockMvc.perform(MockMvcRequestBuilders.post("/restaurants/register")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(getValueAsString(restaurantDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }



    @Test
    void Test_publish_withoutIssues() throws Exception{

        doNothing().when(menuService).publish(RestaurantHelper.prepareMenu(getPublishDTOFull()));
        mockMvc.perform(MockMvcRequestBuilders.post("/restaurants/menu/publish")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(getPublishDTOFull())))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(menuService,times(1)).publish(RestaurantHelper.prepareMenu(getPublishDTOFull()));
    }


    @Test
    void Test_publish_missingparams() throws Exception{
        doThrow(RuntimeException.class).when(menuService).publish(any());
        mockMvc.perform(MockMvcRequestBuilders.post("/restaurants/menu/publish")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(getPublishDTOPartial())))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    private String getValueAsString(RestaurantDTO restaurantDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(restaurantDTO);
    }

    private RestaurantDTO getRestaurantDTOFull(){
        RestaurantDTO restaurantDTO=new RestaurantDTO().restaurantName("Restaurant_1")
                .geoLocation("Location_1")
                .adressess(Collections.singletonList(new AddressDTO()
                             .areaName("Area_1").streetName("Street_1").country("Country_1").postalCode(12345678)))
                .contactNumbers(Collections.singletonList(new ContactNumbersDTO().contactNumber("12345678").countryCode("+65")))
                .operationHours(Collections.singletonList(new OperationTime().day(OperationTime.DayEnum.MONDAY).time("12:00 PM to 10:00 PM")));
        return restaurantDTO;
    }

    private RestaurantDTO getRestaurantDTOPartial(){
        RestaurantDTO restaurantDTO=new RestaurantDTO().restaurantName("Restaurant_1")
                .geoLocation("Location_1")
                .adressess(Collections.singletonList(new AddressDTO()
                        .areaName("Area_1").streetName("Street_1").country("Country_1")));
        return restaurantDTO;
    }


    private PublishDTO getPublishDTOFull(){
        PublishDTO publishDTO=new PublishDTO()
                .menu(new PublishDTOMenu().menuItem("PaneerTikka"))
                .restaurant(new PublishDTORestaurant().restaurantName("Res_1")
                        .geoLocation("Punggol"));
        return publishDTO;
    }

    private PublishDTO getPublishDTOPartial(){
        PublishDTO publishDTO=new PublishDTO()
                .menu(new PublishDTOMenu().menuItem("PaneerTikka"));
        return publishDTO;
    }
}