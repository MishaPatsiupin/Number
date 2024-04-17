package com.example.demo.repository;

import com.example.demo.entity.Fact;
import com.example.demo.entity.FactCategory;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FactCategoryRepository extends CrudRepository<FactCategory, Long> {
  @Query("SELECT fc FROM FactCategory fc WHERE fc.fact.id = :idFact")
  FactCategory findFactCategoryEntitiesById(long idFact);

  // (5453,4,3414,UNKNOWN)
  @Query("SELECT fc FROM FactCategory fc WHERE fc.fact.number.id = :factId")
  List<FactCategory> findFactCategoriesByFactId(@Param("factId") long factId);

  // 10159,4,4455,UNKNOWN все по число_id, любые категории
  @Query("SELECT fc FROM FactCategory fc WHERE fc.fact = :factEntity")
  FactCategory getFactCategoryByFactEntity(@Param("factEntity") Fact factEntity);

  // 10159,4,4455,UNKNOWN
  @Query("SELECT fc FROM FactCategory fc " +
          "JOIN fc.fact f " +  // Присоединяем сущность Fact к FactCategory
          "JOIN f.number n " +  // Присоединяем сущность Number к Fact
          "JOIN fc.category c " +  // Присоединяем сущность Category к FactCategory
          "WHERE n.numberData = :numberData " +  // Устанавливаем условие фильтрации по значению numberData
          "AND c.name = :categoryName")  // Устанавливаем условие фильтрации по имени категории
  List<FactCategory> findFactCategoriesByNumberDataAndCategoryName(
          @Param("numberData") long numberData, @Param("categoryName") String categoryName);


}
