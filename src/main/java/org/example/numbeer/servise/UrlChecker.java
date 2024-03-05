package org.example.numbeer.servise;

import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UrlChecker {
    private UrlChecker() {
    }

    public static String createNewUrl(String number, String type) throws IOException {
        String baseUrl = "http://numbersapi.com/";

        List<String> allowedHosts = new ArrayList<>();
        allowedHosts.add(baseUrl + number + "/trivia?json");
        allowedHosts.add(baseUrl + number + "/math?json");
        allowedHosts.add(baseUrl + number + "/year?json");

        String newUrl = baseUrl + number + "/" + type + "?json";
        URL updatedUrl = new URL(newUrl);

        if (allowedHosts.contains(newUrl)) {
            HttpURLConnection conn = (HttpURLConnection) updatedUrl.openConnection();
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpStatus.OK.value()) {
                return newUrl;
            }
        }

        return "http://numbersapi.com/random/trivia?json";
    }
}