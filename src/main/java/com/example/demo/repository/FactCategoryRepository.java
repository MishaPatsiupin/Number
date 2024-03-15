package com.example.demo.repository;

import com.example.demo.entity.FactCategoryEntity;
import org.springframework.data.repository.CrudRepository;

public interface FactCategoryRepository extends CrudRepository<FactCategoryEntity, Long> {
}
