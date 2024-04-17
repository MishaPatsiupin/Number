package com.example.demo.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger logger = LoggerFactory.getLogger(SimpleCache.class);

    private final Map<String, List<FactCategory>> cache =
            new LinkedHashMap<>() {
                @Override
                protected boolean removeEldestEntry(Map.Entry<String, List<FactCategory>> eldest) {
                    boolean remove = size() > MAX_AMOUNT_OF_ELEMENTS;
                    if (remove) {
                        logger.info("Removing eldest entry from cache: {}", eldest.getKey());
                        super.removeEldestEntry(eldest);
                    }
                    return remove;
                }
            };

  public void updateCache(String key, List<FactCategory> newValue) {
      logger.info("Updating cache for key: {}", key);
    cache.remove(key);
    addToCache(key, newValue);
  }

  public void deleteCache (String key){
      if (cache.get(key) != null){
          logger.info("Deleting cache for key: {}", key);
          cache.remove(key);
      }
  }

  public void addToCache(String key, List<FactCategory> value) {
      logger.info("Adding to cache - Key: {}, Value: {}", key, value);
    cache.put(key, value);
  }

  public List<FactCategory> getFromCache(String key) {
      logger.info("Getting from cache for key: {}", key);
    return cache.get(key);
  }

  public void clearAllCashe(){
      logger.info("Clearing all cache entries");
      cache.clear();
  }
}
