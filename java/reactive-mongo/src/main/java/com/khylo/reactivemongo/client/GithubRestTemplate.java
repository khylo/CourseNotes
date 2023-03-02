package com.khylo.reactivemongo.client;

import com.google.gson.Gson;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.http.*;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class GithubRestTemplate extends GithubBaseClient{
    public static void main(String[] args) {
        // Create a RestTemplate with a Basic Authentication header
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("username", "password"));

        // Set up the request headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Set up the request body
        Gson gson = new Gson();

        // Create the request entity with headers and body
        HttpEntity<String> requestEntity = new HttpEntity<>(gson.toJson(Map.of("key","value")), headers);

        // Make the PUT request with the request entity
        String url = "https://example.com/api/endpoint";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);

        // Print the response body
        System.out.println(response.getBody());
    }
}