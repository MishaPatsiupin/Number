package com.example.demo.repository;

import com.example.demo.entity.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/** The interface Category repository. */
public interface CategoryRepository extends CrudRepository<Category, Long> {

  /**
   * Find id by name long.
   *
   * @param name the name
   * @return the long
   */
  @Query("SELECT c.id FROM Category c WHERE c.name = :name")
  long findIdByName(@Param("name") String name);

  /**
   * Find category by name category.
   *
   * @param name the name
   * @return the category
   */
  @Query("SELECT c FROM Category c WHERE c.name = :name")
  Category findCategoryByName(@Param("name") String name);
}
