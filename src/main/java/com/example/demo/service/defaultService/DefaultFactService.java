package com.example.demo.service.defaultService;

import com.example.demo.entity.FactEntity;
import com.example.demo.entity.NumberEntity;
import com.example.demo.repository.FactCategoryRepository;
import com.example.demo.repository.FactRepository;
import com.example.demo.repository.NumberRepository;
import com.example.demo.service.FactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultFactService implements FactService {
    private final FactRepository factRepository;
    private final NumberRepository numberRepository;
    private final FactCategoryRepository factCategoryRepository;



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

}
