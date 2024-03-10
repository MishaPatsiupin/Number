package org.example.numbeer.controller;

import jakarta.validation.constraints.Pattern;

import org.example.numbeer.dao.NumberDAO;
import org.example.numbeer.response.FactNumberResponseGetter;
import org.example.numbeer.servise.UrlChecker;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Validated
public class FactNumberController {

    @GetMapping(value = "/info")
    public String getInfo(@RequestParam(value = "number", defaultValue = "random")
                          @Pattern(regexp = "\\d+|^(random)")
                          @NumberFormat(style = NumberFormat.Style.NUMBER) String number,
                          @RequestParam(value = "type", defaultValue = "trivia")
                          @Pattern(regexp = "^(year|math|trivia)$") String type) {

        String url;
        try {
            url = UrlChecker.createNewUrl(number, type);
        } catch (IOException e) {
            return "Error occurred while creating the URL: " + e.getMessage();
        }

        return FactNumberResponseGetter.gettingFinalResponse(url);
    }

    @GetMapping(value = "/add/number")
    public ResponseEntity<String> newNumber(@RequestParam(value = "data", defaultValue = "1001")
                            @Pattern(regexp = "\\d+") String number) {
        NumberDAO numberDAO = new NumberDAO();
        numberDAO.createNumber(Long.parseLong(number));


        return new ResponseEntity<>("Add number ", HttpStatus.OK);
    }

    @GetMapping(value = "/**")
    public ResponseEntity<String> defaultMethod() {
        return new ResponseEntity<>("Please specify a valid path. For example, http://localhost:8081/info?number=5&type=math", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<Object> handleIOException() {
        return new ResponseEntity<>("STATUS: 500. Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}