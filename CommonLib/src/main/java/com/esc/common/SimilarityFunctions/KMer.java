package com.esc.common.SimilarityFunctions;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by afirsov on 4/6/2016.
 */
public class KMer implements IGetCoefficient {

    public KMer(int merLength){
           MerLength = merLength;
    }

    private int MerLength;

    @Override
    public float GetCoefficient(String whatToCheck, String withWhatToCheck, boolean ignoreCase) {
        try {
            ArrayList<String> arr1 = getKmers(whatToCheck);
            ArrayList<String> arr2 = getKmers(withWhatToCheck);
            int result1 = getScore(arr1, withWhatToCheck);
            int result2 = getScore(arr2, whatToCheck);

            return Math.max(result1,result2);
        } catch (InvalidArgumentException e) {
            return 0;
        }
    }

    private ArrayList<String> getKmers(String seq) throws InvalidArgumentException {
        ArrayList<String> result = new ArrayList<String>();
        int seqLength = seq.length();

        if(seqLength > MerLength)
        {
            for(int i = 0; i < seqLength - MerLength + 1; i++)
            {
                result.add(seq.substring(i,MerLength + i));
            }
        } else
        {
            throw new InvalidArgumentException(new String[]{"seq length must be bigger, than k"});
        }

        return result;
    }

    private int getScore(ArrayList<String> mersList, String toCheck){
        int result = 0;
        for (int i = 0; i < mersList.size(); i++) {
            if(toCheck.contains(mersList.get(i))){
                result ++;
            }
        }
        return result;
    }
}
