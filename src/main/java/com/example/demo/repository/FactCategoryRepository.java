package com.example.demo.repository;

import com.example.demo.entity.Fact;
import com.example.demo.entity.FactCategory;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/** The interface Fact category repository. */
public interface FactCategoryRepository extends CrudRepository<FactCategory, Long> {
  /**
   * Find fact category entities by id fact category.
   *
   * @param idFact the id fact
   * @return the fact category
   */
  @Query("SELECT fc FROM FactCategory fc WHERE fc.fact.id = :idFact")
  FactCategory findFactCategoryEntitiesById(long idFact);

  /**
   * Find fact categories by fact id list.
   *
   * @param factId the fact id
   * @return the list
   */
  // (5453,4,3414,UNKNOWN)
  @Query("SELECT fc FROM FactCategory fc WHERE fc.fact.number.id = :factId")
  List<FactCategory> findFactCategoriesByFactId(@Param("factId") long factId);

  /**
   * Gets fact category by fact entity.
   *
   * @param factEntity the fact entity
   * @return the fact category by fact entity
   */
  // 10159,4,4455,UNKNOWN все по число_id, любые категории
  @Query("SELECT fc FROM FactCategory fc WHERE fc.fact = :factEntity")
  FactCategory getFactCategoryByFactEntity(@Param("factEntity") Fact factEntity);

  /**
   * Find fact categories by number data and category name list.
   *
   * @param numberData the number data
   * @param categoryName the category name
   * @return the list
   */
  // 10159,4,4455,UNKNOWN
  @Query(
      "SELECT fc FROM FactCategory fc "
          + "JOIN fc.fact f "
          + // Присоединяем сущность Fact к FactCategory
          "JOIN f.number n "
          + // Присоединяем сущность Number к Fact
          "JOIN fc.category c "
          + // Присоединяем сущность Category к FactCategory
          "WHERE n.numberData = :numberData "
          + // Устанавливаем условие фильтрации по значению numberData
          "AND c.name = :categoryName") // Устанавливаем условие фильтрации по имени категории
  List<FactCategory> findFactCategoriesByNumberDataAndCategoryName(
      @Param("numberData") long numberData, @Param("categoryName") String categoryName);
}
