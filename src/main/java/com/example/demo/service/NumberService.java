package com.example.demo.service;

import java.util.Optional;

public interface NumberService {
    Iterable<Number> findAllProducts(long numberData);
    Number createProduct(long numberData);

    Optional<Number> findProduct(long numberData);

    void updateProduct(long id, long numberData);

    void deleteProduct(long id);
}
