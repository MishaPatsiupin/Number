package com.example.demo.service;

import com.example.demo.entity.FactEntity;

import java.util.List;

public interface FactService {
    FactEntity createFact(long number, String description);

    FactEntity findFact (String description);
}
