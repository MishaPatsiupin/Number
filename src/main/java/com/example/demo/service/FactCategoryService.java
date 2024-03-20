package com.example.demo.service;

import com.example.demo.entity.FactCategoryEntity;

public interface FactCategoryService {
    FactCategoryEntity createFactCategory(long catId, long facId);
    void deleteFactCategory (long id);
    void updateFactCategory(long id, long catId, long facId);

    boolean ifCategory (long facId, long catId);
}
