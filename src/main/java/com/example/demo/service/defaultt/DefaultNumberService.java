package com.example.demo.service.defaultt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.entity.Numbeer;
import com.example.demo.service.NumberService;
import lombok.RequiredArgsConstructor;
import com.example.demo.repository.NumberRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultNumberService implements NumberService {

    private final Logger logger = LoggerFactory.getLogger(DefaultNumberService.class);

    private final NumberRepository numberRepository;

    @Override
    public ResponseEntity<String> addNumber(long numberData) {
        try {
            Numbeer existingNumber = findNumber(numberData);
            if (existingNumber != null) {
                logger.info("Number already exists: {}", numberData);
                return ResponseEntity.badRequest().body("Number already exists");
            }

            Numbeer createdNumber = createNumber(numberData);
            logger.info("Number added successfully: {}", createdNumber.getNumberData());

            return ResponseEntity.ok("Number added successfully: " + createdNumber.getNumberData());
        } catch (NumberFormatException e) {

            logger.error("Invalid number format: {}", numberData);
            return ResponseEntity.badRequest().body("Invalid number format");
        }
    }

    @Override
    public ResponseEntity<String> delNumber(String number) {
        try {
            long numberData = Long.parseLong(number);
            Numbeer existingNumber = this.findNumber(numberData);
            if (existingNumber != null) {
                numberRepository.delete(this.findNumber(numberData));
                logger.info("Number deleted successfully: {}", numberData);
                return ResponseEntity.ok().body("Number delete successfully.");
            }

            logger.info("Number not found: {}", numberData);
            return ResponseEntity.ok("Number not found.");
        } catch (NumberFormatException e) {
            logger.error("Invalid number format: {}", number);
            return ResponseEntity.badRequest().body("Invalid number format");
        }
    }

    @Override
    public long findIdByNumber(long id) {
        return numberRepository.findByNumberData(id).getId();
    }

    @Override
    public Numbeer createNumber(long numberData) {
        Optional<Numbeer> existingNumber = Optional.ofNullable(numberRepository.findByNumberData(numberData));

        if (existingNumber.isPresent()) {
            return existingNumber.get();
        }

        Numbeer numberEntity = new Numbeer();
        numberEntity.setNumberData(numberData);
        logger.info("Created new Number entity with number: {}", numberData);
        return numberRepository.save(numberEntity);
    }

    @Override
    public Numbeer findNumber(long numberData) {

        return numberRepository.findByNumberData(numberData);
    }

    @Override
    public void updateNumber(long id, long numberData) {
        Optional<Numbeer> existingNumber = numberRepository.findById(id);
        existingNumber.ifPresent(numberEntity -> {
            numberEntity.setNumberData(numberData);
            numberRepository.save(numberEntity);
            logger.info("Updated Number entity with ID: {}", id);
        });
    }

    @Override
    public void deleteNumber(long id) {
        numberRepository.deleteById(id);
        logger.info("Deleted Number entity with ID: {}", id);
    }


    public enum Type {
        TRIVIA, MATH, YEAR
    }
    @Override
    public String emplyNumber(List<String> responseS, long number, String type) {
            DefaultNumberService.Type typeEnum = DefaultNumberService.Type.valueOf(type.toUpperCase());
        logger.warn("Number {}/type {} not fond ", number, type);
            switch (typeEnum) {
                case TRIVIA:
                    return number + " is an uninteresting number.";
                case MATH:
                    return number + " is a boring number.";
                case YEAR:
                    return number + " BC is the year that we do not know what happened.";
            }
            return "Xmm. Oy";
    }
}