package com.example.demo.controller;


import com.example.demo.entity.NumberEntity;
import com.example.demo.service.NumberService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Validated
@RestController
@AllArgsConstructor
public class FactInfoController {
private final NumberService numberService;

    @GetMapping(value = "/info")
    public String getInfo(@RequestParam(value = "number", defaultValue = "random")
                          @Pattern(regexp = "\\d+|^(random)")
                          @NumberFormat(style = NumberFormat.Style.NUMBER) String number,
                          @RequestParam(value = "type", defaultValue = "trivia")
                          @Pattern(regexp = "^(year|math|trivia)$") String type) {

        return "FactInfoResponseGetter.gettingFinalResponse(number, type)";
    }

    @PostMapping(value = "/add")
    public ResponseEntity<String> addNumber(@RequestParam(value = "number")
                                            @Pattern(regexp = "\\d+")
                                            @NumberFormat(style = NumberFormat.Style.NUMBER) String number) {
        try {
            long numberData = Long.parseLong(number);
            NumberEntity createdNumber = numberService.createProduct(numberData);
            return ResponseEntity.ok("Number added successfully: " + createdNumber.getNumberData());
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid number format");
        }
    }

    @GetMapping(value = "/**")
    public ResponseEntity<String> defaultMethod() {
        return new ResponseEntity<>("Please specify a valid path. For example, http://localhost:8080/info?number=5&type=math", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(IOException.class)
    public ResponseEntity<Object> handleIOException() {
        return new ResponseEntity<>("STATUS: 500. Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolation(ConstraintViolationException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}