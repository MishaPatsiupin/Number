package com.example.demo.controller;

import com.example.demo.entity.Category;
import com.example.demo.entity.FactCategory;
import com.example.demo.entity.Fact;
import com.example.demo.entity.Numbeer;
import com.example.demo.exception.ExceptionHandle;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.FactCategoryRepository;
import com.example.demo.repository.FactRepository;
import com.example.demo.repository.NumberRepository;
import com.example.demo.service.FactCategoryService;
import com.example.demo.service.FactService;
import com.example.demo.service.NumberService;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@AllArgsConstructor
public class FactController {
  private final NumberService numberService;
  private final NumberRepository numberRepository;
  private final FactService factService;
  private CategoryRepository categoryRepository;
  private final FactCategoryRepository factCategoryRepository;
  private final FactRepository factRepository;
  private final FactCategoryService factCategoryService;
  private static final Logger logger = LoggerFactory.getLogger(FactController.class);

  @PostMapping(value = "/fact/add")
  public ResponseEntity<String> addFact(
      @RequestParam(value = "number")
          @Digits(integer = Integer.MAX_VALUE, fraction = 0, message = "Number must be an integer")
          @NumberFormat(style = NumberFormat.Style.NUMBER)
          Long number,
      @RequestParam(value = "type", defaultValue = "trivia")
          @Pattern(regexp = "^(year|math|trivia)$")
          String type,
      @RequestParam(value = "fact", defaultValue = "it's a boring number")
          @Pattern(regexp = "[A-Za-z0-9\\s.,;!?\"'-]+")
          String newFact,
      @RequestParam(value = "author", defaultValue = "User")
          @Pattern(regexp = "[A-Za-z0-9\\s.,;!?\"'-]+")
          String author)
      throws IllegalAccessException {

    try {

      numberService.addNumber(number.longValue());
      Category existingCategory = categoryRepository.findCategoryByName(type);
      Fact createdFact = factService.createFact(number.longValue(), newFact);

      FactCategory factCategory = new FactCategory();
      factCategory.setCategory(existingCategory);
      factCategory.setFact(createdFact);
      factCategory.setAuthor(author);
      factCategoryRepository.save(factCategory);

      logger.info("Fact added successfully with ID: {}", createdFact.getId());
      return ResponseEntity.ok("Fact added successfully with ID: " + createdFact.getId());
    } catch (NumberFormatException e) {
      logger.error("Invalid number/fact format: {}", e.getMessage());
      throw new IllegalAccessException("Invalid number/fact format");
      // return ResponseEntity.badRequest().body("Invalid number/fact format");
    }
  }

  @DeleteMapping(value = "/fact/delete")
  public ResponseEntity<String> delFact(
      @RequestParam(value = "id")
          @Pattern(regexp = "\\d+")
          @NumberFormat(style = NumberFormat.Style.NUMBER)
          String number)
      throws IllegalAccessException {

    try {
      long numberData = Long.parseLong(number);
      FactCategory factCategoryEntity =
          factCategoryRepository.findFactCategoryEntitiesById(numberData);
      if (factCategoryEntity != null) {

        String key =
            factCategoryEntity.getFact().getNumber().getNumberData()
                + "_"
                + factCategoryEntity.getCategory().getName();
        factCategoryService.deleteCache(key);

        factCategoryRepository.delete(
            factCategoryRepository.getFactCategoryByFactEntity(
                factRepository.findById(numberData).get()));

        Numbeer delNumber = factRepository.findById(numberData).get().getNumber();
        factRepository.deleteById(numberData);

        long numId = factCategoryEntity.getFact().getNumber().getId();
        if (factRepository.countByNumberId(numId) > 0) {
          // Если список не пустой, значит есть соответствующий факт
          logger.info("Fact delete successfully.");
          return ResponseEntity.ok().body("Fact delete successfully.");
        } else {
          numberRepository.delete(delNumber);
          logger.info("Fact delete successfully.");
          return ResponseEntity.ok().body("Fact delete successfully.");
        }
      }

      logger.warn("Fact not found.");
      return ResponseEntity.ok("Fact not found.");
    } catch (NumberFormatException e) {
      logger.error("Invalid id format: {}", e.getMessage());
      throw new IllegalAccessException("Invalid id format");
      // return ResponseEntity.badRequest().body("Invalid id format");
    }
  }

  @PutMapping(value = "/fact/update")
  public ResponseEntity<String> updFact(
      @RequestParam(value = "id")
          @Digits(integer = Integer.MAX_VALUE, fraction = 0, message = "Number must be an integer")
          @NumberFormat(style = NumberFormat.Style.NUMBER)
          long factId,
      @RequestParam(value = "number", required = false)
          @Digits(integer = Integer.MAX_VALUE, fraction = 0, message = "Number must be an integer")
          @NumberFormat(style = NumberFormat.Style.NUMBER)
          Long number,
      @RequestParam(value = "type", required = false) @Pattern(regexp = "^(year|math|trivia)$")
          String type,
      @RequestParam(value = "fact", required = false) @Pattern(regexp = "[A-Za-z0-9\\s.,;!?\"'-]+")
          String newFact,
      @RequestParam(value = "author", required = false)
          @Pattern(regexp = "[A-Za-z0-9\\s.,;!?\"'-]+")
          String author)
      throws IllegalAccessException {

    try {
      FactCategory factCategoryEntity = factCategoryRepository.findFactCategoryEntitiesById(factId);

      if (factCategoryEntity == null) {
        return ResponseEntity.badRequest().body("Fact not found for the given ID.");
      }

      factCategoryService.deleteCache(
          factCategoryEntity.getFact().getNumber().getNumberData()
              + "_"
              + factCategoryEntity.getCategory().getName());


      boolean flagNumberChange = false;
      if (number != null
          && factCategoryEntity.getFact().getNumber().getNumberData() != number.longValue()) {
        factCategoryEntity.getFact().setNumber(numberService.createNumber(number.longValue()));
          flagNumberChange = true;
      }

        boolean flagTypeChange = false;
      if (type != null && !factCategoryEntity.getCategory().getName().equals(type)) {
        factCategoryEntity.setCategory(categoryRepository.findCategoryByName(type));
          flagTypeChange = true;
      }

      if (newFact != null && !factCategoryEntity.getFact().getDescription().equals(newFact)) {
        factCategoryEntity.getFact().setDescription(newFact);
      }

      if (author != null && (!author.equals(factCategoryEntity.getAuthor()))) {
        factCategoryEntity.setAuthor(author);
      }

      if (flagNumberChange | flagTypeChange){
          if (flagNumberChange | !flagTypeChange)
              factCategoryService.deleteCache(number.toString() + "_"
                      + factCategoryEntity.getCategory().getName());
          if (!flagNumberChange | flagTypeChange)
              factCategoryService.deleteCache(factCategoryEntity.getFact().getNumber().getNumberData()
                      + "_"
                      + type);
          if (flagNumberChange & flagTypeChange)
              factCategoryService.deleteCache(number.toString() + "_" + type);
      }

      factCategoryRepository.save(factCategoryEntity);

      logger.info("Update fact.");
      return ResponseEntity.ok("Update fact.");
    } catch (Exception e) {
      logger.error("An error occurred while updating the fact: {}", e.getMessage());
      throw new IllegalAccessException("An error occurred while updating the fact.");
      //      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
      //          .body("An error occurred while updating the fact.");
    }
  }
}
