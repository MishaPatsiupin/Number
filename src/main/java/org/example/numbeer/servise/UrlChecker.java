package org.example.numbeer.servise;


public class UrlChecker {
    private UrlChecker(){}
    //http://numbersapi.com/random/year?json
    public static String createNewUrl(String number, String type){
        String url = "http://numbersapi.com/";
        String newUrl;
            newUrl = url + number + "/" + type + "?json";
        return newUrl;
    }
}