package org.example.numbeer.controller;

import org.example.numbeer.server.UrlChecker;
import org.example.numbeer.server.WrongFormatException;
import org.example.numbeer.dao.ResponseGetter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @RequestMapping("/sunInfo")
    public String getSunInfo(@RequestParam(value = "lat", defaultValue = "null") String lat,
                             @RequestParam(value = "lng", defaultValue = "null") String lng,
                             @RequestParam(value = "date", defaultValue = "null") String date,
                             @RequestParam(value = "formatted", defaultValue = "null") String formatted){
        String url;
        try {
            url = UrlChecker.checkUrl(lat, lng, date, formatted);
        }
        catch (WrongFormatException w){
            return w.getExceptionMessage();
        }
        return ResponseGetter.gettingFinalResponse(url);
    }

    @RequestMapping("/**")
    public ResponseEntity<String> defaultMethod() {
        return new ResponseEntity<>("Please specify a valid path", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException() {
        return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}