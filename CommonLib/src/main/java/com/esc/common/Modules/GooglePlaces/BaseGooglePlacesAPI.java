package com.esc.common.Modules.GooglePlaces;

import com.esc.common.Modules.GooglePlaces.models.GooglePlacesResponse;

import java.io.UnsupportedEncodingException;

/**
 * Created by afirsov on 1/27/2016.
 */
public abstract class BaseGooglePlacesAPI {
    protected String APIKey = "AIzaSyBMpMIj38PG1F1_B3ynC0AeIq-rlKmWMk0";
    public abstract GooglePlacesResponse GetPlaceByName(String name) throws UnsupportedEncodingException;
}
