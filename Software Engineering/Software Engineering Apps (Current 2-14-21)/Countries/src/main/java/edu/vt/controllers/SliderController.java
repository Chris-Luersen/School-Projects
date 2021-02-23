/*
 * Created by Chris Luersen on 2021.2.23
 * Copyright © 2021 Chris Luersen. All rights reserved.
 */
package edu.vt.controllers;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named(value = "sliderController")
@RequestScoped

public class SliderController {

    // Each String object in the List contains the image filename, e.g., photo1.png
    private List<String> sliderImages;

    /*
    The PostConstruct annotation is used on a method that needs to be executed after
    dependency injection is done to perform any initialization. The initialization 
    method init() is the first method invoked before this class is put into service. 
    */
    @PostConstruct
    public void init() {

        sliderImages = new ArrayList<>();

        for (int i = 1; i <= 12; i++) {
            sliderImages.add("photo" + i + ".png");
        }
    }

    /*
    =============
    Getter Method
    =============
     */
    public List<String> getSliderImages() {
        return sliderImages;
    }
    
    /*
    ===============
    Instance Method
    ===============
     */
    public String description(String image) {

        String imageDescription = "";

        switch (image) {
            case "photo1.png":
                imageDescription = "Satellite View of North American Countries";
                break;
            case "photo2.png":
                imageDescription = "Political Map of Countries on the Planet of Earth";
                break;
            case "photo3.png":
                imageDescription = "Physical Map of Countries on the Planet of Earth";
                break;
            case "photo4.png":
                imageDescription = "Country Flags Around the Globe";
                break;
            case "photo5.png":
                imageDescription = "Continents of the Planet of Earth";
                break;
            case "photo6.png":
                imageDescription = "Map of the Continent of Australia";
                break;
            case "photo7.png":
                imageDescription = "Map of the Countries in the Continent of Asia";
                break;
            case "photo8.png":
                imageDescription = "Map of the Countries in the European Union";
                break;
            case "photo9.png":
                imageDescription = "Map of the Countries in the Continent of Africa";
                break;
            case "photo10.png":
                imageDescription = "Map of the Countries in the Continent of South America";
                break;
            case "photo11.png":
                imageDescription = "Flags of the Countries in the Continent of Africa";
                break;
            case "photo12.png":
                imageDescription = "Physical Map of Countries in the Middle East";
                break;
        }

        return imageDescription;
    }
}
