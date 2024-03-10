package org.example.numbeer.dao;
import org.example.numbeer.entity.Category;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
public class CategoryDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public void createCategory(Category category) {
        entityManager.persist(category);
    }

    public Category getCategoryById(long id) {
        return entityManager.find(Category.class, id);
    }

    public void updateCategory(Category category) {
        entityManager.merge(category);
    }

    public void deleteCategory(Category category) {
        entityManager.remove(category);
    }
}