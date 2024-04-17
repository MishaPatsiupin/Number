package com.example.demo.service;

import com.example.demo.entity.FactCategory;

import java.util.List;

public interface FactCategoryService {
    FactCategory createFactCategory(long catId, long facId);

    void deleteFactCategory(long id);

    void updateFactCategory(long id, long catId, long facId, String author) ;

    public List<FactCategory> getFactsByFactAndCategory(String numberS, String type);
    public FactCategory getFactByFactAndCategory(String numberS, String type);
    void updateCache(String key, List<FactCategory> newValue);
    void deleteCache (String key);
    public void clearCaсhe();

}
