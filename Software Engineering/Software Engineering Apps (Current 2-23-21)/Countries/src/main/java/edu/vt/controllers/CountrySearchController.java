/*
 * Created by Chris Luersen on 2021.2.23
 * Copyright © 2021 Chris Luersen. All rights reserved.
 */
package edu.vt.controllers;

import edu.vt.globals.Methods;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;

@SessionScoped
@Named(value = "countrySearchController")

public class CountrySearchController implements Serializable {

    /*
    ===============================
    Instance Variables (Properties)
    ===============================
     */
    // Provided by the User
    private String searchField;
    private String searchString;

    // Used for Search Processing
    private String searchApiUrl;

    // Country Data Determined by Search Results
    private String name;
    private String alpha2Code;
    private String alpha3Code;
    private String capital;
    private Integer population;
    private Double latitude;
    private Double longitude;
    private Integer area;
    private String currencyCode;
    private String currencyName;
    private String language;
    private String flagUrl;

    /*
    =========================
    Getter and Setter Methods
    =========================
     */
    public String getSearchField() {
        return searchField;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public String getSearchApiUrl() {
        return searchApiUrl;
    }

    public void setSearchApiUrl(String searchApiUrl) {
        this.searchApiUrl = searchApiUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlpha2Code() {
        return alpha2Code;
    }

    public void setAlpha2Code(String alpha2Code) {
        this.alpha2Code = alpha2Code;
    }

    public String getAlpha3Code() {
        return alpha3Code;
    }

    public void setAlpha3Code(String alpha3Code) {
        this.alpha3Code = alpha3Code;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getFlagUrl() {
        return flagUrl;
    }

    public void setFlagUrl(String flagUrl) {
        this.flagUrl = flagUrl;
    }

    /*
    ================
    Instance Methods
    ================
     */
    public void performSearch() {

        switch (searchField) {
            case "Country Full Name":
                searchApiUrl = "https://restcountries.eu/rest/v2/name/" + searchString + "?fullText=true";
                break;
            case "Country Code":
                searchApiUrl = "https://restcountries.eu/rest/v2/alpha/" + searchString;
                break;
            case "Capital City Name":
                searchApiUrl = "https://restcountries.eu/rest/v2/capital/" + searchString;
                break;
            default:
                System.out.print("Search Field Value is Out of Range!");
        }

        /*
        Redirecting to show a JSF page involves more than one subsequent requests and
        the messages would die from one request to another if not kept in the Flash scope.
        Since we will redirect to show the search Results page, we invoke preserveMessages().
         */
        Methods.preserveMessages();

        /*
        JSON uses the following notation:
        { }    represents a JavaScript object as a Dictionary with Key:Value pairs
        [ ]    represents Array
        [{ }]  represents an Array of JavaScript objects (dictionaries)
          :    separates Key from the Value
         */
        try {
            // Obtain the JSON file from the searchApiUrl
            String searchResultsJsonData = Methods.readUrlContent(searchApiUrl);
            
            /*
            https://restcountries.eu/rest/v2/name/germany?fullText=true returns the following JSON data:
            [
                {
                    "name":"Germany",
                    "topLevelDomain":[".de"],
                    "alpha2Code":"DE",
                    "alpha3Code":"DEU",
                    "callingCodes":["49"],
                    "capital":"Berlin",
                    "altSpellings":["DE","Federal Republic of Germany","Bundesrepublik Deutschland"],
                    "region":"Europe",
                    "subregion":"Western Europe",
                    "population":81770900,
                    "latlng":[51.0,9.0],
                    "demonym":"German",
                    "area":357114.0,
                    "gini":28.3,
                    "timezones":["UTC+01:00"],
                    "borders":["AUT","BEL","CZE","DNK","FRA","LUX","NLD","POL","CHE"],
                    "nativeName":"Deutschland",
                    "numericCode":"276",
                    "currencies":[{"code":"EUR","name":"Euro","symbol":"€"}],
                    "languages":[{"iso639_1":"de","iso639_2":"deu","name":"German","nativeName":"Deutsch"}],
                    "translations":{"de":"Deutschland","es":"Alemania","fr":"Allemagne",
                            "ja":"ドイツ","it":"Germania","br":"Alemanha","pt":"Alemanha",
                            "nl":"Duitsland","hr":"Njemačka","fa":"آلمان"},
                    "flag":"https://restcountries.eu/data/deu.svg",
                    "regionalBlocs":[{"acronym":"EU","name":"European Union","otherAcronyms":[],"otherNames":[]}],
                    "cioc":"GER"
                }
            ]
            */

            JSONArray jsonArray;
            JSONObject countryJsonObject;

            /*
            Country Full Name or Capital City Name search returns a JSON array.
            Country Code, 2 letters or 3 letters, search returns a JSON object.
            Therefore, we do the processing by testing the first character.
            */
            char firstChar = searchResultsJsonData.charAt(0);

            if (firstChar == '[') {
                // It is a JSON array
                jsonArray = new JSONArray(searchResultsJsonData);
                countryJsonObject = jsonArray.getJSONObject(0);
            } else {
                // It is a JSON object
                countryJsonObject = new JSONObject(searchResultsJsonData);
            }

            //------------------
            // Country Full Name
            //------------------
            /*
            Try to obtain the value for the key "name" as of String type.
            If the value is actually of String type, return it as such.
            If the value does not exist or it is of different type, return "".
            */
            name = countryJsonObject.optString("name", "");
            if (name.equals("")) {
                name = "Country Name Unavailable!";
            }

            //----------------------
            // Country 2-Letter Code
            //----------------------
            alpha2Code = countryJsonObject.optString("alpha2Code", "");
            if (alpha2Code.equals("")) {
                alpha2Code = "alpha2Code Unavailable!";
            }

            //----------------------
            // Country 3-Letter Code
            //----------------------
            alpha3Code = countryJsonObject.optString("alpha3Code", "");
            if (alpha3Code.equals("")) {
                alpha3Code = "alpha3Code Unavailable!";
            }

            //--------------------------
            // Country Capital City Name
            //--------------------------
            capital = countryJsonObject.optString("capital", "");
            if (capital.equals("")) {
                capital = "Capital City Name Unavailable!";
            }

            //-------------------
            // Country Population
            //-------------------
            /*
            Try to obtain the value for the key "population" as of Integer type.
            If the value is actually of Integer type, return it as such.
            If the value does not exist or it is of different type, return 0.
            population = 0 --> Indicates that the value is unavailable.
            */
            population = countryJsonObject.optInt("population", 0);

            //--------------------------------------
            // Country Center Latitude and Longitude
            //--------------------------------------
            JSONArray latlng = countryJsonObject.getJSONArray("latlng");

            latitude = latlng.getDouble(0);
            longitude = latlng.getDouble(1);

            //-----------------------------------
            // Country Total Area in Square Miles
            //-----------------------------------
            /*
            Try to obtain the value for the key "area" as of Double type.
            If the value is actually of Double type, return it as such.
            If the value does not exist or it is of different type, return 0.0.
            area = 0.0 --> Indicates that the value is unavailable.
            */
            Double countryAreaInSquareKilometers = countryJsonObject.optDouble("area", 0.0);

            /*
            The Countries API provides the country area in Square Kilometers.
            We need to convert it into Square Miles.
            1 Square Kilometers = 0.386102 Square Miles
             */
            Double countryAreaInSquareMiles = countryAreaInSquareKilometers * 0.386102;

            // Convert the Double value to Integer
            area = countryAreaInSquareMiles.intValue();

            //-----------------------
            // Currency Code and Name
            //-----------------------
            JSONArray currenciesArray = countryJsonObject.getJSONArray("currencies");
            JSONObject currenciesObject = currenciesArray.getJSONObject(0);

            currencyCode = currenciesObject.optString("code", "");
            if (currencyCode.equals("")) {
                currencyCode = "Currency Code Unavailable!";
            }

            currencyName = currenciesObject.optString("name", "");
            if (currencyName.equals("")) {
                currencyName = "Currency Name Unavailable!";
            }

            //------------------------
            // Country's Main Language
            //------------------------
            JSONArray languagesArray = countryJsonObject.getJSONArray("languages");
            JSONObject languagesObject = languagesArray.getJSONObject(0);

            language = languagesObject.optString("name", "");
            if (language.equals("")) {
                language = "Language Unavailable!";
            }

            //---------------------------
            // Country Flag SVG Image URL
            //---------------------------
            flagUrl = countryJsonObject.optString("flag", "");
            if (flagUrl.equals("")) {
                flagUrl = "Country Flag Image URL Unavailable!";
            }

        } catch (Exception e) {
            Methods.showMessage("Fatal Error", "Unrecognized Search Query!",
                    "The Countries API provides no data for the search query entered!");
            clear();
        }

        // Reset search queries
        searchString = "";
        searchField = null;
    }

    public void clear() {
        name = null;
        alpha2Code = null;
        alpha3Code = null;
        capital = null;
        population = null;
        latitude = null;
        longitude = null;
        area = null;
        currencyCode = null;
        currencyName = null;
        language = null;
        flagUrl = null;
    }

}
