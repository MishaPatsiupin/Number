package com.example.demo.service;

import com.example.demo.entity.FactEntity;

public interface FactService {
    FactEntity createFact(long number, String description);

    FactEntity findFact (String description);

    FactEntity getFactByNumberId(long number);

}
