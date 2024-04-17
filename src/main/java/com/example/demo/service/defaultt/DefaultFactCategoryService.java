package com.example.demo.service.defaultt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.entity.Category;
import com.example.demo.entity.Fact;
import com.example.demo.entity.FactCategory;
import com.example.demo.entity.Numbeer;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.FactCategoryRepository;
import com.example.demo.repository.FactRepository;
import com.example.demo.repository.NumberRepository;
import com.example.demo.service.FactCategoryService;
import com.example.demo.utils.SimpleCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.*;

@Service
public class DefaultFactCategoryService implements FactCategoryService {

  private final Logger logger = LoggerFactory.getLogger(DefaultFactCategoryService.class);
  private final FactCategoryRepository factCategoryRepository;
  private final CategoryRepository categoryRepository;
  private final FactRepository factRepository;
  private final NumberRepository numberRepository;
  private final SimpleCache simpleCache;
  private final SecureRandom random = new SecureRandom();

  private Category getCategoryEntityById(long catId) {
    // Логика получения Category по идентификатору
    Optional<Category> categoryOptional = categoryRepository.findById(catId);
    return categoryOptional.orElse(null);
  }

  public void updateCache(String key, List<FactCategory> newValue){
    simpleCache.updateCache(key,newValue);
    logger.info("Cache updated for key: {}", key);
  }
  public void deleteCache (String key){
    if (simpleCache.getFromCache(key) != null){
      simpleCache.deleteCache(key);
      logger.info("Cache deleted for key: {}", key);

    }
  }
  public void clearCaсhe(){
    simpleCache.clearAllCashe();
    logger.info("All caches cleared");
  }

  private Fact getFactEntityById(long facId) {
    // Логика получения Fact по идентификатору
    Optional<Fact> factOptional = factRepository.findById(facId);
    return factOptional.orElse(null);
  }

  private FactCategory getFactCategoryEntityById(long id) {
    // Логика получения FactCategory по идентификатору
    Optional<FactCategory> factCategoryOptional = factCategoryRepository.findById(id);
    return factCategoryOptional.orElse(null);
  }

  @Autowired
  public DefaultFactCategoryService(
      FactCategoryRepository factCategoryRepository,
      CategoryRepository categoryRepository,
      FactRepository factRepository,
      NumberRepository numberRepository,
      SimpleCache simpleCache) {
    this.factCategoryRepository = factCategoryRepository;
    this.categoryRepository = categoryRepository;
    this.factRepository = factRepository;
    this.numberRepository = numberRepository;
    this.simpleCache = simpleCache;
  }

  @Override
  public FactCategory createFactCategory(long catId, long facId) {
    FactCategory factCategoryEntity = new FactCategory();
    // Получение связанных сущностей Category и Fact из их идентификаторов
    Category categoryEntity = getCategoryEntityById(catId);
    Fact factEntity = getFactEntityById(facId);
    factCategoryEntity.setCategory(categoryEntity);
    factCategoryEntity.setFact(factEntity);
    return factCategoryRepository.save(factCategoryEntity);
  }

  @Override
  public void deleteFactCategory(long id) {
    factCategoryRepository.deleteById(id);
    logger.info("FactCategory deleted with id: {}", id);
  }

  @Override
  public void updateFactCategory(long id, long catId, long facId, String author) {
    FactCategory factCategoryEntity = getFactCategoryEntityById(id);
    if (factCategoryEntity != null) {
      Category categoryEntity = getCategoryEntityById(catId);
      Fact factEntity = getFactEntityById(facId);
      factCategoryEntity.setCategory(categoryEntity);
      factCategoryEntity.setFact(factEntity);
      factCategoryEntity.setAuthor(author);
      factCategoryRepository.save(factCategoryEntity);
      logger.info("FactCategory updated with id: {}", id);
    } else {
      logger.warn("FactCategory not found with id: {}", id);
    }
  }

  public List<FactCategory> getFactsByFactAndCategory(String numberS, String type) {
    long number = 0;

    if (numberS.equals("random")) {
      number = random.nextLong(1001) - 500;
    } else {
      number = Long.parseLong(numberS);
    }

    Numbeer numberData = numberRepository.findByNumberData(number);
    if (numberData == null) {
      return Collections.emptyList();
    }

    String key = number + "_" + type;
    List<FactCategory> allFactByNumber = simpleCache.getFromCache(key);

    if (allFactByNumber == null) {
      allFactByNumber = factCategoryRepository.findFactCategoriesByNumberDataAndCategoryName(number, type);
      simpleCache.addToCache(key, allFactByNumber);
      logger.info("Data loaded from repository and added to cache. Key: {}", key);
    } else {
      logger.info("Data loaded from cache. Key: {}", key);
}

    return allFactByNumber;
  }

  @Override
  public FactCategory getFactByFactAndCategory(String numberS, String type) {
    List<FactCategory> facts = this.getFactsByFactAndCategory(numberS, type);
    FactCategory fact = null;
    if (facts.isEmpty()) {
      return null;
    }

    if (facts.size() > 1) {
      int sizeFact = facts.size();
      int factRandom = random.nextInt(sizeFact);
      fact = facts.get(factRandom);
    } else {
      return facts.get(0);
    }
    return fact;
  }
}
