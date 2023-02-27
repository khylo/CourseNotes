package com.khylo.reactivemongo.client;
public class GithubRestTemplate{
    public static void main(String[] args) {
        // Create a RestTemplate with a Basic Authentication header
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("username", "password"));

        // Set up the request headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Set up the request body
        JSONObject requestBody = new JSONObject();
        requestBody.put("key", "value");

        // Create the request entity with headers and body
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody.toString(), headers);

        // Make the PUT request with the request entity
        String url = "https://example.com/api/endpoint";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);

        // Print the response body
        System.out.println(response.getBody());
    }
}