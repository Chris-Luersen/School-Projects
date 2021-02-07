/*
 * Created by Chris Luersen on 2021.2.7
 * Copyright © 2021 Chris Luersen. All rights reserved.
 */

package edu.vt.controllers;/*
 * Created by Osman Balci on 2021.1.18
 * Copyright © 2021 Osman Balci. All rights reserved.
 */

import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

/*
 The following @Named class annotation designates the bean object created by this class
 as a Contexts and Dependency Injection (CDI) managed bean.

 The object reference of a CDI-managed bean can be @Inject'ed in another CDI-managed bean
 so that the other CDI-managed bean can access the methods and properties of that bean.

 The "value" attribute defines the logical name of the bean, which is used to access
 the bean through the Expression Language (EL) in a JSF facelets (XHTML) page.

 For example, the bean object's property heightInFeet can be obtained or set using the
 EL expression #{personController.heightInFeet} in a JSF page.
 */
@Named(value = "personController")

/*
The @SessionScoped annotation indicates that this CDI-managed bean's
instance variable (property) values will be kept across multiple HTTP
requests as long as the user's established HTTP session is active.
 */
@SessionScoped

public class PersonController implements Serializable {

    /*
    ===============================
    Instance Variables (Properties)
    ===============================
     */
    // The values of these instance variables are supplied by the user
    private Double heightInFeet;    // edu.vt.controllers.PersonController's height in Feet
    private Double heightInInches;  // edu.vt.controllers.PersonController's height in Inches
    private Double weightInPounds;  // edu.vt.controllers.PersonController's weight in Pounds

    // The values of these instance variables are computed in the calculateBMI method
    private Double bmiValue;
    private String bmiCategory;

    /*
    ************************************************************************************************
    The import javax.inject.Inject; brings in the javax.inject package into our project.
    "This package specifies a means for obtaining objects in such a way as to maximize
    reusability, testability and maintainability compared to traditional approaches such as
    constructors, factories, and service locators (e.g., JNDI). This process, known as
    dependency injection, is beneficial to most nontrivial applications." [Oracle]

    The @Inject annotation of the instance variable "private edu.vt.controllers.EditorController editorController;"
    directs the CDI Container Manager to store the object reference of the edu.vt.controllers.EditorController class
    bean object, after it is instantiated at runtime, into the instance variable "editorController".

    With this injection, the instance variables and instance methods of the edu.vt.controllers.EditorController
    class can be accessed in this CDI-managed bean.
    ************************************************************************************************
    */
    @Inject
    private EditorController editorController;

    /*
    ==================
    Constructor Method
    ==================
     */
    public PersonController() {
    }

    /*
    =========================
    Getter and Setter edu.vt.globals.Methods
    =========================
     */
    public EditorController getEditorController() {
        return editorController;
    }

    public void setEditorController(EditorController editorController) {
        this.editorController = editorController;
    }

    public Double getHeightInFeet() {
        return heightInFeet;
    }

    public void setHeightInFeet(Double heightInFeet) {
        this.heightInFeet = heightInFeet;
    }

    public Double getHeightInInches() {
        return heightInInches;
    }

    public void setHeightInInches(Double heightInInches) {
        this.heightInInches = heightInInches;
    }

    public Double getWeightInPounds() {
        return weightInPounds;
    }

    public void setWeightInPounds(Double weightInPounds) {
        this.weightInPounds = weightInPounds;
    }

    public Double getBmiValue() {
        return bmiValue;
    }

    public void setBmiValue(Double bmiValue) {
        this.bmiValue = bmiValue;
    }

    public String getBmiCategory() {
        return bmiCategory;
    }

    public void setBmiCategory(String bmiCategory) {
        this.bmiCategory = bmiCategory;
    }

    /*
    ================
    Instance edu.vt.globals.Methods
    ================
     */
    /**
     * Compute bmiValue and bmiCategory
     *
     * @return Display the Results.xhtml page
     */
    public String calculateBMI() {

        double totalHeightInInches = heightInInches + (12.0 * heightInFeet);
        double bmi = (weightInPounds * 703.0) / (totalHeightInInches * totalHeightInInches);

        /*
        Round the calculated BMI value to 2 decimal places.
        100d --> 2 decimal places; 1000d --> 3 decimal places; 10000d --> 4 decimal places; etc.
         */
        bmiValue = (double) Math.round(bmi * 100d) / 100d;

        if (bmiValue < 18.5) {
            bmiCategory = "Underweight";
        } else if (bmiValue <= 24.9) {
            bmiCategory = "Normal Weight";
        } else if (bmiValue < 30) {
            bmiCategory = "Overweight";
        } else if (bmiValue >= 30) {
            bmiCategory = "Obese";
        }

        // Redirect to show the Results.xhtml page
        return "/bmi/Results?faces-redirect=true";
    }

    /**
     * Reset the input data fields
     */
    public void clear() {
        heightInFeet = null;
        heightInInches = null;
        weightInPounds = null;
    }

    /**
     * Composes the initial content of the Email message.
     *
     * @return Email.xhtml
     */
    public String prepareEmailBody() {

        String imageUrl = "";

        switch (bmiCategory) {
            case "Underweight":
                // To enter a double quote " in a string literal, use the backslash \ escape character as \"
                imageUrl = "<img src=\"https://manta.cs.vt.edu/csd/underweight.png\" style=\"width:200px;height:200px;\">";
                break;
            case "Normal Weight":
                imageUrl = "<img src=\"https://manta.cs.vt.edu/csd/normalWeight.png\" style=\"width:200px;height:200px;\">";
                break;
            case "Overweight":
                imageUrl = "<img src=\"https://manta.cs.vt.edu/csd/overweight.png\" style=\"width:200px;height:200px;\">";
                break;
            case "Obese":
                imageUrl = "<img src=\"https://manta.cs.vt.edu/csd/obese.png\" style=\"width:200px;height:200px;\">";
                break;
            default:
                break;
        }

        // Compose the email message content in HTML format
        String emailBodyText = "<div align=\"center\">" + imageUrl +
                "<br /><br /><b>Body Mass Index (BMI)</b><br /><br />Computed BMI value is "
                + bmiValue + "<br /><br />The person is " + bmiCategory + "!<p>&nbsp;</p></div>";

        // Set the HTML content to be the body of the email message
        editorController.setEmailMessage(emailBodyText);

        // Redirect to show the Email.xhtml page
        return "/send/Email?faces-redirect=true";
    }

}
