package com.example.demo.repository;

import com.example.demo.entity.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    @Query("SELECT c.id FROM Category c WHERE c.name = :name")
    long findIdByName(@Param("name") String name);

    @Query("SELECT c FROM Category c WHERE c.name = :name")
    Category findCategoryByName(@Param("name") String name);
}
