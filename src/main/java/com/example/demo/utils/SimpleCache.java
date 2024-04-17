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

/** The type Simple cache. */
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

  /**
   * Update cache.
   *
   * @param key the key
   * @param newValue the new value
   */
  public void updateCache(String key, List<FactCategory> newValue) {
    logger.info("Updating cache for key: {}", key);
    cache.remove(key);
    addToCache(key, newValue);
  }

  /**
   * Delete cache.
   *
   * @param key the key
   */
  public void deleteCache(String key) {
    if (cache.get(key) != null) {
      logger.info("Deleting cache for key: {}", key);
      cache.remove(key);
    }
  }

  /**
   * Add to cache.
   *
   * @param key the key
   * @param value the value
   */
  public void addToCache(String key, List<FactCategory> value) {
    logger.info("Adding to cache - Key: {}, Value: {}", key, value);
    cache.put(key, value);
  }

  /**
   * Gets from cache.
   *
   * @param key the key
   * @return the from cache
   */
  public List<FactCategory> getFromCache(String key) {
    logger.info("Getting from cache for key: {}", key);
    return cache.get(key);
  }

  /** Clear all cashe. */
  public void clearAllCashe() {
    logger.info("Clearing all cache entries");
    cache.clear();
  }
}
