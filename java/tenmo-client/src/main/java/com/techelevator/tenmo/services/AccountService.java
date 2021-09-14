package com.techelevator.tenmo.services;

import com.techelevator.tenmo.App;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.User;
import org.springframework.http.*;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class AccountService {


    private static final String API_BASE_URL = "http://localhost:8080/";
    private String AUTH_TOKEN;

    private AuthenticatedUser currentUser;
    private AuthenticationService authenticationService;
    private RestTemplate restTemplate = new RestTemplate();

    public AccountService() {
    }

    public void setAuthToken(String token) {
        AUTH_TOKEN = token;
    }





    public String getMyBalance() {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AUTH_TOKEN);
        HttpEntity entity = new HttpEntity(headers);
        String balanceString = "";
        try {
            ResponseEntity<Account> account = restTemplate.exchange(API_BASE_URL + "tenmo/accounts/myAccount", HttpMethod.GET, entity, Account.class);
            Account retrieved = account.getBody();
            balanceString = retrieved.getBalance().toString();

        } catch (RestClientResponseException ex) {
            // throw new HotelServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
        }
        return balanceString;
    }

    public String getBalance(int id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AUTH_TOKEN);
        HttpEntity entity = new HttpEntity(id, headers);
        String balanceString = "";
        try {
            ResponseEntity<Account> account = restTemplate.exchange(API_BASE_URL + "tenmo/accounts", HttpMethod.GET, entity, Account.class);
            Account retrieved = account.getBody();
            balanceString = retrieved.getBalance().toString();

        } catch (RestClientResponseException ex) {
            // throw new HotelServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
        }
        return balanceString;
    }

    public int getAccountIdFromUser(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AUTH_TOKEN);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity(user, headers);
        Account account = new Account();
      //  try {
            account = restTemplate.exchange(API_BASE_URL + "tenmo/accounts/byUser", HttpMethod.POST, entity, Account.class).getBody();
       // } catch (RestClientResponseException ex) {
            // throw new HotelServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
      //      System.out.println("didn't work");
      //  }
        return account.getAccount_id();
    }

    public Account getMyAccount() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AUTH_TOKEN);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity(headers);
        Account account = new Account();
        //  try {
        account = restTemplate.exchange(API_BASE_URL + "tenmo/accounts/myAccount", HttpMethod.GET, entity, Account.class).getBody();
        // } catch (RestClientResponseException ex) {
        // throw new HotelServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
        //      System.out.println("didn't work");
        //  }
        return account;
    }

    public void setBalance(Account account) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AUTH_TOKEN);
        HttpEntity entity = new HttpEntity(account, headers);
        try {
            Account retrieved = restTemplate.exchange(API_BASE_URL + "tenmo/accounts", HttpMethod.PUT, entity, Account.class).getBody();

        } catch (RestClientResponseException ex) {
            // throw new HotelServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
        }
    }

}
