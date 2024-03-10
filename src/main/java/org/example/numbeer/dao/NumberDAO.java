package org.example.numbeer.dao;
import org.example.numbeer.entity.Number;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
public class NumberDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public void createNumber(long number) {
        Number numberEntity = new Number();
        numberEntity.setNumberData(number);
        entityManager.persist(numberEntity);
    }

    public Number getNumberById(long id) {
        return entityManager.find(Number.class, id);
    }

    public void updateNumber(Number number) {
        entityManager.merge(number);
    }

    public void deleteNumber(Number number) {
        entityManager.remove(number);
    }
}