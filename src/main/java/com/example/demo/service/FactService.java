package com.example.demo.service;

import com.example.demo.entity.Fact;
import org.springframework.http.ResponseEntity;

public interface FactService {
    public ResponseEntity<String> deleteFact(String number);
    Fact createFact(long number, String description);

    Fact findFact (String description);

    Fact getFactByNumberId(long number);

}
