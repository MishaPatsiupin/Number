package com.example.demo.utils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.example.demo.entity.FactCategory;
import lombok.Data;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;


@Component
@Data
@EnableScheduling
public class SimpleCache {

  private static final int MAX_AMOUNT_OF_ELEMENTS = 3;

    private final Map<String, List<FactCategory>> cache =
            new LinkedHashMap<>() {
                @Override
                protected boolean removeEldestEntry(Map.Entry<String, List<FactCategory>> eldest) {
                    boolean remove = size() > MAX_AMOUNT_OF_ELEMENTS;
                    if (remove) {
                        super.removeEldestEntry(eldest);
                    }
                    return remove;
                }
            };

  public void updateCache(String key, List<FactCategory> newValue) {
    cache.remove(key);
    addToCache(key, newValue);
  }

  public void deleteCache (String key){
      if (!cache.get(key).isEmpty()){
          cache.remove(key);
      }
  }

  public void addToCache(String key, List<FactCategory> value) {
    cache.put(key, value);
  }

  public List<FactCategory> getFromCache(String key) {
    return cache.get(key);
  }

  public void clearAllCashe(){
      cache.clear();
  }
}
