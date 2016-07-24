package com.esc.common.util.AffiliationSplitter;

import com.esc.common.util.AffiliationSplitter.Core.Interfaces.IAffiliationSplitStrategy;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by afirsov on 7/9/2016.
 */
public class AffliationSplitStrategy implements IAffiliationSplitStrategy {
    private ArrayList<String> indicators = new ArrayList<String>();
    private ILocationService locationService = new LocationService();

    public static void main(String[] args) throws IOException {
        AffliationSplitStrategy strat = new AffliationSplitStrategy();
        strat.Split("Institute of Cytology and Genetics, Russian Academy of Sciences, SiberianDepartment, Novosibirsk, Russia and MRC Clinical Sciences Centre, ImperialCollege of Medicine, Hammersmith Hospital, London, United Kingdom.");
    }

    public AffliationSplitStrategy() throws IOException {
        indicators.add("university");
        indicators.add("institute");
        indicators.add("centre");
        indicators.add("center");
    }
    @Override
    public ArrayList<String> Split(String affiliationName) throws IOException {
        int count = 0;
        affiliationName = affiliationName.toLowerCase();
        ArrayList<String> result = new ArrayList<String>();
        for(int i = 0; i<indicators.size();i++)
        {
            if(affiliationName.contains(indicators.get(i))){
                count ++;
            }
        }

        if(count>1){
            Map<Integer, String> cityIndices = new HashMap<Integer,String>();
            Map<Integer,String> countryIndices = new HashMap<Integer,String>();
            Map<Integer,String> matchIndices = new HashMap<Integer,String>();

            String[] arr = affiliationName.split(",");
            for (int i = 0; i< arr.length;i++) {

                SetLocationIndices(countryIndices,cityIndices,arr,i);

                for (String ind : indicators) {
                    if(arr[i].contains(ind.toLowerCase())){
                        matchIndices.put(i,arr[i].toLowerCase());
                    }
                }
            }

            ArrayList<Integer> city_match = LocateCollisions(cityIndices, matchIndices);
            ArrayList<Integer> country_match = LocateCollisions(countryIndices, matchIndices);

            ArrayList<Integer> union = new ArrayList<Integer>();
            union.addAll(city_match);
            union.addAll(country_match);

            ArrayList<String> newArray = new ArrayList<String>();
            for (Integer elem : union) {

            }
        }
        else{
            result.add(affiliationName);
        }

        return result;
    }

    private void SetLocationIndices(Map<Integer,String>  countryIndices, Map<Integer,String> cityIndices,
                                    String[] source, int currIndex)
    {
        ArrayList<Country> entries = locationService.getAllCountries();
        for (Country entry : entries) {
            String entryKey =  entry.Name.toLowerCase();
            if(entryKey.isEmpty()){
                continue;
            }
            String countryPattern = ".*(\\b" + entryKey + "\\b).*";
            Pattern pattern = Pattern.compile(countryPattern);
            Matcher matcher = pattern.matcher(source[currIndex]);

            if(matcher.matches()){
                countryIndices.put(currIndex, source[currIndex].toLowerCase());
            }

            ArrayList<City> cityEntries = entry.Cities;
            for (int j = 0; j < cityEntries.size(); j++) {
                String cityEntryKey = cityEntries.get(j).Name.toLowerCase();
                if(cityEntryKey.isEmpty()){
                    continue;
                }
                String cityPattern = ".*(\\b" + cityEntryKey + "\\b).*";
                pattern = Pattern.compile(cityPattern);
                matcher = pattern.matcher(source[currIndex]);
                if(matcher.matches()){
                    cityIndices.put(currIndex,source[currIndex].toLowerCase());
                }
            }
        }

    }

    private ArrayList<Integer> LocateCollisions(Map<Integer,String> first,Map<Integer,String> second){
        ArrayList<Integer> result = new ArrayList<Integer>();

        Set<Integer> keysFirst = first.keySet();
        for (Integer key: keysFirst) {
            if(second.containsKey(key)){
                result.add(key);
            }
        }

        return result;
    }
}
