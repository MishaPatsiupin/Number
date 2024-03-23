package com.example.demo.service.def;

import com.example.demo.entity.NumberEntity;
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
    private final NumberRepository numberRepository;

    @Override
    public ResponseEntity<String> addNumber(long numberData) {
        try {
            NumberEntity existingNumber = findNumber(numberData);
            if (existingNumber != null) {
                return ResponseEntity.badRequest().body("Number already exists");
            }

            NumberEntity createdNumber = createNumber(numberData);
            return ResponseEntity.ok("Number added successfully: " + createdNumber.getNumberData());
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid number format");
        }
    }

    @Override
    public long findIdByNumber(long id) {
        return numberRepository.findByNumberData(id).getId();
    }

    @Override
    public NumberEntity createNumber(long numberData) {
        Optional<NumberEntity> existingNumber = Optional.ofNullable(numberRepository.findByNumberData(numberData));

        if (existingNumber.isPresent()) {
            return existingNumber.get();
        }

        NumberEntity numberEntity = new NumberEntity();
        numberEntity.setNumberData(numberData);
        return numberRepository.save(numberEntity);
    }

    @Override
    public NumberEntity findNumber(long numberData) {

        return numberRepository.findByNumberData(numberData);
    }

    @Override
    public void updateNumber(long id, long numberData) {
        Optional<NumberEntity> existingNumber = numberRepository.findById(id);
        existingNumber.ifPresent(numberEntity -> {
            numberEntity.setNumberData(numberData);
            numberRepository.save(numberEntity);
        });
    }

    @Override
    public void deleteNumber(long id) {
        numberRepository.deleteById(id);
    }


    public enum Type {
        TRIVIA, MATH, YEAR
    }
    @Override
    public String emplyNumber(List<String> responseS, long number, String type) {
            DefaultNumberService.Type typeEnum = DefaultNumberService.Type.valueOf(type.toUpperCase());
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