package com.example.demo.service.defaultt;

import com.example.demo.entity.Category;
import com.example.demo.entity.Fact;
import com.example.demo.entity.FactCategory;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.FactCategoryRepository;
import com.example.demo.repository.FactRepository;
import com.example.demo.repository.NumberRepository;
import com.example.demo.service.FactCategoryService;
import com.example.demo.service.NumberService;
import com.example.demo.utils.CategoryCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.*;

@Service
public class DefaultFactCategoryService implements FactCategoryService {

    private final FactCategoryRepository factCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final FactRepository factRepository;
    private final NumberRepository numberRepository;
    private final NumberService numberService;
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
    public DefaultFactCategoryService(FactCategoryRepository factCategoryRepository, CategoryRepository categoryRepository, FactRepository factRepository, NumberRepository numberRepository, NumberService numberService) {
        this.factCategoryRepository = factCategoryRepository;
        this.categoryRepository = categoryRepository;
        this.factRepository = factRepository;
        this.numberRepository = numberRepository;
        this.numberService = numberService;
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
    public void updateFactCategory(long id, long catId, long facId) {
        FactCategory factCategoryEntity = getFactCategoryEntityById(id);
        if (factCategoryEntity != null) {
            Category categoryEntity = getCategoryEntityById(catId);
            Fact factEntity = getFactEntityById(facId);
            factCategoryEntity.setCategory(categoryEntity);
            factCategoryEntity.setFact(factEntity);
            factCategoryRepository.save(factCategoryEntity);
        }
    }


    public ResponseEntity<List<String>> getFactsByFactAndCategory(String numberS, String type) {
        List<String> responseS = new ArrayList<>();
        List<FactCategory> categories;
        long number = 0;

        try {
            if (numberS.equals("random")) {
                number = random.nextLong(1001) - 500;
            } else {
                number = Long.parseLong(numberS);
            }

            long numberId = numberRepository.findByNumberData(number).getId();

            List<FactCategory> allFactByNumber;
            allFactByNumber = factCategoryRepository.findFactCategoriesByFactId(numberId);

            for (int i = 0; i < allFactByNumber.size(); i++) {
                if (allFactByNumber.get(i).getCategory().getId() == categoryRepository.findIdByName(type)) {
                    responseS.add("Fact id:" + allFactByNumber.get(i).getFact().getId() + ", " + number + " " + allFactByNumber.get(i).getFact().getDescription());
                }
            }
            if (responseS.isEmpty()) {
                responseS.add(numberService.emplyNumber(responseS, number, type));
            }

            categories = factCategoryRepository.findByNumberData(number);
            CategoryCounter counter = new CategoryCounter();
            counter.addCategoryFacts(responseS, categories);


            return ResponseEntity.ok(responseS);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(Collections.singletonList("Invalid number format."));
        } catch (Exception e) {
            if (responseS.isEmpty()) {
                responseS.add(numberService.emplyNumber(responseS, number, type));
            }

            categories = factCategoryRepository.findByNumberData(number);
            CategoryCounter counter = new CategoryCounter();
            counter.addCategoryFacts(responseS, categories);

            return ResponseEntity.ok().body(responseS);
        }
    }

    @Override
    public ResponseEntity<String> getFactByFactAndCategory(String numberS, String type) {
        ResponseEntity<List<String>> facts = this.getFactsByFactAndCategory(numberS, type);
        ResponseEntity<String> fact = null;
        if (facts.getBody().size() > 1) {
            int factRandom = random.nextInt(facts.getBody().size());
            fact = ResponseEntity.ok().body(facts.getBody().get(factRandom));
        } else {
            return ResponseEntity.ok().body(facts.getBody().get(0));
        }
        return fact;
    }


}