package org.example.numbeer.controller;

import org.example.numbeer.server.UrlChecker;
import org.example.numbeer.server.WrongFormatException;
import org.example.numbeer.response.ResponseGetter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {
    @RequestMapping(value = "/info", method = {RequestMethod.GET})
    public String getInfo(@RequestParam(value = "number", defaultValue = "random") String number,
                          @RequestParam(value = "type", defaultValue = "trivia") String type){

        String url;
        try {
            url = UrlChecker.formatingUrl(number, type);
        }
        catch (WrongFormatException w){
            return w.getExceptionMessage();
        }
        return ResponseGetter.gettingFinalResponse(url);
    }

    @RequestMapping(value = "/**", method = {RequestMethod.GET})
    public ResponseEntity<String> defaultMethod() {
        return new ResponseEntity<>("Please specify a valid path. For exemple, http://localhost:8080/info?number=5&type=math", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException() {
        return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}