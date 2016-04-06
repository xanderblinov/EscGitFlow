package com.esc.common.SimilarityFunctions;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.ArrayList;

/**
 * Created by afirsov on 4/6/2016.
 */
public class KMerSmart implements IGetCoefficient {

    public KMerSmart(ArrayList<Integer> merLength){
        MerLengths = merLength;
    }

    private ArrayList<Integer> MerLengths;

    @Override
    public float GetCoefficient(String whatToCheck, String withWhatToCheck, boolean ignoreCase) {
        try {
            ArrayList<String> arr1 = getKmers(whatToCheck,0);
            ArrayList<String> arr2 = getKmers(withWhatToCheck,0);
            int result1 = getScore(arr1, withWhatToCheck);
            int result2 = getScore(arr2, whatToCheck);

            return Math.max(result1,result2);
        } catch (InvalidArgumentException e) {
            return 0;
        }
    }

    private ArrayList<String> getKmers(String seq, int index) throws InvalidArgumentException {
        ArrayList<String> result = new ArrayList<String>();
        int seqLength = seq.length();

        if(seqLength > MerLengths.get(index))
        {
            for(int i = 0; i < seqLength - MerLengths.get(index) + 1; i++)
            {
                result.add(seq.substring(i,MerLengths.get(index) + i));
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
