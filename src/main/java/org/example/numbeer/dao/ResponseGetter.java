package org.example.numbeer.dao;

import org.example.numbeer.server.JsonRemaster;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ResponseGetter {
    private ResponseGetter(){}

    public static String gettingFinalResponse(String url){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        String jsonResponse = response.getBody();
        System.out.println(jsonResponse);
        return JsonRemaster.gettingJsonKeyValues(jsonResponse);
    }
}