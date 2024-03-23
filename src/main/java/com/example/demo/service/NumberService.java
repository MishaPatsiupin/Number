package com.example.demo.service;

import com.example.demo.entity.NumberEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface NumberService {
    ResponseEntity<String> addNumber(long numberData);
    long findIdByNumber(long numberData);
    NumberEntity createNumber(long numberData);

    NumberEntity findNumber(long numberData);

    void updateNumber(long id, long numberData);

    void deleteNumber(long id);

    public String emplyNumber(List<String> responseS, long number, String type);
}
