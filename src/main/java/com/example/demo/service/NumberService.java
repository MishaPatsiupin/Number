package com.example.demo.service;

import com.example.demo.entity.NumberEntity;

public interface NumberService {
    NumberEntity findAllProducts(long numberData);
    NumberEntity createProduct(long numberData);

    NumberEntity findProduct(long numberData);

    void updateProduct(long id, long numberData);

    void deleteProduct(long id);
}
