package com.example.demo.service;

import com.example.demo.entity.NumberEntity;
import org.springframework.http.ResponseEntity;

public interface NumberService {
    ResponseEntity<String> addNumber(String number);
    long findIdByNumber(long numberData);
    NumberEntity createNumber(long numberData);

    NumberEntity findNumber(long numberData);

    void updateNumber(long id, long numberData);

    void deleteNumber(long id);
}
