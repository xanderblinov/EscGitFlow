package com.esc.common.util.AffiliationSplitter.Core.Interfaces;

import com.esc.common.util.AffiliationSplitter.Core.Models.City;
import com.esc.common.util.AffiliationSplitter.Core.Models.Country;

import java.util.ArrayList;

/**
 * Created by afirsov on 7/10/2016.
 */
public interface ILocationService {
    ArrayList<City> citiesByCountryName(String countryName);
    ArrayList<Country> countriesByCityName(String cityName);
    ArrayList<Country> getAllCountries();
    ArrayList<City> getAlCities();

}
