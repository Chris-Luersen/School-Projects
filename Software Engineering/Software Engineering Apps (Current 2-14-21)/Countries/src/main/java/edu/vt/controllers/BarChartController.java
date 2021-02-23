/*
 * Created by Chris Luersen on 2021.2.23
 * Copyright © 2021 Chris Luersen. All rights reserved.
 */
package edu.vt.controllers;

import edu.vt.EntityBeans.Country;
import edu.vt.FacadeBeans.CountryFacade;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Inject;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

/*
---------------------------------------------------------------------------
The @Named (javax.inject.Named) annotation indicates that the objects
instantiated from this class will be managed by the Contexts and Dependency
Injection (CDI) container. The name "barChartController" is used within
Expression Language (EL) expressions in JSF (XHTML) facelets pages to
access the properties and invoke methods of this class.
---------------------------------------------------------------------------
 */
@Named("barChartController")

/*
The @SessionScoped annotation preserves the values of the BarChartController
object's instance variables across multiple HTTP request-response cycles
as long as the user's established HTTP session is alive.
 */
@SessionScoped

/*
------------------------------------------------------------------------------
Marking the BarChartController class as "implements Serializable" implies that
instances of the class can be automatically serialized and deserialized. 

Serialization is the process of converting a class instance (object)
from memory into a suitable format for storage in a file or memory buffer, 
or for transmission across a network connection link.

Deserialization is the process of recreating a class instance (object)
in memory from the format under which it was stored.
------------------------------------------------------------------------------
 */
public class BarChartController implements Serializable {

    /*
    ===============================
    Instance Variables (Properties)
    ===============================

    Instance variable to contain the object reference of the BarChartModel
    (org.primefaces.model.chart.BarChartModel) object to be instantiated.
     */
    private BarChartModel barModel;

    // Maximum Y axis value of Total Area or Population
    Integer maxAreaOrPopulation;

    /*
    The @Inject annotation directs the storage (injection) of the object 
    reference of the CDI container-managed PickListController bean into the 
    instance variable pickListController below after it is instantiated at runtime.
     */
    @Inject
    PickListController pickListController;

    /*
    The @EJB annotation directs the storage (injection) of the object 
    reference of the JPA CountryFacade class object into the instance
    variable countryFacade below after it is instantiated at runtime.
     */
    @EJB
    private CountryFacade countryFacade;

    /*
    =============
    Getter Method
    =============
     */
    public BarChartModel getBarModel() {
        createBarModel();
        return barModel;
    }

    /*
    ================
    Instance Methods
    ================

    ********************
    Initialize Bar Model
    ********************
     */
    private BarChartModel initBarModel() {

        BarChartModel model = new BarChartModel();

        // Initialize maxAreaOrPopulation every time the chart is to be created
        maxAreaOrPopulation = 1;

        ChartSeries countryTotalArea = new ChartSeries();
        countryTotalArea.setLabel("Total Area in Thousands");

        ChartSeries countryPopulation = new ChartSeries();
        countryPopulation.setLabel("Population in Millions");

        // Obtain the list of selected country names
        List<String> selectedCountryNamesList = pickListController.getCountryNames().getTarget();

        // Iteration in Java 8 using the forEach() method with lamda
        selectedCountryNamesList.forEach((countryName) -> {

            // Obtain the object reference of the country object whose name is countryName
            Country countryObject = countryFacade.findByCountryName(countryName);

            // Obtain the country's totalArea in thousands
            Integer totalArea = countryObject.getTotalArea() / 1000;

            if (totalArea > maxAreaOrPopulation) {
                maxAreaOrPopulation = totalArea;
            }

            // Obtain the country's population in millions
            Integer population = countryObject.getPopulation() / 1000000;

            if (population > maxAreaOrPopulation) {
                maxAreaOrPopulation = population;
            }

            countryTotalArea.set(countryName, totalArea);
            countryPopulation.set(countryName, population);
        });

        model.addSeries(countryTotalArea);
        model.addSeries(countryPopulation);

        // JavaScript function "barChartExtender" in BarChart.xhtml is used to style the chart
        model.setExtender("barChartExtender");

        return model;
    }

    /*
    ****************
    Create Bar Chart
    ****************
     */
    private void createBarModel() {

        barModel = initBarModel();

        barModel.setTitle("Country Bar Chart");
        barModel.setLegendPosition("ne");

        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Country Name");

        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Total Area / Population");

        yAxis.setMin(0);
        yAxis.setMax(maxAreaOrPopulation);
    }

}
