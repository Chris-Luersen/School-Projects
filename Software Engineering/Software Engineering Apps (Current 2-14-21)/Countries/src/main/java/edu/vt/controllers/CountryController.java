/*
 * Created by Chris Luersen on 2021.2.23
 * Copyright © 2021 Chris Luersen. All rights reserved.
 */
package edu.vt.controllers;

import edu.vt.EntityBeans.Country;
import edu.vt.controllers.util.JsfUtil;
import edu.vt.controllers.util.JsfUtil.PersistAction;
import edu.vt.FacadeBeans.CountryFacade;
import edu.vt.globals.Methods;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/*
---------------------------------------------------------------------------
The @Named (javax.inject.Named) annotation indicates that the objects
instantiated from this class will be managed by the Contexts and Dependency
Injection (CDI) container. The name "countryController" is used within
Expression Language (EL) expressions in JSF (XHTML) facelets pages to
access the properties and invoke methods of this class.
---------------------------------------------------------------------------
 */
@Named("countryController")

/*
The @SessionScoped annotation preserves the values of the CountryController
object's instance variables across multiple HTTP request-response cycles
as long as the user's established HTTP session is alive.
 */
@SessionScoped

/*
-----------------------------------------------------------------------------
Marking the CountryController class as "implements Serializable" implies that
instances of the class can be automatically serialized and deserialized. 

Serialization is the process of converting a class instance (object)
from memory into a suitable format for storage in a file or memory buffer, 
or for transmission across a network connection link.

Deserialization is the process of recreating a class instance (object)
in memory from the format under which it was stored.
-----------------------------------------------------------------------------
 */
public class CountryController implements Serializable {

    /*
    ===============================
    Instance Variables (Properties)
    ===============================

    The @EJB annotation directs the storage (injection) of the object 
    reference of the JPA CountryFacade class object into the instance
    variable countryFacade below after it is instantiated at runtime.
     */
    @EJB
    private CountryFacade countryFacade;

    private List<Country> items = null;
    private Country selected;
    private Boolean countryDataChanged;

    /*
    Create and initialize a List of flagNames for 196 countries. 
    Note: flag image name = 2-letter unique country code.
    Flag image filename = flagName.png obtained from https://manta.cs.vt.edu/csd/flags/
    Example for US flag: https://manta.cs.vt.edu/csd/flags/us.png
     */
    private final List<String> flagNames = Arrays.asList("ad", "ae", "af", "ag", "al", "am", "ao", "ar", "at", "au", "az", "ba", "bb", "bd", "be", "bf", "bg", "bh", "bi", "bj", "bn", "bo", "br", "bs", "bt", "bw", "by", "bz", "ca", "cd", "cf", "cg", "ch", "ci", "cl", "cm", "cn", "co", "cr", "cu", "cv", "cy", "cz", "de", "dj", "dk", "dm", "do", "dz", "ec", "ee", "eg", "eh", "er", "es", "et", "fi", "fj", "fm", "fr", "ga", "gb", "gd", "ge", "gh", "gm", "gn", "gq", "gr", "gt", "gw", "gy", "hn", "hr", "ht", "hu", "id", "ie", "il", "in", "iq", "ir", "is", "it", "jm", "jo", "jp", "ke", "kg", "kh", "ki", "km", "kn", "kp", "kr", "ks", "kw", "kz", "la", "lb", "lc", "li", "lk", "lr", "ls", "lt", "lu", "lv", "ly", "ma", "mc", "md", "me", "mg", "mh", "mk", "ml", "mm", "mn", "mr", "mt", "mu", "mv", "mw", "mx", "my", "mz", "na", "ne", "ng", "ni", "nl", "no", "np", "nr", "nz", "om", "pa", "pe", "pg", "ph", "pk", "pl", "pt", "pw", "py", "qa", "ro", "rs", "ru", "rw", "sa", "sb", "sc", "sd", "se", "sg", "si", "sk", "sl", "sm", "sn", "so", "sr", "st", "sv", "sy", "sz", "td", "tg", "th", "tj", "tl", "tm", "tn", "to", "tr", "tt", "tv", "tw", "tz", "ua", "ug", "us", "uy", "uz", "va", "vc", "ve", "vn", "vu", "ws", "ye", "za", "zm", "zw");

    /*
    HashMap object stores Key-Value pairs, where the first entry is the key.
    Given the Key, the corresponding value is obtained with the GET method.
    The Key-Value pairs are stored in HashMap in no particular order.
     */
    HashMap<String, String> countryLookupMap = new HashMap<>();
    HashMap<String, String> countryLookupLatLong = new HashMap<>();

    /*
    ==================
    Constructor Method
    ==================
     */
    public CountryController() {

        /*
        This constructor method is called first right after the object is
        instantiated from this class. The code included in the constructor
        method is intended to initialize and dress up the newly created object.
         */
        // Initialize the HashMap for 239 countries
        // KEY = Country Code, VALUE = Country Name
        countryLookupMap.put("AD", "Andorra");
        countryLookupMap.put("AE", "United Arab Emirates");
        countryLookupMap.put("AF", "Afghanistan");
        countryLookupMap.put("AG", "Antigua and Barbuda");
        countryLookupMap.put("AI", "Anguilla");
        countryLookupMap.put("AL", "Albania");
        countryLookupMap.put("AM", "Armenia");
        countryLookupMap.put("AN", "Netherlands Antilles");
        countryLookupMap.put("AO", "Angola");
        countryLookupMap.put("AQ", "Antarctica");
        countryLookupMap.put("AR", "Argentina");
        countryLookupMap.put("AS", "American Samoa");
        countryLookupMap.put("AT", "Austria");
        countryLookupMap.put("AU", "Australia");
        countryLookupMap.put("AW", "Aruba");
        countryLookupMap.put("AZ", "Azerbaijan");
        countryLookupMap.put("BA", "Bosnia and Herzegovina");
        countryLookupMap.put("BB", "Barbados");
        countryLookupMap.put("BD", "Bangladesh");
        countryLookupMap.put("BE", "Belgium");
        countryLookupMap.put("BF", "Burkina Faso");
        countryLookupMap.put("BG", "Bulgaria");
        countryLookupMap.put("BH", "Bahrain");
        countryLookupMap.put("BI", "Burundi");
        countryLookupMap.put("BJ", "Benin");
        countryLookupMap.put("BM", "Bermuda");
        countryLookupMap.put("BN", "Brunei");
        countryLookupMap.put("BO", "Bolivia");
        countryLookupMap.put("BR", "Brazil");
        countryLookupMap.put("BS", "Bahamas");
        countryLookupMap.put("BT", "Bhutan");
        countryLookupMap.put("BV", "Bouvet Island");
        countryLookupMap.put("BW", "Botswana");
        countryLookupMap.put("BY", "Belarus");
        countryLookupMap.put("BZ", "Belize");
        countryLookupMap.put("CA", "Canada");
        countryLookupMap.put("CC", "Cocos (Keeling) Islands");
        countryLookupMap.put("CD", "Congo, The Democratic Republic of the");
        countryLookupMap.put("CF", "Central African Republic");
        countryLookupMap.put("CG", "Congo");
        countryLookupMap.put("CH", "Switzerland");
        countryLookupMap.put("CI", "Côte d'Ivoire");
        countryLookupMap.put("CK", "Cook Islands");
        countryLookupMap.put("CL", "Chile");
        countryLookupMap.put("CM", "Cameroon");
        countryLookupMap.put("CN", "China");
        countryLookupMap.put("CO", "Colombia");
        countryLookupMap.put("CR", "Costa Rica");
        countryLookupMap.put("CU", "Cuba");
        countryLookupMap.put("CV", "Cape Verde");
        countryLookupMap.put("CX", "Christmas Island");
        countryLookupMap.put("CY", "Cyprus");
        countryLookupMap.put("CZ", "Czech Republic");
        countryLookupMap.put("DE", "Germany");
        countryLookupMap.put("DJ", "Djibouti");
        countryLookupMap.put("DK", "Denmark");
        countryLookupMap.put("DM", "Dominica");
        countryLookupMap.put("DO", "Dominican Republic");
        countryLookupMap.put("DZ", "Algeria");
        countryLookupMap.put("EC", "Ecuador");
        countryLookupMap.put("EE", "Estonia");
        countryLookupMap.put("EG", "Egypt");
        countryLookupMap.put("EH", "Western Sahara");
        countryLookupMap.put("ER", "Eritrea");
        countryLookupMap.put("ES", "Spain");
        countryLookupMap.put("ET", "Ethiopia");
        countryLookupMap.put("FI", "Finland");
        countryLookupMap.put("FJ", "Fiji Islands");
        countryLookupMap.put("FK", "Falkland Islands");
        countryLookupMap.put("FM", "Micronesia, Federated States of");
        countryLookupMap.put("FO", "Faroe Islands");
        countryLookupMap.put("FR", "France");
        countryLookupMap.put("GA", "Gabon");
        countryLookupMap.put("GB", "United Kingdom");
        countryLookupMap.put("GD", "Grenada");
        countryLookupMap.put("GE", "Georgia");
        countryLookupMap.put("GF", "French Guiana");
        countryLookupMap.put("GH", "Ghana");
        countryLookupMap.put("GI", "Gibraltar");
        countryLookupMap.put("GL", "Greenland");
        countryLookupMap.put("GM", "Gambia");
        countryLookupMap.put("GN", "Guinea");
        countryLookupMap.put("GP", "Guadeloupe");
        countryLookupMap.put("GQ", "Equatorial Guinea");
        countryLookupMap.put("GR", "Greece");
        countryLookupMap.put("GS", "South Georgia and the South Sandwich Islands");
        countryLookupMap.put("GT", "Guatemala");
        countryLookupMap.put("GU", "Guam");
        countryLookupMap.put("GW", "Guinea-Bissau");
        countryLookupMap.put("GY", "Guyana");
        countryLookupMap.put("HK", "Hong Kong");
        countryLookupMap.put("HM", "Heard Island and McDonald Islands");
        countryLookupMap.put("HN", "Honduras");
        countryLookupMap.put("HR", "Croatia");
        countryLookupMap.put("HT", "Haiti");
        countryLookupMap.put("HU", "Hungary");
        countryLookupMap.put("ID", "Indonesia");
        countryLookupMap.put("IE", "Ireland");
        countryLookupMap.put("IL", "Israel");
        countryLookupMap.put("IN", "India");
        countryLookupMap.put("IO", "British Indian Ocean Territory");
        countryLookupMap.put("IQ", "Iraq");
        countryLookupMap.put("IR", "Iran");
        countryLookupMap.put("IS", "Iceland");
        countryLookupMap.put("IT", "Italy");
        countryLookupMap.put("JM", "Jamaica");
        countryLookupMap.put("JO", "Jordan");
        countryLookupMap.put("JP", "Japan");
        countryLookupMap.put("KE", "Kenya");
        countryLookupMap.put("KG", "Kyrgyzstan");
        countryLookupMap.put("KH", "Cambodia");
        countryLookupMap.put("KI", "Kiribati");
        countryLookupMap.put("KM", "Comoros");
        countryLookupMap.put("KN", "Saint Kitts and Nevis");
        countryLookupMap.put("KP", "North Korea");
        countryLookupMap.put("KR", "South Korea");
        countryLookupMap.put("KW", "Kuwait");
        countryLookupMap.put("KY", "Cayman Islands");
        countryLookupMap.put("KZ", "Kazakstan");
        countryLookupMap.put("LA", "Laos");
        countryLookupMap.put("LB", "Lebanon");
        countryLookupMap.put("LC", "Saint Lucia");
        countryLookupMap.put("LI", "Liechtenstein");
        countryLookupMap.put("LK", "Sri Lanka");
        countryLookupMap.put("LR", "Liberia");
        countryLookupMap.put("LS", "Lesotho");
        countryLookupMap.put("LT", "Lithuania");
        countryLookupMap.put("LU", "Luxembourg");
        countryLookupMap.put("LV", "Latvia");
        countryLookupMap.put("LY", "Libyan Arab Jamahiriya");
        countryLookupMap.put("MA", "Morocco");
        countryLookupMap.put("MC", "Monaco");
        countryLookupMap.put("MD", "Moldova");
        countryLookupMap.put("MG", "Madagascar");
        countryLookupMap.put("MH", "Marshall Islands");
        countryLookupMap.put("MK", "Macedonia");
        countryLookupMap.put("ML", "Mali");
        countryLookupMap.put("MM", "Myanmar");
        countryLookupMap.put("MN", "Mongolia");
        countryLookupMap.put("MO", "Macao");
        countryLookupMap.put("MP", "Northern Mariana Islands");
        countryLookupMap.put("MQ", "Martinique");
        countryLookupMap.put("MR", "Mauritania");
        countryLookupMap.put("MS", "Montserrat");
        countryLookupMap.put("MT", "Malta");
        countryLookupMap.put("MU", "Mauritius");
        countryLookupMap.put("MV", "Maldives");
        countryLookupMap.put("MW", "Malawi");
        countryLookupMap.put("MX", "Mexico");
        countryLookupMap.put("MY", "Malaysia");
        countryLookupMap.put("MZ", "Mozambique");
        countryLookupMap.put("NA", "Namibia");
        countryLookupMap.put("NC", "New Caledonia");
        countryLookupMap.put("NE", "Niger");
        countryLookupMap.put("NF", "Norfolk Island");
        countryLookupMap.put("NG", "Nigeria");
        countryLookupMap.put("NI", "Nicaragua");
        countryLookupMap.put("NL", "Netherlands");
        countryLookupMap.put("NO", "Norway");
        countryLookupMap.put("NP", "Nepal");
        countryLookupMap.put("NR", "Nauru");
        countryLookupMap.put("NU", "Niue");
        countryLookupMap.put("NZ", "New Zealand");
        countryLookupMap.put("OM", "Oman");
        countryLookupMap.put("PA", "Panama");
        countryLookupMap.put("PE", "Peru");
        countryLookupMap.put("PF", "French Polynesia");
        countryLookupMap.put("PG", "Papua New Guinea");
        countryLookupMap.put("PH", "Philippines");
        countryLookupMap.put("PK", "Pakistan");
        countryLookupMap.put("PL", "Poland");
        countryLookupMap.put("PM", "Saint Pierre and Miquelon");
        countryLookupMap.put("PN", "Pitcairn");
        countryLookupMap.put("PR", "Puerto Rico");
        countryLookupMap.put("PS", "Palestine");
        countryLookupMap.put("PT", "Portugal");
        countryLookupMap.put("PW", "Palau");
        countryLookupMap.put("PY", "Paraguay");
        countryLookupMap.put("QA", "Qatar");
        countryLookupMap.put("RE", "Réunion");
        countryLookupMap.put("RO", "Romania");
        countryLookupMap.put("RU", "Russian Federation");
        countryLookupMap.put("RW", "Rwanda");
        countryLookupMap.put("SA", "Saudi Arabia");
        countryLookupMap.put("SB", "Solomon Islands");
        countryLookupMap.put("SC", "Seychelles");
        countryLookupMap.put("SD", "Sudan");
        countryLookupMap.put("SE", "Sweden");
        countryLookupMap.put("SG", "Singapore");
        countryLookupMap.put("SH", "Saint Helena");
        countryLookupMap.put("SI", "Slovenia");
        countryLookupMap.put("SJ", "Svalbard and Jan Mayen");
        countryLookupMap.put("SK", "Slovakia");
        countryLookupMap.put("SL", "Sierra Leone");
        countryLookupMap.put("SM", "San Marino");
        countryLookupMap.put("SN", "Senegal");
        countryLookupMap.put("SO", "Somalia");
        countryLookupMap.put("SR", "Suriname");
        countryLookupMap.put("ST", "Sao Tome and Principe");
        countryLookupMap.put("SV", "El Salvador");
        countryLookupMap.put("SY", "Syria");
        countryLookupMap.put("SZ", "Swaziland");
        countryLookupMap.put("TC", "Turks and Caicos Islands");
        countryLookupMap.put("TD", "Chad");
        countryLookupMap.put("TF", "French Southern territories");
        countryLookupMap.put("TG", "Togo");
        countryLookupMap.put("TH", "Thailand");
        countryLookupMap.put("TJ", "Tajikistan");
        countryLookupMap.put("TK", "Tokelau");
        countryLookupMap.put("TM", "Turkmenistan");
        countryLookupMap.put("TN", "Tunisia");
        countryLookupMap.put("TO", "Tonga");
        countryLookupMap.put("TP", "East Timor");
        countryLookupMap.put("TR", "Turkey");
        countryLookupMap.put("TT", "Trinidad and Tobago");
        countryLookupMap.put("TV", "Tuvalu");
        countryLookupMap.put("TW", "Taiwan");
        countryLookupMap.put("TZ", "Tanzania");
        countryLookupMap.put("UA", "Ukraine");
        countryLookupMap.put("UG", "Uganda");
        countryLookupMap.put("UM", "United States Minor Outlying Islands");
        countryLookupMap.put("US", "United States");
        countryLookupMap.put("UY", "Uruguay");
        countryLookupMap.put("UZ", "Uzbekistan");
        countryLookupMap.put("VA", "Holy See (Vatican City State)");
        countryLookupMap.put("VC", "Saint Vincent and the Grenadines");
        countryLookupMap.put("VE", "Venezuela");
        countryLookupMap.put("VG", "Virgin Islands, British");
        countryLookupMap.put("VI", "Virgin Islands, U.S.");
        countryLookupMap.put("VN", "Vietnam");
        countryLookupMap.put("VU", "Vanuatu");
        countryLookupMap.put("WF", "Wallis and Futuna");
        countryLookupMap.put("WS", "Samoa");
        countryLookupMap.put("YE", "Yemen");
        countryLookupMap.put("YT", "Mayotte");
        countryLookupMap.put("YU", "Yugoslavia");
        countryLookupMap.put("ZA", "South Africa");
        countryLookupMap.put("ZM", "Zambia");
        countryLookupMap.put("ZW", "Zimbabwe");

        /*
        The country latitude and longitude data are obtained from
        https://developers.google.com/public-data/docs/canonical/countries_csv
        
        Initialize the HashMap for 244 countries
        KEY = Country Code, VALUE = Country's Latitude and Longitude
         */
        countryLookupLatLong.put("AD", "42.546245, 1.601554");
        countryLookupLatLong.put("AE", "23.424076, 53.847818");
        countryLookupLatLong.put("AF", "33.93911, 67.709953");
        countryLookupLatLong.put("AG", "17.060816, -61.796428");
        countryLookupLatLong.put("AI", "18.220554, -63.068615");
        countryLookupLatLong.put("AL", "41.153332, 20.168331");
        countryLookupLatLong.put("AM", "40.069099, 45.038189");
        countryLookupLatLong.put("AN", "12.226079, -69.060087");
        countryLookupLatLong.put("AO", "-11.202692, 17.873887");
        countryLookupLatLong.put("AQ", "-75.250973, -0.071389");
        countryLookupLatLong.put("AR", "-38.416097, -63.616672");
        countryLookupLatLong.put("AS", "-14.270972, -170.132217");
        countryLookupLatLong.put("AT", "47.516231, 14.550072");
        countryLookupLatLong.put("AU", "-25.274398, 133.775136");
        countryLookupLatLong.put("AW", "12.52111, -69.968338");
        countryLookupLatLong.put("AZ", "40.143105, 47.576927");
        countryLookupLatLong.put("BA", "43.915886, 17.679076");
        countryLookupLatLong.put("BB", "13.193887, -59.543198");
        countryLookupLatLong.put("BD", "23.684994, 90.356331");
        countryLookupLatLong.put("BE", "50.503887, 4.469936");
        countryLookupLatLong.put("BF", "12.238333, -1.561593");
        countryLookupLatLong.put("BG", "42.733883, 25.48583");
        countryLookupLatLong.put("BH", "25.930414, 50.637772");
        countryLookupLatLong.put("BI", "-3.373056, 29.918886");
        countryLookupLatLong.put("BJ", "9.30769, 2.315834");
        countryLookupLatLong.put("BM", "32.321384, -64.75737");
        countryLookupLatLong.put("BN", "4.535277, 114.727669");
        countryLookupLatLong.put("BO", "-16.290154, -63.588653");
        countryLookupLatLong.put("BR", "-14.235004, -51.92528");
        countryLookupLatLong.put("BS", "25.03428, -77.39628");
        countryLookupLatLong.put("BT", "27.514162, 90.433601");
        countryLookupLatLong.put("BV", "-54.423199, 3.413194");
        countryLookupLatLong.put("BW", "-22.328474, 24.684866");
        countryLookupLatLong.put("BY", "53.709807, 27.953389");
        countryLookupLatLong.put("BZ", "17.189877, -88.49765");
        countryLookupLatLong.put("CA", "56.130366, -106.346771");
        countryLookupLatLong.put("CC", "-12.164165, 96.870956");
        countryLookupLatLong.put("CD", "-4.038333, 21.758664");
        countryLookupLatLong.put("CF", "6.611111, 20.939444");
        countryLookupLatLong.put("CG", "-0.228021, 15.827659");
        countryLookupLatLong.put("CH", "46.818188, 8.227512");
        countryLookupLatLong.put("CI", "7.539989, -5.54708");
        countryLookupLatLong.put("CK", "-21.236736, -159.777671");
        countryLookupLatLong.put("CL", "-35.675147, -71.542969");
        countryLookupLatLong.put("CM", "7.369722, 12.354722");
        countryLookupLatLong.put("CN", "35.86166, 104.195397");
        countryLookupLatLong.put("CO", "4.570868, -74.297333");
        countryLookupLatLong.put("CR", "9.748917, -83.753428");
        countryLookupLatLong.put("CU", "21.521757, -77.781167");
        countryLookupLatLong.put("CV", "16.002082, -24.013197");
        countryLookupLatLong.put("CX", "-10.447525, 105.690449");
        countryLookupLatLong.put("CY", "35.126413, 33.429859");
        countryLookupLatLong.put("CZ", "49.817492, 15.472962");
        countryLookupLatLong.put("DE", "51.165691, 10.451526");
        countryLookupLatLong.put("DJ", "11.825138, 42.590275");
        countryLookupLatLong.put("DK", "56.26392, 9.501785");
        countryLookupLatLong.put("DM", "15.414999, -61.370976");
        countryLookupLatLong.put("DO", "18.735693, -70.162651");
        countryLookupLatLong.put("DZ", "28.033886, 1.659626");
        countryLookupLatLong.put("EC", "-1.831239, -78.183406");
        countryLookupLatLong.put("EE", "58.595272, 25.013607");
        countryLookupLatLong.put("EG", "26.820553, 30.802498");
        countryLookupLatLong.put("EH", "24.215527, -12.885834");
        countryLookupLatLong.put("ER", "15.179384, 39.782334");
        countryLookupLatLong.put("ES", "40.463667, -3.74922");
        countryLookupLatLong.put("ET", "9.145, 40.489673");
        countryLookupLatLong.put("FI", "61.92411, 25.748151");
        countryLookupLatLong.put("FJ", "-16.578193, 179.414413");
        countryLookupLatLong.put("FK", "-51.796253, -59.523613");
        countryLookupLatLong.put("FM", "7.425554, 150.550812");
        countryLookupLatLong.put("FO", "61.892635, -6.911806");
        countryLookupLatLong.put("FR", "46.227638, 2.213749");
        countryLookupLatLong.put("GA", "-0.803689, 11.609444");
        countryLookupLatLong.put("GB", "55.378051, -3.435973");
        countryLookupLatLong.put("GD", "12.262776, -61.604171");
        countryLookupLatLong.put("GE", "42.315407, 43.356892");
        countryLookupLatLong.put("GF", "3.933889, -53.125782");
        countryLookupLatLong.put("GG", "49.465691, -2.585278");
        countryLookupLatLong.put("GH", "7.946527, -1.023194");
        countryLookupLatLong.put("GI", "36.137741, -5.345374");
        countryLookupLatLong.put("GL", "71.706936, -42.604303");
        countryLookupLatLong.put("GM", "13.443182, -15.310139");
        countryLookupLatLong.put("GN", "9.945587, -9.696645");
        countryLookupLatLong.put("GP", "16.995971, -62.067641");
        countryLookupLatLong.put("GQ", "1.650801, 10.267895");
        countryLookupLatLong.put("GR", "39.074208, 21.824312");
        countryLookupLatLong.put("GS", "-54.429579, -36.587909");
        countryLookupLatLong.put("GT", "15.783471, -90.230759");
        countryLookupLatLong.put("GU", "13.444304, 144.793731");
        countryLookupLatLong.put("GW", "11.803749, -15.180413");
        countryLookupLatLong.put("GY", "4.860416, -58.93018");
        countryLookupLatLong.put("GZ", "31.354676, 34.308825");
        countryLookupLatLong.put("HK", "22.396428, 114.109497");
        countryLookupLatLong.put("HM", "-53.08181, 73.504158");
        countryLookupLatLong.put("HN", "15.199999, -86.241905");
        countryLookupLatLong.put("HR", "45.1, 15.2");
        countryLookupLatLong.put("HT", "18.971187, -72.285215");
        countryLookupLatLong.put("HU", "47.162494, 19.503304");
        countryLookupLatLong.put("ID", "-0.789275, 113.921327");
        countryLookupLatLong.put("IE", "53.41291, -8.24389");
        countryLookupLatLong.put("IL", "31.046051, 34.851612");
        countryLookupLatLong.put("IM", "54.236107, -4.548056");
        countryLookupLatLong.put("IN", "20.593684, 78.96288");
        countryLookupLatLong.put("IO", "-6.343194, 71.876519");
        countryLookupLatLong.put("IQ", "33.223191, 43.679291");
        countryLookupLatLong.put("IR", "32.427908, 53.688046");
        countryLookupLatLong.put("IS", "64.963051, -19.020835");
        countryLookupLatLong.put("IT", "41.87194, 12.56738");
        countryLookupLatLong.put("JE", "49.214439, -2.13125");
        countryLookupLatLong.put("JM", "18.109581, -77.297508");
        countryLookupLatLong.put("JO", "30.585164, 36.238414");
        countryLookupLatLong.put("JP", "36.204824, 138.252924");
        countryLookupLatLong.put("KE", "-0.023559, 37.906193");
        countryLookupLatLong.put("KG", "41.20438, 74.766098");
        countryLookupLatLong.put("KH", "12.565679, 104.990963");
        countryLookupLatLong.put("KI", "-3.370417, -168.734039");
        countryLookupLatLong.put("KM", "-11.875001, 43.872219");
        countryLookupLatLong.put("KN", "17.357822, -62.782998");
        countryLookupLatLong.put("KP", "40.339852, 127.510093");
        countryLookupLatLong.put("KR", "35.907757, 127.766922");
        countryLookupLatLong.put("KW", "29.31166, 47.481766");
        countryLookupLatLong.put("KY", "19.513469, -80.566956");
        countryLookupLatLong.put("KZ", "48.019573, 66.923684");
        countryLookupLatLong.put("LA", "19.85627, 102.495496");
        countryLookupLatLong.put("LB", "33.854721, 35.862285");
        countryLookupLatLong.put("LC", "13.909444, -60.978893");
        countryLookupLatLong.put("LI", "47.166, 9.555373");
        countryLookupLatLong.put("LK", "7.873054, 80.771797");
        countryLookupLatLong.put("LR", "6.428055, -9.429499");
        countryLookupLatLong.put("LS", "-29.609988, 28.233608");
        countryLookupLatLong.put("LT", "55.169438, 23.881275");
        countryLookupLatLong.put("LU", "49.815273, 6.129583");
        countryLookupLatLong.put("LV", "56.879635, 24.603189");
        countryLookupLatLong.put("LY", "26.3351, 17.228331");
        countryLookupLatLong.put("MA", "31.791702, -7.09262");
        countryLookupLatLong.put("MC", "43.750298, 7.412841");
        countryLookupLatLong.put("MD", "47.411631, 28.369885");
        countryLookupLatLong.put("ME", "42.708678, 19.37439");
        countryLookupLatLong.put("MG", "-18.766947, 46.869107");
        countryLookupLatLong.put("MH", "7.131474, 171.184478");
        countryLookupLatLong.put("MK", "41.608635, 21.745275");
        countryLookupLatLong.put("ML", "17.570692, -3.996166");
        countryLookupLatLong.put("MM", "21.913965, 95.956223");
        countryLookupLatLong.put("MN", "46.862496, 103.846656");
        countryLookupLatLong.put("MO", "22.198745, 113.543873");
        countryLookupLatLong.put("MP", "17.33083, 145.38469");
        countryLookupLatLong.put("MQ", "14.641528, -61.024174");
        countryLookupLatLong.put("MR", "21.00789, -10.940835");
        countryLookupLatLong.put("MS", "16.742498, -62.187366");
        countryLookupLatLong.put("MT", "35.937496, 14.375416");
        countryLookupLatLong.put("MU", "-20.348404, 57.552152");
        countryLookupLatLong.put("MV", "3.202778, 73.22068");
        countryLookupLatLong.put("MW", "-13.254308, 34.301525");
        countryLookupLatLong.put("MX", "23.634501, -102.552784");
        countryLookupLatLong.put("MY", "4.210484, 101.975766");
        countryLookupLatLong.put("MZ", "-18.665695, 35.529562");
        countryLookupLatLong.put("NA", "-22.95764, 18.49041");
        countryLookupLatLong.put("NC", "-20.904305, 165.618042");
        countryLookupLatLong.put("NE", "17.607789, 8.081666");
        countryLookupLatLong.put("NF", "-29.040835, 167.954712");
        countryLookupLatLong.put("NG", "9.081999, 8.675277");
        countryLookupLatLong.put("NI", "12.865416, -85.207229");
        countryLookupLatLong.put("NL", "52.132633, 5.291266");
        countryLookupLatLong.put("NO", "60.472024, 8.468946");
        countryLookupLatLong.put("NP", "28.394857, 84.124008");
        countryLookupLatLong.put("NR", "-0.522778, 166.931503");
        countryLookupLatLong.put("NU", "-19.054445, -169.867233");
        countryLookupLatLong.put("NZ", "-40.900557, 174.885971");
        countryLookupLatLong.put("OM", "21.512583, 55.923255");
        countryLookupLatLong.put("PA", "8.537981, -80.782127");
        countryLookupLatLong.put("PE", "-9.189967, -75.015152");
        countryLookupLatLong.put("PF", "-17.679742, -149.406843");
        countryLookupLatLong.put("PG", "-6.314993, 143.95555");
        countryLookupLatLong.put("PH", "12.879721, 121.774017");
        countryLookupLatLong.put("PK", "30.375321, 69.345116");
        countryLookupLatLong.put("PL", "51.919438, 19.145136");
        countryLookupLatLong.put("PM", "46.941936, -56.27111");
        countryLookupLatLong.put("PN", "-24.703615, -127.439308");
        countryLookupLatLong.put("PR", "18.220833, -66.590149");
        countryLookupLatLong.put("PS", "31.952162, 35.233154");
        countryLookupLatLong.put("PT", "39.399872, -8.224454");
        countryLookupLatLong.put("PW", "7.51498, 134.58252");
        countryLookupLatLong.put("PY", "-23.442503, -58.443832");
        countryLookupLatLong.put("QA", "25.354826, 51.183884");
        countryLookupLatLong.put("RE", "-21.115141, 55.536384");
        countryLookupLatLong.put("RO", "45.943161, 24.96676");
        countryLookupLatLong.put("RS", "44.016521, 21.005859");
        countryLookupLatLong.put("RU", "61.52401, 105.318756");
        countryLookupLatLong.put("RW", "-1.940278, 29.873888");
        countryLookupLatLong.put("SA", "23.885942, 45.079162");
        countryLookupLatLong.put("SB", "-9.64571, 160.156194");
        countryLookupLatLong.put("SC", "-4.679574, 55.491977");
        countryLookupLatLong.put("SD", "12.862807, 30.217636");
        countryLookupLatLong.put("SE", "60.128161, 18.643501");
        countryLookupLatLong.put("SG", "1.352083, 103.819836");
        countryLookupLatLong.put("SH", "-24.143474, -10.030696");
        countryLookupLatLong.put("SI", "46.151241, 14.995463");
        countryLookupLatLong.put("SJ", "77.553604, 23.670272");
        countryLookupLatLong.put("SK", "48.669026, 19.699024");
        countryLookupLatLong.put("SL", "8.460555, -11.779889");
        countryLookupLatLong.put("SM", "43.94236, 12.457777");
        countryLookupLatLong.put("SN", "14.497401, -14.452362");
        countryLookupLatLong.put("SO", "5.152149, 46.199616");
        countryLookupLatLong.put("SR", "3.919305, -56.027783");
        countryLookupLatLong.put("ST", "0.18636, 6.613081");
        countryLookupLatLong.put("SV", "13.794185, -88.89653");
        countryLookupLatLong.put("SY", "34.802075, 38.996815");
        countryLookupLatLong.put("SZ", "-26.522503, 31.465866");
        countryLookupLatLong.put("TC", "21.694025, -71.797928");
        countryLookupLatLong.put("TD", "15.454166, 18.732207");
        countryLookupLatLong.put("TF", "-49.280366, 69.348557");
        countryLookupLatLong.put("TG", "8.619543, 0.824782");
        countryLookupLatLong.put("TH", "15.870032, 100.992541");
        countryLookupLatLong.put("TJ", "38.861034, 71.276093");
        countryLookupLatLong.put("TK", "-8.967363, -171.855881");
        countryLookupLatLong.put("TL", "-8.874217, 125.727539");
        countryLookupLatLong.put("TM", "38.969719, 59.556278");
        countryLookupLatLong.put("TN", "33.886917, 9.537499");
        countryLookupLatLong.put("TO", "-21.178986, -175.198242");
        countryLookupLatLong.put("TR", "38.963745, 35.243322");
        countryLookupLatLong.put("TT", "10.691803, -61.222503");
        countryLookupLatLong.put("TV", "-7.109535, 177.64933");
        countryLookupLatLong.put("TW", "23.69781, 120.960515");
        countryLookupLatLong.put("TZ", "-6.369028, 34.888822");
        countryLookupLatLong.put("UA", "48.379433, 31.16558");
        countryLookupLatLong.put("UG", "1.373333, 32.290275");
        countryLookupLatLong.put("US", "37.09024, -95.712891");
        countryLookupLatLong.put("UY", "-32.522779, -55.765835");
        countryLookupLatLong.put("UZ", "41.377491, 64.585262");
        countryLookupLatLong.put("VA", "41.902916, 12.453389");
        countryLookupLatLong.put("VC", "12.984305, -61.287228");
        countryLookupLatLong.put("VE", "6.42375, -66.58973");
        countryLookupLatLong.put("VG", "18.420695, -64.639968");
        countryLookupLatLong.put("VI", "18.335765, -64.896335");
        countryLookupLatLong.put("VN", "14.058324, 108.277199");
        countryLookupLatLong.put("VU", "-15.376706, 166.959158");
        countryLookupLatLong.put("WF", "-13.768752, -177.156097");
        countryLookupLatLong.put("WS", "-13.759029, -172.104629");
        countryLookupLatLong.put("XK", "42.602636, 20.902977");
        countryLookupLatLong.put("YE", "15.552727, 48.516388");
        countryLookupLatLong.put("YT", "-12.8275, 45.166244");
        countryLookupLatLong.put("ZA", "-30.559482, 22.937506");
        countryLookupLatLong.put("ZM", "-13.133897, 27.849332");
        countryLookupLatLong.put("ZW", "-19.015438, 29.154857");
    }

    /*
    =========================
    Getter and Setter Methods
    =========================
     */
    public CountryFacade getCountryFacade() {
        return countryFacade;
    }

    public void setCountryFacade(CountryFacade countryFacade) {
        this.countryFacade = countryFacade;
    }

    public Country getSelected() {
        return selected;
    }

    public void setSelected(Country selected) {
        this.selected = selected;
    }

    public List<Country> getItems() {
        if (items == null) {
            items = getCountryFacade().findAll();
        }
        return items;
    }

    public List<String> getFlagNames() {
        return flagNames;
    }

    public Boolean getCountryDataChanged() {
        return countryDataChanged;
    }

    public void setCountryDataChanged(Boolean countryDataChanged) {
        this.countryDataChanged = countryDataChanged;
    }

    /*
    ================
    Instance Methods
    ================
    
    ***************************************************
    Are All New Country Attributes Entered by the User?
    ***************************************************
     */
    public boolean allCountryAttributesEntered() {

        if (selected != null) {
            return (!selected.getName().isEmpty()
                    && !selected.getCode().isEmpty()
                    && selected.getPopulation() != 0
                    && selected.getTotalArea() != 0
                    && !selected.getLanguage().isEmpty()
                    && !selected.getCurrency().isEmpty());
        } else {
            return false;
        }
    }

    /**
     * Given the flag name (= country code), return the country name
     *
     * @param nameOfFlag
     * @return country name of the flag
     */
    public String countryNameOfFlag(String nameOfFlag) {

        // Flag Name = Country Code
        String flagNameInUpperCase = nameOfFlag.toUpperCase();
        String nameOfCountry = countryLookupMap.get(flagNameInUpperCase);
        return nameOfCountry;
    }

    /**
     * Given the selected country's code, return its Latitude and Longitude
     *
     * @return Country Latitude and Longitude, e.g., "41.377491, 64.585262"
     */
    public String getCountryLatLong() {

        if (!allCountryAttributesEntered()) {
            selected = null;
        }
        /*
         Lazy Loading causes the execution of the statements below when the user
         selects the 'Country List' menu option in the header. Therefore, 'selected' will
         be null during lazy loading since the user has not yet made a selection.
         */

        // Initialize country Latitude and Longitude
        String countryLatLong = "";

        if (selected != null) {
            String countryCode = selected.getCode();
            String countryCodeInCaps = countryCode.toUpperCase();

            if (countryLookupLatLong.containsKey(countryCodeInCaps)) {
                // Success: country code is in the HashMap
                countryLatLong = countryLookupLatLong.get(countryCodeInCaps);
                return countryLatLong;
            } else {
                // Error: given country code is not in the HashMap
                Methods.showMessage("Error", "Latitude and longitude unavailable for the given country code!", "");
            }
        }

        return countryLatLong;
    }

    /**
     * Determine the map zoom level for the given country total area
     *
     * @param countryTotalArea Given country total area
     * @return Country Google map zoom level as a String
     */
    public String getZoomLevel(int countryTotalArea) {

        // Country Google Map Zoom Level
        String mapZoomLevel = "1";

        // Adjust map zoom level depending on countryTotalArea in square miles
        if (countryTotalArea < 1000) {
            mapZoomLevel = "11";
        } else if (countryTotalArea < 2000) {
            mapZoomLevel = "10";
        } else if (countryTotalArea < 4000) {
            mapZoomLevel = "9";
        } else if (countryTotalArea < 15000) {
            mapZoomLevel = "8";
        } else if (countryTotalArea < 70000) {
            mapZoomLevel = "7";
        } else if (countryTotalArea < 300000) {
            mapZoomLevel = "6";
        } else if (countryTotalArea < 1000000) {
            mapZoomLevel = "5";
        } else if (countryTotalArea < 4000000) {
            mapZoomLevel = "4";
        } else if (countryTotalArea < 6500000) {
            mapZoomLevel = "3";
        } else if (countryTotalArea < 7000000) {
            mapZoomLevel = "2";
        }

        return mapZoomLevel;
    }

    /*
    *******************************
    Prepare to Create a New Country
    *******************************
     */
    public Country prepareCreate() {

        // Create a Country object as selected
        selected = new Country();

        // Initialize newly created Country object
        selected.setName("");
        selected.setCode("");
        selected.setCapitalCity("");
        selected.setPopulation(null);
        selected.setTotalArea(null);
        selected.setLanguage("");
        selected.setCurrency("");

        return selected;
    }

    /*
     ****************
     *   Unselect   *
     ****************
     */
    public void unselect() {
        selected = null;
    }

    /*
     ******************************************************
     *   Cancel to Display List.xhtml JSF Facelets Page   *
     ******************************************************
     */
    public String cancel() {
        // Unselect previously selected company if any
        selected = null;
        return "/country/List?faces-redirect=true";
    }

    /*
    ************************************
    CREATE a New Country in the Database
    ************************************
     */
    public void create() {

        if (!allCountryAttributesEntered()) {
            selected = null;
            return;
        }

        /*
         --------------------------------------
         Validate Country Code and Country Name
         --------------------------------------
         */
        String countryCodeInUpperCase = selected.getCode().toUpperCase();

        if (countryLookupMap.containsKey(countryCodeInUpperCase)) {
            // Country code is in the HashMap
            String countryNameInHashMap = countryLookupMap.get(countryCodeInUpperCase);

            // Country name entered by the user
            String countryNameEntered = selected.getName();

            if (countryNameInHashMap.equals(countryNameEntered)) {
                // Success: Country name and country code match
            } else {
                Methods.showMessage("Error", "Country name does not match the given country code!", "");
                return;
            }
        } else {
            Methods.showMessage("Error", "Country code is not recognized!", "");
            return;
        }

        /*
         ------------------------------------
         It is okay to create the new country
         ------------------------------------

         We need to preserve the messages since we will redirect to show a
         different JSF page after successful creation of the country.
         */
        Methods.preserveMessages();
        /*
        Prevent displaying the same msg twice, one for Summary and one for Detail, by setting the 
        message Detail to "" in the addSuccessMessage(String msg) method in the jsfUtil.java file.
         */
        persist(PersistAction.CREATE, "Country was Successfully Created!");
        if (!JsfUtil.isValidationFailed()) {
            // No JSF validation error. The CREATE operation is successfully performed.
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
            countryDataChanged = true;
        }
    }

    /*
    ***************************************
    UPDATE Selected Country in the Database
    ***************************************
     */
    public void update() {
        Methods.preserveMessages();
        persist(PersistAction.UPDATE, "Country was Successfully Updated!");
        if (!JsfUtil.isValidationFailed()) {
            // No JSF validation error. The UPDATE operation is successfully performed.
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
            countryDataChanged = true;
        }
    }

    /*
    *****************************************
    DELETE Selected Country from the Database
    *****************************************
     */
    public void destroy() {
        Methods.preserveMessages();
        persist(PersistAction.DELETE, "Country was Successfully Deleted!");
        if (!JsfUtil.isValidationFailed()) {
            // No JSF validation error. The DELETE operation is successfully performed.
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
            countryDataChanged = true;
        }
    }

    /*
     ****************************************************************************
     *   Perform CREATE, EDIT (UPDATE), and DELETE Operations in the Database   *
     ****************************************************************************
     */
    /**
     * @param persistAction refers to CREATE, UPDATE (Edit) or DELETE action
     * @param successMessage displayed to inform the user about the result
     */
    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            try {
                if (persistAction != PersistAction.DELETE) {
                    /*
                     -------------------------------------------------
                     Perform CREATE or EDIT operation in the database.
                     -------------------------------------------------
                     The edit(selected) method performs the SAVE (STORE) operation of the "selected"
                     object in the database regardless of whether the object is a newly
                     created object (CREATE) or an edited (updated) object (EDIT or UPDATE).
                    
                     CountryFacade inherits the edit(selected) method from the AbstractFacade class.
                     */
                    getCountryFacade().edit(selected);
                } else {
                    /*
                     -----------------------------------------
                     Perform DELETE operation in the database.
                     -----------------------------------------
                     The remove method performs the DELETE operation in the database.
                    
                     CountryFacade inherits the remove(selected) method from the AbstractFacade class.
                     */
                    getCountryFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, "A persistence error occurred!");
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, "A persistence error occurred");
            }
        }
    }

    /*
    ************************************************
    |   Other Auto Generated Methods by NetBeans   |
    ************************************************
     */
    public Country getCountry(Integer id) {
        return getCountryFacade().find(id);
    }

    public List<Country> getItemsAvailableSelectMany() {
        return getCountryFacade().findAll();
    }

    public List<Country> getItemsAvailableSelectOne() {
        return getCountryFacade().findAll();
    }

    @FacesConverter(forClass = Country.class)
    public static class CountryControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CountryController controller = (CountryController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "countryController");
            return controller.getCountry(getKey(value));
        }

        Integer getKey(String value) {
            Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Country) {
                Country o = (Country) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
                        "object {0} is of type {1}; expected type: {2}",
                        new Object[]{object, object.getClass().getName(), Country.class.getName()});
                return null;
            }
        }

    }

}
