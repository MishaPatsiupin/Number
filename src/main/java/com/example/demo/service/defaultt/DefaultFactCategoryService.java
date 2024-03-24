package com.example.demo.service.defaultt;

import com.example.demo.entity.CategoryEntity;
import com.example.demo.entity.FactCategoryEntity;
import com.example.demo.entity.FactEntity;
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

    private CategoryEntity getCategoryEntityById(long catId) {
        // Логика получения CategoryEntity по идентификатору
        Optional<CategoryEntity> categoryOptional = categoryRepository.findById(catId);
        return categoryOptional.orElse(null);
    }

    private FactEntity getFactEntityById(long facId) {
        // Логика получения FactEntity по идентификатору
        Optional<FactEntity> factOptional = factRepository.findById(facId);
        return factOptional.orElse(null);
    }

    private FactCategoryEntity getFactCategoryEntityById(long id) {
        // Логика получения FactCategoryEntity по идентификатору
        Optional<FactCategoryEntity> factCategoryOptional = factCategoryRepository.findById(id);
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
    public FactCategoryEntity createFactCategory(long catId, long facId) {
        FactCategoryEntity factCategoryEntity = new FactCategoryEntity();
        // Получение связанных сущностей CategoryEntity и FactEntity из их идентификаторов
        CategoryEntity categoryEntity = getCategoryEntityById(catId);
        FactEntity factEntity = getFactEntityById(facId);
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
        FactCategoryEntity factCategoryEntity = getFactCategoryEntityById(id);
        if (factCategoryEntity != null) {
            CategoryEntity categoryEntity = getCategoryEntityById(catId);
            FactEntity factEntity = getFactEntityById(facId);
            factCategoryEntity.setCategory(categoryEntity);
            factCategoryEntity.setFact(factEntity);
            factCategoryRepository.save(factCategoryEntity);
        }
    }


    public ResponseEntity<List<String>> getFactsByFactAndCategory(String numberS, String type) {
        List<String> responseS = new ArrayList<>();
        List<FactCategoryEntity> categories;
        long number = 0;

        try {
            if (numberS.equals("random")) {
                number = random.nextLong(1001) - 500;
            } else {
                number = Long.parseLong(numberS);
            }

            long numberId = numberRepository.findByNumberData(number).getId();

            List<FactCategoryEntity> allFactByNumber;
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