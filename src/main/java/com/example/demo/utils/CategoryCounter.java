package com.example.demo.utils;

import com.example.demo.entity.FactCategoryEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryCounter {

    private SimpleCache cache;

    public CategoryCounter() {
        cache = new SimpleCache();
    }

    public void incrementCategory(String category) {
        Integer count = (Integer) cache.get(category);
        if(count == null) {
            cache.put(category, 1);
        } else {
            cache.put(category, count + 1);
        }
    }

    public void addCategoryFacts(List<String> response, List<FactCategoryEntity> entities) {

        for(FactCategoryEntity entity : entities) {
            incrementCategory(entity.getCategory().getName());
        }

        if(cache.size() > 0) {

            response.add("<----------------------------------------------------------------->");
            response.add("There are also facts about this number in the following categories:");

            for(String cat : cache.keySet()) {
                Integer count = (Integer) cache.get(cat);
                response.add("- " + cat + ": " + count);
            }
        }
    }

}

