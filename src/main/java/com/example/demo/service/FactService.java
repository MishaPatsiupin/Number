package com.example.demo.service;

import com.example.demo.entity.FactEntity;
import org.springframework.http.ResponseEntity;

public interface FactService {
    public ResponseEntity<String> deleteFact(String number);
    FactEntity createFact(long number, String description);

    FactEntity findFact (String description);

    FactEntity getFactByNumberId(long number);

}
