/*
 * Created by Osman Balci on 2021.1.15
 * Copyright © 2021 Osman Balci. All rights reserved.
 */
// The package statement declares which Java package contains this class
package edu.vt.loan;

/*
The import statement is used to bring in the classes and/or Java packages
needed for the functionality of this class
*/
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/*
 The following @Named class annotation designates the bean object created by this class
 as a Contexts and Dependency Injection (CDI) managed bean.

 The "value" attribute defines the logical name of the bean, which is used to access
 the bean in the Expression Language (EL) expressions in a JSF facelets (XHTML) page.

 For example, the bean object's property monthlyPayment can be obtained using the
 EL expression #{autoLoan.monthlyPayment} in the "results" JSF page.
 */
@Named(value = "autoLoan")

/*
The @SessionScoped annotation indicates that this CDI-managed bean will be
maintained (i.e., its property values will be kept) across multiple HTTP requests
as long as the user's established HTTP session is active.
 */
@SessionScoped

/*
-----------------------------------------------------------------------------
Marking the AutoLoan class as "implements Serializable" implies that
instances of the class can be automatically serialized and deserialized.

Serialization is the process of converting a class instance (object)
from memory into a suitable format for storage in a file or memory buffer,
or for transmission across a network connection link.

Deserialization is the process of recreating a class instance (object)
in memory from the format under which it was stored.
-----------------------------------------------------------------------------
 */
public class AutoLoan implements Serializable {

    /*
    +++++++++++++++++++++++++++++++++++++++++++++++++++++++
    Instance variable (property) values entered by the user
    +++++++++++++++++++++++++++++++++++++++++++++++++++++++
    "Double" is a class. "double" is a primitive data type.
    An object of type "Double" contains a single field whose data type is "double".

    principalAmount, interestRatePercent, or numberOfYears is an object reference
    pointng to an object of type "Double".

    When this page is shown for the first time, since no value is yet assigned, each
    instance variable will have a "null" value, meaning the inputText field will be empty.

    When the user enters a value in the inputText field, that value will be set
    as the value of its corresponding instance variable (property).
    */
    private Double principalAmount;      // = P
    private Double interestRatePercent;
    private Double numberOfYears;        // = y

    /* Instance variable (property) values computed in calculateLoan() */
    private Double monthlyPayment;       // = M
    private Double totalInterest;

    /*
    ==================
    Constructor Method
    ==================
    Creates a new instance of AutoLoan
     */
    public AutoLoan() {
    }

    /*
    =========================
    Getter and Setter Methods
    =========================
     */
    public Double getPrincipalAmount() {
        return principalAmount;
    }

    public void setPrincipalAmount(Double principalAmount) {
        this.principalAmount = principalAmount;
    }

    public Double getInterestRatePercent() {
        return interestRatePercent;
    }

    public void setInterestRatePercent(Double interestRatePercent) {
        this.interestRatePercent = interestRatePercent;
    }

    public Double getNumberOfYears() {
        return numberOfYears;
    }

    public void setNumberOfYears(Double numberOfYears) {
        this.numberOfYears = numberOfYears;
    }

    public Double getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(Double monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public Double getTotalInterest() {
        return totalInterest;
    }

    public void setTotalInterest(Double totalInterest) {
        this.totalInterest = totalInterest;
    }

    /*
    ================
    Instance Methods
    ================
     */
    public String calculateLoan() {

        /*
         M = (P (i(1 + i)**n)) / ((1 + i)**n - 1)

            M = Monthly payment amount
            P = Principal amount borrowed
            r = Interest rate as a decimal value
            i = r / 12  for 12 monthly payments per year
            y = number of years
            n = 12 * y  number of monthly payments
         */
        double r = interestRatePercent / 100;  // e.g., 4.5 becomes 0.045
        double i = r / 12;
        double n = 12 * numberOfYears;

        double x = Math.pow(1 + i, n);   // = (1 + i) to the power n

        monthlyPayment = (principalAmount * (i * x)) / (x - 1);

        /* Round the calculated monthly payment value to 2 decimal places */
        monthlyPayment = monthlyPayment * 100;
        monthlyPayment = (double) Math.round(monthlyPayment);
        monthlyPayment = monthlyPayment / 100;

        totalInterest = (monthlyPayment * 12 * numberOfYears) - principalAmount;

        /* Round the calculated total interest value to 2 decimal places */
        totalInterest = totalInterest * 100;
        totalInterest = (double) Math.round(totalInterest);
        totalInterest = totalInterest / 100;

        /* Return to display the XHTML page "results" and update the page's URL */
        return "/results?faces-redirect=true";
    }

    public void clear() {
        // Set the object references point to nothing
        principalAmount = null;
        interestRatePercent = null;
        numberOfYears = null;
    }

}
