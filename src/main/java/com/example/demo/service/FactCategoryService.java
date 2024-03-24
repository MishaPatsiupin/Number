package com.example.demo.service;

import com.example.demo.entity.FactCategoryEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FactCategoryService {
    FactCategoryEntity createFactCategory(long catId, long facId);

    void deleteFactCategory(long id);

    void updateFactCategory(long id, long catId, long facId);

    ResponseEntity<List<String>> getFactsByFactAndCategory(String numberS, String type);
    ResponseEntity<String> getFactByFactAndCategory(String numberS, String type);

}
