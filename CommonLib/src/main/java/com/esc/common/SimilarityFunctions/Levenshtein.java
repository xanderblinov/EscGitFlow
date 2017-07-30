package com.esc.common.SimilarityFunctions;

/**
 * Created by afirsov on 1/27/2016.
 */
public class Levenshtein implements IGetCoefficient{
    public static void main(String[] args)
    {
        //distance should == 1 if ignoreCase
        String s1 = "Novosibirsk Institute of Bioorganic Chemistry, Siberian Division of Russian Academy of Sciences, Russia.";
        String s2 = "Novosibirsk Institute of Bioorganic Chemistry, SiberianDivision of Russian Academy of Sciences, Russia.";

        int differencies = new Levenshtein().GetDistance(s1,s2, true);
        System.out.println(differencies);

        System.out.println("Test " + (differencies == 1 ? "succeeded":"failed"));
    }

    public int GetDistance(String S1, String S2, boolean ignoreCase) {
        if(ignoreCase){
            S1 = S1.toLowerCase();
            S2 = S2.toLowerCase();
        }
        int m = S1.length(), n = S2.length();
        int[] D1;
        int[] D2 = new int[n + 1];

        for(int i = 0; i <= n; i ++)
            D2[i] = i;

        for(int i = 1; i <= m; i ++) {
            D1 = D2;
            D2 = new int[n + 1];
            for(int j = 0; j <= n; j ++) {
                if(j == 0) D2[j] = i;
                else {
                    int cost = (S1.charAt(i - 1) != S2.charAt(j - 1)) ? 1 : 0;
                    if(D2[j - 1] < D1[j] && D2[j - 1] < D1[j - 1] + cost)
                        D2[j] = D2[j - 1] + 1;
                    else if(D1[j] < D1[j - 1] + cost)
                        D2[j] = D1[j] + 1;
                    else
                        D2[j] = D1[j - 1] + cost;
                }
            }
        }
        return D2[n];
    }

    @Override
    public float GetCoefficient(String whatToCheck, String withWhatToCheck, boolean ignoreCase) {
        double result = GetDistance(whatToCheck,withWhatToCheck,ignoreCase);

        return 1.0f - (float)result/(float)Math.max(whatToCheck.length(),withWhatToCheck.length());
    }
}
