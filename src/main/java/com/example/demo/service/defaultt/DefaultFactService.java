package com.example.demo.service.defaultt;

import com.example.demo.entity.FactCategory;
import com.example.demo.entity.Fact;
import com.example.demo.entity.Numbeer;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.FactCategoryRepository;
import com.example.demo.repository.FactRepository;
import com.example.demo.repository.NumberRepository;
import com.example.demo.service.FactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultFactService implements FactService {
    private final FactRepository factRepository;
    private final NumberRepository numberRepository;
    private final FactCategoryRepository factCategoryRepository;
    private final CategoryRepository categoryRepository;


    @Override
    public ResponseEntity<String> deleteFact(String number) {
        try {
            long numberData = Long.parseLong(number);
            FactCategory factCategoryEntity = factCategoryRepository.findFactCategoryEntitiesById(numberData);
            if (factCategoryEntity != null) {
                factCategoryRepository.delete(factCategoryRepository.getFactCategoryByFactEntity(factRepository.findById(numberData).get()));

                Numbeer delNumber = factRepository.findById(numberData).get().getNumber();
                factRepository.deleteById(numberData);
                if (factRepository.findByNumber(delNumber) != null) {
                    return ResponseEntity.ok().body("Fact delete successfully.");
                }
                numberRepository.delete(delNumber);

                return ResponseEntity.ok().body("Fact delete successfully.");
            }

            return ResponseEntity.ok("Fact not found.");
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid id format");
        }
    }

    @Override
    public Fact createFact(long number, String description) {
        Numbeer existingNumber = numberRepository.findByNumberData(number);

        if (existingNumber == null) {
            existingNumber = new Numbeer();
            existingNumber.setNumberData(number);
            existingNumber = numberRepository.save(existingNumber); // Save the new Numbeer to generate an ID
        }

        Fact factEntity = new Fact();
        factEntity.setNumber(existingNumber);
        factEntity.setDescription(description);

        return factRepository.save(factEntity);
    }

    @Override
    public Fact findFact(String description) {
        return factRepository.findByDescription(description);
    }

    @Override
    public Fact getFactByNumberId(long number) {
        Numbeer numberEntity = numberRepository.findByNumberData(number);

        return factRepository.findByNumber(numberEntity);
    }



}
