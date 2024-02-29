package org.example.numbeer.server;


import java.util.Objects;

public class UrlChecker {
    private UrlChecker(){}
    public static String checkUrl(String lat, String lng, String date, String formatted) throws WrongFormatException{
        if(Objects.equals(lat, "null") || Objects.equals(lng, "null")){
            throw new WrongFormatException("Wrong format! Variables \"lat\" and \"lng\" are obligatory!");
        }

        String url = "https://api.sunrise-sunset.org/json";
        String newUrl;
        final String latS = "?lat=";
        final String lngS = "&lng=";

        if(!Objects.equals(date, "null")&&!Objects.equals(formatted, "null")){
            newUrl = url + latS + lat + lngS + lng + "&date=" + date + "&formatted=" + formatted;
        }
        else if(!Objects.equals(date, "null")&&Objects.equals(formatted, "null")){
            newUrl = url + latS + lat + lngS + lng + "&date=" + date;
        }
        else if(Objects.equals(date, "null")&&!Objects.equals(formatted, "null")){
            newUrl = url + latS + lat + lngS + lng + "&formatted=" + formatted;
        }
        else{
            newUrl = url + latS + lat + lngS + lng;
        }

        return newUrl;
    }
}