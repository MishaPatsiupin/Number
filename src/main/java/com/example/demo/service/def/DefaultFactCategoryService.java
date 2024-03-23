package com.example.demo.service.def;

import com.example.demo.entity.CategoryEntity;
import com.example.demo.entity.FactCategoryEntity;
import com.example.demo.entity.FactEntity;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.FactCategoryRepository;
import com.example.demo.repository.FactRepository;
import com.example.demo.repository.NumberRepository;
import com.example.demo.service.FactCategoryService;
import com.example.demo.service.NumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DefaultFactCategoryService implements FactCategoryService {

    private final FactCategoryRepository factCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final FactRepository factRepository;
    private final NumberRepository numberRepository;
    private final NumberService numberService;

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



    public ResponseEntity<List<String>> getFactCategoryByFactAndCategory(String numberS, String type) {
        List<String> responseS = new ArrayList<>();
        long number = 0;

        try {
            if (numberS.equals("random")) {
                Random random = new Random();
                number = random.nextInt(1001) - 500;
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

            return ResponseEntity.ok(responseS);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(Collections.singletonList("Invalid number format."));
        } catch (Exception e) {
            if (responseS.isEmpty()) {
                responseS.add(numberService.emplyNumber(responseS, number, type));
            }
            return ResponseEntity.ok().body(responseS);
        }
    }


}