package com.example.demo.controller;


import com.example.demo.entity.CategoryEntity;
import com.example.demo.entity.FactCategoryEntity;
import com.example.demo.entity.FactEntity;
import com.example.demo.entity.NumberEntity;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.FactCategoryRepository;
import com.example.demo.repository.FactRepository;
import com.example.demo.repository.NumberRepository;
import com.example.demo.service.FactCategoryService;
import com.example.demo.service.FactService;
import com.example.demo.service.NumberService;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@AllArgsConstructor
public class FactController {
    private final NumberService numberService;
    private final NumberRepository numberRepository;
    private final FactService factService;
    private CategoryRepository categoryRepository;
    private final FactCategoryRepository factCategoryRepository;
    private final FactCategoryService factCategoryService;
    private final FactRepository factRepository;

    @PostMapping(value = "/number/add")
    public ResponseEntity<String> addNumber(@RequestParam(value = "number")
                                            @Pattern(regexp = "\\d+")
                                            @NumberFormat(style = NumberFormat.Style.NUMBER) long number) {

            return numberService.addNumber(number);
    }

    @DeleteMapping(value = "/number/delete")
    public ResponseEntity<String> delNumber(@RequestParam(value = "number")
                                            @Pattern(regexp = "\\d+")
                                            @NumberFormat(style = NumberFormat.Style.NUMBER) String number) {
        try {
            long numberData = Long.parseLong(number);
            NumberEntity existingNumber = numberService.findNumber(numberData);
            if (existingNumber != null) {
                numberRepository.delete(numberService.findNumber(numberData));
                return ResponseEntity.ok().body("Number delete.");
            }

            return ResponseEntity.ok("Number delete successfully.");
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid number format");
        }
    }

    @PostMapping(value = "/fact/add")
    public ResponseEntity<String> addFact(@RequestParam(value = "number")
                                          @Pattern(regexp = "\\d+")
                                          @NumberFormat(style = NumberFormat.Style.NUMBER) long numberData,
                                          @RequestParam(value = "type", defaultValue = "trivia")
                                          @Pattern(regexp = "^(year|math|trivia)$") String type,
                                          @RequestParam(value = "fact", defaultValue = "it's a boring number")
                                          @Pattern(regexp = "[A-Za-z0-9\\s.,;!?\"'-]+") String newFact) {
        try {

            numberService.addNumber(numberData);
            CategoryEntity existingCategory = categoryRepository.findCategoryByName(type);
            FactEntity createdFact = factService.createFact(numberData, newFact);

            FactCategoryEntity factCategory = new FactCategoryEntity();
            factCategory.setCategory(existingCategory);
            factCategory.setFact(createdFact);
            factCategoryRepository.save(factCategory);

            return ResponseEntity.ok("Fact added successfully with ID: " + createdFact.getId());
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid number format");
        }
    }

    @DeleteMapping(value = "/fact/delete")
    public ResponseEntity<String> delFact(@RequestParam(value = "number")
                                            @Pattern(regexp = "\\d+")
                                            @NumberFormat(style = NumberFormat.Style.NUMBER) String number) {
        try {
            long numberData = Long.parseLong(number);
            FactCategoryEntity factCategoryEntity = factCategoryRepository.findFactCategoryEntitiesById(numberData);
            if (factCategoryEntity != null) {
                factCategoryRepository.delete(factCategoryRepository.getFactCategoryByFactEntity(factRepository.findById(numberData).get()));
                factRepository.deleteById(numberData);
                return ResponseEntity.ok().body("Fact delete successfully.");
            }

            return ResponseEntity.ok("Fact not found.");
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid id format");
        }
    }


}
