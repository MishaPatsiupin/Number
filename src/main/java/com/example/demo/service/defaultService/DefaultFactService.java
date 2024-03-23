package com.example.demo.service.defaultService;

import com.example.demo.entity.FactCategoryEntity;
import com.example.demo.entity.FactEntity;
import com.example.demo.entity.NumberEntity;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.FactCategoryRepository;
import com.example.demo.repository.FactRepository;
import com.example.demo.repository.NumberRepository;
import com.example.demo.service.FactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
@RequiredArgsConstructor
public class DefaultFactService implements FactService {
    private final FactRepository factRepository;
    private final NumberRepository numberRepository;
    private final FactCategoryRepository factCategoryRepository;
    private final CategoryRepository categoryRepository;


    @Override
    public FactEntity createFact(long number, String description) {
        NumberEntity existingNumber = numberRepository.findByNumberData(number);

        if (existingNumber == null) {
            existingNumber = new NumberEntity();
            existingNumber.setNumberData(number);
            existingNumber = numberRepository.save(existingNumber); // Save the new NumberEntity to generate an ID
        }

        FactEntity factEntity = new FactEntity();
        factEntity.setNumber(existingNumber);
        factEntity.setDescription(description);

        return factRepository.save(factEntity);
    }

    @Override
    public FactEntity findFact(String description) {
        return (FactEntity) factRepository.findByDescription(description);
    }

    @Override
    public FactEntity getFactByNumberId(long number) {
        NumberEntity numberEntity = numberRepository.findByNumberData(number);

        return factRepository.findByNumber(numberEntity);
    }



}
