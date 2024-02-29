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
    @RequestMapping("/info")
    public String getSunInfo(@RequestParam(value = "number", defaultValue = "random") String number,
                             @RequestParam(value = "type", defaultValue = "trivia") String type){
        String url;
        try {
            url = UrlChecker.checkUrl(number, type);
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