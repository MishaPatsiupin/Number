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
    private final FactCategoryService factCategoryService;
    private final FactRepository factRepository;


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
            return ResponseEntity.badRequest().body("Invalid number/fact format");
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

                NumberEntity delNumber = factRepository.findById(numberData).get().getNumber();
                factRepository.deleteById(numberData);

                long numId = factCategoryEntity.getFact().getNumber().getId();
                if (factRepository.countByNumberId(numId) > 0) {
                    // Если список не пустой, значит есть соответствующий факт
                    return ResponseEntity.ok().body("Fact delete successfully.");
                } else {
                    numberRepository.delete(delNumber);

                    return ResponseEntity.ok().body("Fact delete successfully.");
                }
            }

            return ResponseEntity.ok("Fact not found.");
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid id format");
        }
    }

    @PutMapping(value = "/fact/update")
    public ResponseEntity<String> updFact(@RequestParam(value = "id")
                                          @Pattern(regexp = "\\d+")
                                          @NumberFormat(style = NumberFormat.Style.NUMBER) long factId,
                                          @RequestParam(value = "number", required = false)
                                          // Установлено значение required = false
                                          @Pattern(regexp = "\\d+")
                                          @NumberFormat(style = NumberFormat.Style.NUMBER) Long number, // Использован класс обертка Long
                                          @RequestParam(value = "type", defaultValue = "nothing")
                                          // Удалено значение "nothing"
                                          @Pattern(regexp = "^(year|math|trivia)$") String type,
                                          @RequestParam(value = "fact", defaultValue = "it's a boring number")
                                          // Установлено значение defaultValue = null
                                          @Pattern(regexp = "[A-Za-z0-9\\s.,;!?\"'-]+") String newFact) {


        FactCategoryEntity factCategoryEntity = factCategoryRepository.findFactCategoryEntitiesById(factId);

        if (!type.equals("nothing") && !factCategoryEntity.getCategory().getName().equals(type)) {
                factCategoryEntity.setCategory(categoryRepository.findCategoryByName(type));
                factCategoryRepository.save(factCategoryEntity);
        }
        if (factCategoryEntity.getFact().getNumber().getNumberData() != number.longValue()) {
            factCategoryEntity.getFact().setNumber(numberService.createNumber(number.longValue()));
            factCategoryRepository.save(factCategoryEntity);
        }


        return ResponseEntity.ok("Update fact:" + factCategoryRepository.findFactCategoryEntitiesById(factId));
    }
}
