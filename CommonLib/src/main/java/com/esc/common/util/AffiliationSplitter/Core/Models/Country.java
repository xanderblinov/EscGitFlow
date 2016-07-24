package com.esc.common.util.AffiliationSplitter.Core.Models;

import java.util.ArrayList;

/**
 * Created by afirsov on 7/10/2016.
 */
public class Country {

    public Country(){
        this.Cities = new ArrayList<City>();
    }
    public String Name;
    public ArrayList<City> Cities;
}
