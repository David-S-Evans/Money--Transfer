package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import io.cucumber.java.bs.A;
import org.springframework.http.*;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TransferService {

    private static final String API_BASE_URL = "http://localhost:8080/";
    private String AUTH_TOKEN;

    private AuthenticatedUser currentUser;
    private AuthenticationService authenticationService;
    private RestTemplate restTemplate = new RestTemplate();

    public TransferService(){}

    public void setAuthToken(String token) {
        AUTH_TOKEN = token;
    }

    public Transfer[] listTransfersByUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AUTH_TOKEN);
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<Transfer[]> response = restTemplate.exchange(API_BASE_URL + "tenmo/transfers/byUser", HttpMethod.GET, entity, Transfer[].class);
        return response.getBody();
    }

    public void createTransfer(Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AUTH_TOKEN);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity(transfer, headers);

        try {
            restTemplate.exchange(API_BASE_URL + "tenmo/transfers", HttpMethod.POST, entity, Transfer.class).getBody();
        } catch (RestClientResponseException ex) {
            System.out.println("The amount to send can't be greater than your balance");
        }

    }
    public Transfer getTransferById( int transferId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AUTH_TOKEN);
        HttpEntity entity = new HttpEntity(transferId, headers);
        ResponseEntity<Transfer> response = restTemplate.exchange(API_BASE_URL + "tenmo/transfers/" + transferId, HttpMethod.GET, entity, Transfer.class);
        return response.getBody();
    }
}
