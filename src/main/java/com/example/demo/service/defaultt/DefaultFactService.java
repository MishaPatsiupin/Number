package com.example.demo.service.defaultt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger logger = LoggerFactory.getLogger(DefaultFactService.class);

    private final FactRepository factRepository;
    private final NumberRepository numberRepository;
    private final FactCategoryRepository factCategoryRepository;
    private final CategoryRepository categoryRepository;


    @Override
    public ResponseEntity<String> deleteFact(String number) {
        try {
            long numberData = Long.parseLong(number);
            Fact factEntity = factRepository.findById(numberData).orElse(null);

            if (factEntity != null) {
                FactCategory factCategoryEntity = factCategoryRepository.getFactCategoryByFactEntity(factEntity);
                if (factCategoryEntity != null) {
                    factCategoryRepository.delete(factCategoryEntity);
                }

                Numbeer numberEntity = factEntity.getNumber();
                factRepository.deleteById(numberData);

                if (factRepository.findByNumber(numberEntity) == null) {
                    numberRepository.delete(numberEntity);
                    logger.info("Fact deleted with number: {}", number);
                    return ResponseEntity.ok().body("Fact deleted successfully.");
                }
            }

            logger.info("Fact not found with number: {}", number);
            return ResponseEntity.ok().body("Fact not found.");
        } catch (NumberFormatException e) {
            logger.error("Invalid id format: {}", number);
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
            logger.info("Created new Numbeer entity with number: {}", number);
        } else {
            logger.info("Found existing Numbeer entity with number: {}", number);
        }

        Fact factEntity = new Fact();
        factEntity.setNumber(existingNumber);
        factEntity.setDescription(description);
        factRepository.save(factEntity);
        logger.info("Created new Fact entity with description: {}", description);
        return factEntity;
    }

    @Override
    public Fact findFact(String description) {
        Fact factEntity = factRepository.findByDescription(description);

        if (factEntity != null) {
            logger.info("Fact found with description: {}", description);
        } else {
            logger.info("Fact not found with description: {}", description);
        }

        return factEntity;
    }

    @Override
    public Fact getFactByNumberId(long number) {
        Numbeer numberEntity = numberRepository.findByNumberData(number);
        Fact factEntity = factRepository.findByNumber(numberEntity);

        if (factEntity != null) {
            logger.info("Fact found with number: {}", number);
        } else {
            logger.info("Fact not found with number: {}", number);
        }

        return factEntity;
    }



}
