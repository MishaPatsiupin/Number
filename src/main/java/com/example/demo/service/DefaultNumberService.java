package com.example.demo.service;

import com.example.demo.entity.NumberEntity;
import lombok.RequiredArgsConstructor;
import com.example.demo.repository.NumberRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultNumberService implements NumberService {
    private final NumberRepository numberRepository;

    @Override
    public NumberEntity findAllProducts(long numberData) {
        return numberRepository.findByNumberData(numberData);
    }

    @Override
    public NumberEntity createProduct(long numberData) {
        Optional<NumberEntity> existingNumber = Optional.ofNullable(numberRepository.findByNumberData(numberData));

        if (existingNumber.isPresent()) {
            return existingNumber.get();
        }

        NumberEntity numberEntity = new NumberEntity();
        numberEntity.setNumberData(numberData);
        return numberRepository.save(numberEntity);
    }

    @Override
    public NumberEntity findProduct(long numberData) {
        return numberRepository.findByNumberData(numberData);
    }

    @Override
    public void updateProduct(long id, long numberData) {
        Optional<NumberEntity> existingNumber = numberRepository.findById(id);
        existingNumber.ifPresent(numberEntity -> {
            numberEntity.setNumberData(numberData);
            numberRepository.save(numberEntity);
        });
    }

    @Override
    public void deleteProduct(long id) {
        numberRepository.deleteById(id);
    }
}