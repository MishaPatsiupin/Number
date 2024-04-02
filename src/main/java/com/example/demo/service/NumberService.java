package com.example.demo.service;

import com.example.demo.entity.Numbeer;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface NumberService {
    ResponseEntity<String> addNumber(long numberData);
    ResponseEntity<String> delNumber(String number);
    long findIdByNumber(long numberData);
    Numbeer createNumber(long numberData);

    Numbeer findNumber(long numberData);

    void updateNumber(long id, long numberData);

    void deleteNumber(long id);

    public String emplyNumber(List<String> responseS, long number, String type);
}
