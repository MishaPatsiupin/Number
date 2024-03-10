package org.example.numbeer.dao;

import org.example.numbeer.entity.FactCategory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
public class FactCategoryDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public void createFactCategory(FactCategory factCategory) {
        entityManager.persist(factCategory);
    }

    public FactCategory getFactCategoryById(long id) {
        return entityManager.find(FactCategory.class, id);
    }

    public void updateFactCategory(FactCategory factCategory) {
        entityManager.merge(factCategory);
    }

    public void deleteFactCategory(FactCategory factCategory) {
        entityManager.remove(factCategory);
    }
}
