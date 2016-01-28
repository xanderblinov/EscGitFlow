package com.esc.common.SimilarityFunctions;

import com.esc.common.TextUtils;

import java.lang.reflect.Array;
import java.util.HashSet;

/**
 * Created by afirsov on 1/27/2016.
 */
public class Jaccard implements IGetCoefficient{
    public static void main(String[] args)
    {
        //coeff should == 0.8 if ignoreCase
        String s1 = "1234";
        String s2 = "12345";

        double coeff = new Jaccard().GetCoefficient(s1,s2, true);

        System.out.println("Test " + (coeff == 0.8 ? "succeeded":"failed"));
    }

    public float GetCoefficient(String whtaToCheck, String withWhatToCheck, boolean ignoreCase){
        if(ignoreCase){
            whtaToCheck = whtaToCheck.toLowerCase();
            withWhatToCheck = withWhatToCheck.toLowerCase();
        }
        Character[] intersection = TextUtils.GetIntersection(whtaToCheck,withWhatToCheck);
        Character[] union = TextUtils.GetUnion(whtaToCheck,withWhatToCheck);

        return (float)intersection.length/(float)union.length;
    }
}
