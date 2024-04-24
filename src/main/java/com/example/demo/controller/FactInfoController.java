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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/** The type Fact info controller. */
@Validated
@RestController
@AllArgsConstructor
public class FactInfoController {

  /** The Number service. */
  NumberService numberService;

  /** The Number repository. */
  NumberRepository numberRepository;

  /** The Category repository. */
  CategoryRepository categoryRepository;

  /** The Fact category service. */
  FactCategoryService factCategoryService;

  /** The Fact service. */
  FactService factService;

  private static final Logger logger = LoggerFactory.getLogger(FactInfoController.class);

  /** The constant ERROR_MESSEGE_1. */
  public static final String ERROR_MESSEGE_1 = "Error accessing information: ";

  /**
   * Gets info one.
   *
   * @param numberS the number s
   * @param type the type
   * @return the info one
   * @throws IllegalAccessException the illegal access exception
   */
  @GetMapping(value = "/info")
  @Validated
  public ResponseEntity<FactCategory> getInfoOne(
      @RequestParam(value = "number", defaultValue = "random") @Pattern(regexp = "\\d+|^(random)")
          String numberS,
      @RequestParam(value = "type", defaultValue = "trivia")
          @Pattern(regexp = "^(year|math|trivia)$")
          String type)
      throws IllegalAccessException, NoResourceFoundException {

    try {
      logger.info("GET /info called.");
      FactCategory factCategory = factCategoryService.getFactByFactAndCategory(numberS, type);

      if (factCategory == null) {
        throw new NoResourceFoundException(HttpMethod.GET, "Resource not found");
      }

      return new ResponseEntity<>(factCategory, HttpStatus.OK);
    } catch (NoResourceFoundException e) {
      String errorMessage = ERROR_MESSEGE_1 + e.getMessage();
      logger.error(errorMessage);
      throw e;
    } catch (Exception e) {
      String errorMessage = ERROR_MESSEGE_1 + e.getMessage();
      logger.error(errorMessage);
      throw new IllegalAccessException(errorMessage);
    }
  }

  /**
   * Gets info all.
   *
   * @param numberS the number s
   * @param type the type
   * @return the info all
   * @throws IllegalAccessException the illegal access exception
   */
  @GetMapping(value = "/info/all")
  public ResponseEntity<List<FactCategory>> getInfoAll(
      @RequestParam(value = "number", defaultValue = "random") @Pattern(regexp = "\\d+|^(random)")
          String numberS,
      @RequestParam(value = "type", defaultValue = "trivia")
          @Pattern(regexp = "^(year|math|trivia)$")
          String type)
      throws IllegalAccessException, NoResourceFoundException {

    try {
      logger.info("GET /info/all called.");
      List<FactCategory> factCategories =
          factCategoryService.getFactsByFactAndCategory(numberS, type);

      if (factCategories.isEmpty()) {
        throw new NoResourceFoundException(HttpMethod.GET, "Resources not found");
      }

      return new ResponseEntity<>(factCategories, HttpStatus.OK);
    } catch (NoResourceFoundException e) {
      String errorMessage = ERROR_MESSEGE_1 + e.getMessage();
      logger.error(errorMessage);
      throw e;
    } catch (Exception e) {
      String errorMessage = ERROR_MESSEGE_1 + e.getMessage();
      logger.error(errorMessage);
      throw new IllegalAccessException(errorMessage);
    }
  }

  /**
   * Gets info cat.
   *
   * @param numberS the number s
   * @return the info cat
   * @throws IllegalAccessException the illegal access exception
   */
  @GetMapping(value = "/info/all/number", produces = "application/json")
  @Validated
  public ResponseEntity<List<List<FactCategory>>> getInfoAllNumber(
      @RequestParam(value = "number", defaultValue = "random") @Pattern(regexp = "\\d+|^(random)")
          String numberS)
      throws IllegalAccessException {

    try {
      logger.info("GET /info/all/number called.");

      List<List<FactCategory>> response = new ArrayList<>();
      for (int i = 0; i < 3; i++) {
        response.add(
            factCategoryService.getFactsByFactAndCategory(
                numberS, DefaultNumberService.Type.values()[i].name().toLowerCase()));
      }

      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      String errorMessage = ERROR_MESSEGE_1 + e.getMessage();
      logger.error(errorMessage);
      throw new IllegalAccessException(errorMessage);
    }
  }
}
