package org.example.numbeer.controller;
import jakarta.validation.constraints.Pattern;

import org.example.numbeer.response.ResponseGetter;
import org.example.numbeer.servise.UrlChecker;
import org.example.numbeer.servise.WrongFormatException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;




@RestController
@Validated
public class FactNumberController {
    @Autowired
    private MethodValidationPostProcessor validator;

    @GetMapping(value = "/info")
    public String getInfo(  @RequestParam(value = "number", defaultValue = "random")
                                @Pattern(regexp="\\d*(\\/\\d+)?")
                                @NumberFormat(style = NumberFormat.Style.NUMBER) String number,
                            @RequestParam(value = "type", defaultValue = "trivia")
                                @Pattern(regexp = "^[a-z]+$") String type) {

        if (!number.matches("\\d*(\\/\\d+)?")) {
            return "Invalid type parameter";
        }

        if (!type.matches("\\w+")) {
            return "Invalid type parameter";
        }

        String url;
        try {
            url = UrlChecker.createNewUrl(number, type);
        } catch (WrongFormatException w) {
            return w.getExceptionMessage();
        }
        return ResponseGetter.gettingFinalResponse(url);
    }


    @GetMapping(value = "/**")
    public ResponseEntity<String> defaultMethod() {
        return new ResponseEntity<>("Please specify a valid path. For exemple, http://localhost:8080/info?number=5&type=math", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException() {
        return new ResponseEntity<>("STATUS: 500. Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}