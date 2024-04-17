package com.example.demo.service.defaultt;

import com.example.demo.entity.Category;
import com.example.demo.entity.Fact;
import com.example.demo.entity.FactCategory;
import com.example.demo.entity.Numbeer;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.FactCategoryRepository;
import com.example.demo.repository.FactRepository;
import com.example.demo.repository.NumberRepository;
import com.example.demo.service.FactCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.*;

@Service
public class DefaultFactCategoryService implements FactCategoryService {

  private final FactCategoryRepository factCategoryRepository;
  private final CategoryRepository categoryRepository;
  private final FactRepository factRepository;
  private final NumberRepository numberRepository;
  private final SecureRandom random = new SecureRandom();

  private Category getCategoryEntityById(long catId) {
    // Логика получения Category по идентификатору
    Optional<Category> categoryOptional = categoryRepository.findById(catId);
    return categoryOptional.orElse(null);
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
      NumberRepository numberRepository) {
    this.factCategoryRepository = factCategoryRepository;
    this.categoryRepository = categoryRepository;
    this.factRepository = factRepository;
    this.numberRepository = numberRepository;
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

    List<FactCategory> allFactByNumber;
    allFactByNumber = factCategoryRepository.findFactCategoriesByNumberDataAndCategoryName(number, type);

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
