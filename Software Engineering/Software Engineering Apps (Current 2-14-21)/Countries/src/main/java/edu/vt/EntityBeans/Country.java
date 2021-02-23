/*
 * Created by Chris Luersen on 2021.2.23
 * Copyright © 2021 Chris Luersen. All rights reserved.
 */
package edu.vt.EntityBeans;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
//import javax.xml.bind.annotation.XmlRootElement;

/*
The @Entity annotation designates this class as a JPA Entity POJO class
representing the Country table in the CountriesDB database.
 */
@Entity

// Name of the table in the CountriesDB database
@Table(name = "Country")

//@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Country.findAll", query = "SELECT c FROM Country c")
    , @NamedQuery(name = "Country.findById", query = "SELECT c FROM Country c WHERE c.id = :id")
    , @NamedQuery(name = "Country.findByName", query = "SELECT c FROM Country c WHERE c.name = :name")
    , @NamedQuery(name = "Country.findByCode", query = "SELECT c FROM Country c WHERE c.code = :code")
    , @NamedQuery(name = "Country.findByCapitalCity", query = "SELECT c FROM Country c WHERE c.capitalCity = :capitalCity")
    , @NamedQuery(name = "Country.findByPopulation", query = "SELECT c FROM Country c WHERE c.population = :population")
    , @NamedQuery(name = "Country.findByTotalArea", query = "SELECT c FROM Country c WHERE c.totalArea = :totalArea")
    , @NamedQuery(name = "Country.findByLanguage", query = "SELECT c FROM Country c WHERE c.language = :language")
    , @NamedQuery(name = "Country.findByCurrency", query = "SELECT c FROM Country c WHERE c.currency = :currency")
})

public class Country implements Serializable {

    /*
    ========================================================
    Instance variables representing the attributes (columns)
    of the Country table in the CountriesDB database.
    ========================================================
     */
    // Primary Key id is internally used; not shown to the user
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    // Country Name
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "name")
    private String name;

    // 2-letter unique country code (which is also the flag name)
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "code")
    private String code;

    // Country's capital city name
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "capital_city")
    private String capitalCity;

    // Country's population
    @Basic(optional = false)
    @NotNull
    @Column(name = "population")
    private Integer population;

    // Country's total area in Square Miles
    @Basic(optional = false)
    @NotNull
    @Column(name = "total_area")
    private Integer totalArea;

    // Country's mostly used language
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "language")
    private String language;
    
    // 'language' was a reserved keyword in SQL in 1999, but not any more.

    // Country's currency name
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "currency")
    private String currency;

    /*
    =================================================================
    Class constructors for instantiating a Country entity / object to
    represent a row in the Country table in the CountriesDB database.
    =================================================================
     */
    public Country() {
    }

    public Country(Integer id) {
        this.id = id;
    }

    public Country(Integer id, String name, String code, String capitalCity,
            Integer population, Integer totalArea, String language, String currency) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.capitalCity = capitalCity;
        this.population = population;
        this.totalArea = totalArea;
        this.language = language;
        this.currency = currency;
    }

    /*
    ======================================================
    Getter and Setter methods for the attributes (columns)
    of the Country table in the CountriesDB database.
    ======================================================
     */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCapitalCity() {
        return capitalCity;
    }

    public void setCapitalCity(String capitalCity) {
        this.capitalCity = capitalCity;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public Integer getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(Integer totalArea) {
        this.totalArea = totalArea;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /*
    ================
    Instance Methods
    ================
     */
    /**
     * Generates and returns a hash code value for the object with id
     *
     * @return A hash code value for the object with id
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     *
     * Checks if the MovieStar object identified by 'object' is the same as the Country object identified by 'id'
     *
     * @param object The Country object identified by 'object'
     * @return True if the Country 'object' and 'id' are the same; otherwise, return False
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Country)) {
            return false;
        }
        Country other = (Country) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    /**
     *
     * @return the String representation of the Country's database primary key
     */
    @Override
    public String toString() {
         // Convert the Country object's database primary key (Integer) to String type and return it.
        return id.toString();
    }

}
