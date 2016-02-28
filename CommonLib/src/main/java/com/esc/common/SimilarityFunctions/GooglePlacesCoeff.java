package com.esc.common.SimilarityFunctions;

import com.esc.common.DistanceCalculator;
import com.esc.common.Modules.GooglePlaces.GooglePlacesAPI;
import com.esc.common.Modules.GooglePlaces.models.GooglePlacesResponse;
import com.esc.common.Modules.GooglePlaces.models.Result;
import org.apache.commons.collections4.Get;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by afirsov on 1/28/2016.
 */
public class GooglePlacesCoeff implements IGetCoefficient {

    public GooglePlacesCoeff(IGetCoefficient helperCoeff){
        cache = new HashMap<String, GooglePlacesResponse>();
        api = new GooglePlacesAPI();
        helper = helperCoeff;
    }

    //in kilometers
    public final double MaxRadius = 10;

    private GooglePlacesAPI api;
    private Map<String,GooglePlacesResponse> cache;
    private IGetCoefficient helper;

    public void SetCache(Map<String,GooglePlacesResponse> ca){
        if(ca == null)
        {
            throw new NullPointerException("ca");
        }
        cache = ca;
    }

    public Map<String,GooglePlacesResponse> GetCache(){
        return cache;
    }

    @Override
    public float GetCoefficient(String whatToCheck, String withWhatToCheck, boolean ignoreCase) {
        String toCheckText = !ignoreCase ? whatToCheck : whatToCheck.toLowerCase();
        String fromCheckText = !ignoreCase ? withWhatToCheck : withWhatToCheck.toLowerCase();

        GooglePlacesResponse toCheck = GetResponse(toCheckText);
        GooglePlacesResponse fromCheck = GetResponse(fromCheckText);

        if(toCheck.results.toArray().length == 0){
            return 0.0f;
        }

        if(toCheck.results.toArray().length > 1){
            return 0.0f;
        }

        if(toCheck.results.toArray().length == 1)
        {
            Result checkedOne = (Result)toCheck.results.toArray()[0];
            Float bestCoeff = 0.0f;
            Float currCoeff = 0.0f;

            for (Result res : fromCheck.results){
                if(res.place_id.equals(checkedOne.place_id)) {
                    return 1.0f;
                }

                /*currCoeff = helper.GetCoefficient(res.name,checkedOne.name,true);
                if(currCoeff > bestCoeff){
                    bestCoeff = currCoeff;
                }

                double lat1 = res.geometry.location.lat;
                double lnt1 = res.geometry.location.lng;
                double lat2 = checkedOne.geometry.location.lat;
                double lnt2 = checkedOne.geometry.location.lng;
                Double calcDistance = DistanceCalculator.distance(lat1, lnt1, lat2, lnt2, "K");
                if(calcDistance < MaxRadius){
                    currCoeff =  1.0f - (float)Math.pow(calcDistance/MaxRadius,2);
                    if(currCoeff > bestCoeff){
                        bestCoeff = currCoeff;
                    }
                }*/
            }

            return bestCoeff;
        }

        return 0.0f;
    }

    private GooglePlacesResponse GetResponse(String searchString){
        GooglePlacesResponse result = null;

        try {
            if (cache.containsKey(searchString)) {
                result = cache.get(searchString);
            } else {
                result = api.GetPlaceByName(searchString);
                cache.put(searchString, result);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
        return result;
    }
}
