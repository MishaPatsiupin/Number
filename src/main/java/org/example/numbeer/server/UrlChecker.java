package org.example.numbeer.server;


import java.util.Objects;

public class UrlChecker {
    private UrlChecker(){}
    //http://numbersapi.com/random/year?json
    public static String createNewUrl(String number, String type) throws WrongFormatException{
        if(Objects.equals(number, "null") || Objects.equals(type, "null")){
            throw new WrongFormatException("Wrong format! Variables \"number\" and \"type\" are obligatory!");
        }

        String url = "http://numbersapi.com/";
        String newUrl;

            newUrl = url + number + "/" + type + "?json";
        return newUrl;
    }
}