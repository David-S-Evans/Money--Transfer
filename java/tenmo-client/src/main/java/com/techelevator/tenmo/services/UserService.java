package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    private static final String API_BASE_URL = "http://localhost:8080/";
    private String AUTH_TOKEN;

    private AuthenticatedUser currentUser;
    private AuthenticationService authenticationService;
    private RestTemplate restTemplate = new RestTemplate();

    public UserService(){}

    public void setAuthToken(String token) {
        AUTH_TOKEN = token;
    }

    public User[] listUsers(){
            HttpHeaders headers = new HttpHeaders();
            User[] userArray = null;
            headers.setBearerAuth(AUTH_TOKEN);
            HttpEntity entity = new HttpEntity(headers);
            try {
                userArray = restTemplate.exchange(API_BASE_URL + "tenmo/users", HttpMethod.GET, entity, User[].class).getBody();
            } catch (RestClientResponseException ex) {
                // throw new HotelServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
            }
            return userArray;


    }

}
