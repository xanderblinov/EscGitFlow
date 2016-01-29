package com.esc.common.Modules.GooglePlaces.models;

import java.util.List;

/**
 * Created by afirsov on 1/28/2016.
 */
public class GooglePlacesResponse implements java.io.Serializable {
    public List<Object> html_attributions;
    public String next_page_token;
    public List<Result> results;
    public String status;
}

