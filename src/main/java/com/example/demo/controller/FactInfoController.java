package com.example.demo.controller;

import com.example.demo.entity.FactCategory;
import com.example.demo.repository.CategoryRepository;

import com.example.demo.repository.NumberRepository;
import com.example.demo.service.FactCategoryService;
import com.example.demo.service.FactService;
import com.example.demo.service.NumberService;
import com.example.demo.service.defaultt.DefaultNumberService;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Validated
@RestController
@AllArgsConstructor
public class FactInfoController {

  NumberService numberService;
  NumberRepository numberRepository;
  CategoryRepository categoryRepository;
  FactCategoryService factCategoryService;
  FactService factService;



  @GetMapping(value = "/info")
  @Validated
  public ResponseEntity<FactCategory> getInfoOne(
      @RequestParam(value = "number", defaultValue = "random") @Pattern(regexp = "\\d+|^(random)")
          String numberS,
      @RequestParam(value = "type", defaultValue = "trivia")
          @Pattern(regexp = "^(year|math|trivia)$")
          String type) {

    return new ResponseEntity<>(
        factCategoryService.getFactByFactAndCategory(numberS, type), HttpStatus.OK);
  }

  @GetMapping(value = "/info/all", produces = "application/json")
  @Validated
  public ResponseEntity<List<FactCategory>> getInfoAll(
      @RequestParam(value = "number", defaultValue = "random") @Pattern(regexp = "\\d+|^(random)")
          String numberS,
      @RequestParam(value = "type", defaultValue = "trivia")
          @Pattern(regexp = "^(year|math|trivia)$")
          String type) {

    return new ResponseEntity<>(factCategoryService.getFactsByFactAndCategory(numberS, type), HttpStatus.OK);
  }

  @GetMapping(value = "/info/all/number", produces = "application/json")
  @Validated
  public ResponseEntity<List<List<FactCategory>>> getInfoCat(
          @RequestParam(value = "number", defaultValue = "random") @Pattern(regexp = "\\d+|^(random)")
          String numberS) {

    List<List<FactCategory>> response = new ArrayList<>();
    for (int i = 0; i < 3; i++){
        response.add(factCategoryService.getFactsByFactAndCategory(numberS, DefaultNumberService.Type.values()[i].name().toLowerCase()));
    }

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}
