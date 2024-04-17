package com.example.demo.service;

import com.example.demo.entity.FactCategory;

import java.util.List;

/** The interface Fact category service. */
public interface FactCategoryService {
  /**
   * Create fact category fact category.
   *
   * @param catId the cat id
   * @param facId the fac id
   * @return the fact category
   */
  FactCategory createFactCategory(long catId, long facId);

  /**
   * Delete fact category.
   *
   * @param id the id
   */
  void deleteFactCategory(long id);

  /**
   * Update fact category.
   *
   * @param id the id
   * @param catId the cat id
   * @param facId the fac id
   * @param author the author
   */
  void updateFactCategory(long id, long catId, long facId, String author);

  /**
   * Gets facts by fact and category.
   *
   * @param numberS the number s
   * @param type the type
   * @return the facts by fact and category
   */
  public List<FactCategory> getFactsByFactAndCategory(String numberS, String type);

  /**
   * Gets fact by fact and category.
   *
   * @param numberS the number s
   * @param type the type
   * @return the fact by fact and category
   */
  public FactCategory getFactByFactAndCategory(String numberS, String type);

  /**
   * Update cache.
   *
   * @param key the key
   * @param newValue the new value
   */
  void updateCache(String key, List<FactCategory> newValue);

  /**
   * Delete cache.
   *
   * @param key the key
   */
  void deleteCache(String key);
}
