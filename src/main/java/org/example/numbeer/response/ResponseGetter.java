package org.example.numbeer.response;

import org.example.numbeer.servise.JsonRemaster;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ResponseGetter {
    private ResponseGetter() {
    }

    public static String gettingFinalResponse(String url) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        String jsonResponse = response.getBody();
        return JsonRemaster.gettingJsonKeyValues(jsonResponse);
    }
}