package com.esc.common.Modules.GooglePlaces;

import com.esc.common.Modules.GooglePlaces.models.GooglePlacesResponse;
import com.esc.common.Web.HttpSender;
import com.esc.common.Web.Method;
import com.sun.deploy.net.URLEncoder;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;


/**
 * Created by afirsov on 1/27/2016.
 */
public class GooglePlacesAPI extends BaseGooglePlacesAPI {

    public static void main(String[] args) throws Exception {

        BaseGooglePlacesAPI http = new GooglePlacesAPI();

        GooglePlacesResponse response = http.GetPlaceByName("The Pennsylvania State University");

    }

    private final String NameSearchDomain = "https://maps.googleapis.com/maps/api/place/textsearch/json?";

    @Override
    public GooglePlacesResponse GetPlaceByName(String name) throws UnsupportedEncodingException {
        String params = "query=" + URLEncoder.encode(name, "UTF-8") + "&key=" + APIKey;

        String response = null;
        GooglePlacesResponse result = null;

        HttpSender http = new HttpSender();
        try {
            response = http.send(NameSearchDomain + params, Method.GET,null);
            result = new Gson().fromJson(response,GooglePlacesResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
