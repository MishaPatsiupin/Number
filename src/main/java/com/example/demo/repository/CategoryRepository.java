package com.example.demo.repository;

import com.example.demo.entity.CategoryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends CrudRepository<CategoryEntity, Long> {

    @Query("SELECT c.id FROM CategoryEntity c WHERE c.name = :name")
    long findIdByName(@Param("name") String name);

    @Query("SELECT c FROM CategoryEntity c WHERE c.name = :name")
    CategoryEntity findCategoryByName(@Param("name") String name);
}
