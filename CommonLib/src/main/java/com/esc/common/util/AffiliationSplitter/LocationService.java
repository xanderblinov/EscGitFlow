package com.esc.common.util.AffiliationSplitter;

import com.esc.common.util.AffiliationSplitter.Core.Interfaces.ILocationService;
import com.esc.common.util.AffiliationSplitter.Core.Models.City;
import com.esc.common.util.AffiliationSplitter.Core.Models.Country;
import com.esc.common.util.Beautifier.IAffilationBeautifier;
import com.esc.common.util.Beautifier.JustLowercaseWords;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * Created by afirsov on 7/10/2016.
 */
public class LocationService implements ILocationService {
    private static ArrayList<Country> _countries = new ArrayList<Country>();

    public LocationService() throws IOException {
        String fileName = new File("CommonLib/src/main/java/com/esc/common/util/AffiliationSplitter/Data/countries.json").getAbsolutePath();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(fileName)));
        String str = "";
        String line = "";
        IAffilationBeautifier beauty = new JustLowercaseWords();
        while((line = bufferedReader.readLine()) != null) {
            str+=line;
        }
        bufferedReader.close();

        JsonParser parser = new JsonParser();
        JsonElement json = parser.parse(str).getAsJsonObject();
        JsonObject obj = json.getAsJsonObject();

        Set<Map.Entry<String, JsonElement>> entries = obj.entrySet();

        for (Map.Entry<String, JsonElement> entry : entries) {
            Country country = new Country();
            country.Name = entry.getKey();

            JsonArray cityEntries = entry.getValue().getAsJsonArray();
            for (int j = 0; j < cityEntries.size(); j++) {
                City city = new City();
                city.Name = cityEntries.get(j).getAsString();
                country.Cities.add(city);
            }

            _countries.add(country);
        }
    }

    @Override
    public ArrayList<City> citiesByCountryName(String countryName) {
        return _countries.stream().filter(x->x.Name.toLowerCase() == countryName.toLowerCase()).findFirst().get().Cities;
    }

    @Override
    public ArrayList<Country> countriesByCityName(String cityName) {
        return null;
    }

    @Override
    public ArrayList<Country> getAllCountries() {
        return _countries;
    }

    @Override
    public ArrayList<City> getAlCities() {
        return null;
    }
}
