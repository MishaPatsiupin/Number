package com.example.demo.controller;


import com.example.demo.entity.FactEntity;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.FactCategoryService;
import com.example.demo.service.FactService;
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
import java.util.ArrayList;
import java.util.List;

@Validated
@RestController
@AllArgsConstructor
public class FactInfoController {

    NumberService numberService;
    CategoryRepository categoryRepository;
    FactCategoryService factCategoryService;
    FactService factService;

    @GetMapping(value = "/info")
    public List<String> getInfo(@RequestParam(value = "number", defaultValue = "random")
                                @Pattern(regexp = "\\d+|^(random)")
                                @NumberFormat(style = NumberFormat.Style.NUMBER) long number,
                                @RequestParam(value = "type", defaultValue = "trivia")
                                @Pattern(regexp = "^(year|math|trivia)$") String type) {

        long idNum = numberService.findIdByNumber(number);
        long idCat = categoryRepository.findIdByName(type);


   //     List<Long> idsFact = factService.idsByNumberId(idNum);
        List<String> response = new ArrayList<>();

    //    for (Long idFact : idsFact) {
      //      if (factCategoryService.ifCategory(idFact, idCat)) {
        //        String factDescription = factService.factById(idFact);
          //      response.add(factDescription);
            //}
        //}

        return response;
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