package com.hpe.restaurant.integration;

import com.hpe.restaurant.web.model.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RestaurantIntegrationTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void test_register(){
        HttpEntity<RestaurantDTO> restaurantDTOHttpEntity=new HttpEntity<>(getRestaurantDTOFull());
          ResponseEntity responseEntity= restTemplate.exchange("/restaurants/register", HttpMethod.POST, restaurantDTOHttpEntity, new ParameterizedTypeReference<Object>() {
        });
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void test_Search_by_Name(){
        HttpEntity<RestaurantDTO> restaurantDTOHttpEntity=new HttpEntity<>(getRestaurantDTOFull());
        ResponseEntity responseEntity= restTemplate.exchange("/restaurants/register", HttpMethod.POST, restaurantDTOHttpEntity, new ParameterizedTypeReference<Object>() {
        });
        HttpEntity<SearchDTO> searchDTOHttpEntity=new HttpEntity<>(getSearchDTO());
        ResponseEntity<List<ResultDTO>> listResponseEntity=  restTemplate.exchange("/restaurants/search", HttpMethod.POST, searchDTOHttpEntity, new ParameterizedTypeReference<List<ResultDTO>>() {
        });
        assertThat(listResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
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

    private SearchDTO getSearchDTO(){
        SearchDTO searchDTO=new SearchDTO().restaurantName("Restaurant_1");
        return searchDTO;
    }
}
