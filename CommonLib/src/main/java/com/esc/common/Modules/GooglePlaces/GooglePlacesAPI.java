package com.esc.common.Modules.GooglePlaces;

import com.esc.common.Modules.GooglePlaces.models.GooglePlacesResponse;
import com.esc.common.Web.HttpSender;
import com.esc.common.Web.Method;
import com.sun.deploy.net.URLEncoder;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by afirsov on 1/27/2016.
 */
public class GooglePlacesAPI extends BaseGooglePlacesAPI {

    public static void main(String[] args) throws Exception {

        BaseGooglePlacesAPI http = new GooglePlacesAPI();
        GooglePlacesResponse response = http.GetPlaceByName("Institute of Earth Environment, CAS, Xi'an, 710061, China.");

    }

    public GooglePlacesAPI(){
    }

    private static int RequestPerformed = 0;
    private final int MaxRequestPerSecond = 5;
    private final String NameSearchDomain = "https://maps.googleapis.com/maps/api/place/textsearch/json?";

    @Override
    public GooglePlacesResponse GetPlaceByName(String name) throws UnsupportedEncodingException {
        String params = "query=" + URLEncoder.encode(name, "UTF-8") + "&key=" + APIKey;

        String response = null;
        ArrayList<GooglePlacesResponse> resultArr = new ArrayList<GooglePlacesResponse>();

        boolean tokenPresent = false;
        String tokenParam = "";
        do{
            try {
                HttpSender http = new HttpSender();
                tokenParam = tokenPresent ? "&pagetoken=" + tokenParam: "";
                response = http.send(NameSearchDomain + params + tokenParam, Method.GET,null);
                GooglePlacesResponse resp = new Gson().fromJson(response,GooglePlacesResponse.class);
                resultArr.add(resp);
                if(resp.next_page_token != null) {
                    tokenPresent = true;
                    tokenParam = resp.next_page_token;
                    Thread.sleep(1500);
                }
                else{
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }while(true);


        if(resultArr.toArray().length == 1)
        {
            return resultArr.get(0);
        }

        GooglePlacesResponse result = null;
        result = resultArr.get(0);
        for (int i = 1; i<resultArr.toArray().length;i++) {
            result.results.addAll(((GooglePlacesResponse)resultArr.toArray()[i]).results);
        }

        return result;
    }
}
