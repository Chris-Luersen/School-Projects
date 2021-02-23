/*
 * Created by Chris Luersen on 2021.2.23
 * Copyright © 2021 Chris Luersen. All rights reserved.
 */
package edu.vt.FacadeBeans;

import edu.vt.EntityBeans.Country;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

// @Stateless annotation implies that the conversational state with the client shall NOT be maintained.
@Stateless
public class CountryFacade extends AbstractFacade<Country> {

    /*
    Annotating 'private EntityManager em;' with '@PersistenceContext(unitName = "Countries-LuersenPU")'
    implies that the EntityManager instance pointed to by 'em' is associated with the 
    'Countries-LuersenPU' persistence context. The persistence context is a set of entity (Country)
    instances in which for any persistent entity (Country) identity, there is a unique entity (Country)
    instance. Within the persistence context, the entity (Country) instances and their life cycle are 
    managed. The EntityManager API is used to create and remove persistent entity (Country) instances, 
    to find entities by their primary key, and to query over entities (Country).
     */
    @PersistenceContext(unitName = "Countries-LuersenPU")
    private EntityManager em;

    // @Override annotation indicates that the super class AbstractFacade's 
    // getEntityManager() method is overridden.
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /*
    This constructor method invokes the parent abstract class AbstractFacade.java's 
    constructor method, which in turn initializes its entityClass instance variable
    with the Country class object reference returned by the Country.class. 
     */
    public CountryFacade() {
        // Invokes super's AbstractFacade constructor method by passing
        // Country.class, which is the object reference of the Country class.
        super(Country.class);
    }

    /*
    ============
    Added Method
    ============
     */
    /**
     *
     * @param country_name
     * @return Object reference of the country object whose name is country_name
     */
    public Country findByCountryName(String country_name) {
        /*
        The following @NamedQuery definition is given in Country.java entity class file:
        @NamedQuery(name = "Country.findByName", query = "SELECT c FROM Country c WHERE c.name = :name")
         */

        if (em.createNamedQuery("Country.findByName")
                .setParameter("name", country_name)
                .getResultList().isEmpty()) {
            
            // Return null if no country object exists by the name of country_name
            return null;
            
        } else {
            
            // Return the Object reference of the country object whose name is country_name
            return (Country) (em.createNamedQuery("Country.findByName")
                    .setParameter("name", country_name)
                    .getSingleResult());
        }
    }

}
