/*
 * Created by Chris Luersen on 2021.2.23
 * Copyright © 2021 Chris Luersen. All rights reserved.
 */
package edu.vt.FacadeBeans;

import java.util.List;
/* 
 An instance of javax.persistence.EntityManager represents an Entity Manager.
 An Entity Manager manages JPA Entities. 
 Each Entity Manager instance is associated with a persistence context.
 A persistence context is a set of managed entity instances. 
 */
import javax.persistence.EntityManager;

/**
 * The AbstractFacade.java is an abstract Facade class providing a generic interface to the Entity Manager.
 *
 * @author Balci
 * @param <T> refers to the Class Type
 */
public abstract class AbstractFacade<T> {

    // An instance variable pointing to a class object of type T
    private final Class<T> entityClass;

    /*  
     This is the constructor method called by the subclass CountryFacade.java class's
     constructor method by passing the Country.class as a parameter.
     Country.class returns an object reference to the Country class, which is set 
     as the value of the entityClass instance variable.
     The class type T is set to the Country entity class. So, T = Country
     */
    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /* 
     This method is overridden in CountryFacade.java, which is the 
     concrete Facade subclass providing the actual implementation. 
     */
    protected abstract EntityManager getEntityManager();

    /**
     * Stores the newly CREATED Country (entity) object into the database.
     *
     * @param entity contains the object reference of the newly created Country.
     */
    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    /**
     * Stores the EDITED (UPDATED) Country (entity) object into the database.
     *
     * @param entity contains the object reference of the edited Country.
     */
    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    /**
     * Deletes (REMOVES) the given Country (entity) object from the database.
     *
     * @param entity contains the object reference of the Country to be deleted.
     */
    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    /**
     * Returns Object reference of the Country whose database Primary Key = id
     *
     * @param id refers to the Primary Key of the Country.
     * @return the object reference of the Country found.
     */
    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    /**
     * @return a list of object references of all of the Country entities (objects) in the database.
     */
    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    /*
    Finds and returns a list of Country objects within the specified id range from the database.
    
    The range is specified by the range parameter of type integer array. 
    The 1st element of the range array = index number of the first Country object to retrieve. 
    The 2nd element of the range array = index number of the last Country object to retrieve.
     */
    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    /**
     * Obtains and returns the total number of Country entities in the database.
     *
     * @return the total number of Country entities in the database.
     */
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
