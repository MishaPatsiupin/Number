package org.example.numbeer.server;

import com.google.gson.Gson;

public class JsonRemaster {
    private JsonRemaster() {
    }

    public static String gettingJsonKeyValues(String jsonResponse){
        Gson json = new Gson();
        SunriseSunset sunrise = json.fromJson(jsonResponse, SunriseSunset.class);
        return "Sunrise " + sunrise.results.sunrise + ", sunset " + sunrise.results.sunset;
    }
}