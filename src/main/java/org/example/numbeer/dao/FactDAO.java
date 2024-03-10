package org.example.numbeer.dao;
import org.example.numbeer.entity.Fact;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
public class FactDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public void createFact(Fact fact) {
        entityManager.persist(fact);
    }

    public Fact getFactById(long id) {
        return entityManager.find(Fact.class, id);
    }

    public void updateFact(Fact fact) {
        entityManager.merge(fact);
    }

    public void deleteFact(Fact fact) {
        entityManager.remove(fact);
    }
}