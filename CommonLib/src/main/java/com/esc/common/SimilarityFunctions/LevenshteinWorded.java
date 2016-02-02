package com.esc.common.SimilarityFunctions;

/**
 * Created by afirsov on 1/27/2016.
 */
public class LevenshteinWorded implements IGetCoefficient{
    public static void main(String[] args)
    {
        //distance should == 1 if ignoreCase
        String[] s1 = "Hello, world!".split("[ ,!]",-1);
        String[] s2 = "Hello,World!".split("[ ,!]",-1);

        int differencies = new LevenshteinWorded().GetDistance(s1,s2, true);

        System.out.println("Test " + (differencies == 1 ? "succeeded":"failed"));
    }

    public int GetDistance(String[] S1, String[] S2, boolean ignoreCase) {
        if(ignoreCase){
            for(int i=0; i < S1.length; i++) {
                S1[i] = S1[i].toLowerCase();
            }
            for(int i=0; i < S2.length; i++) {
                S2[i] = S2[i].toLowerCase();
            }
        }
        int m = S1.length, n = S2.length;
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
                    int cost = S1[i - 1].equals(S2[j - 1]) ? 1 : 0;
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
        double result = GetDistance(whatToCheck.split("[ ,;.]"),withWhatToCheck.split("[ ,;.]"),ignoreCase);

        return 1.0f - (float)result/(float)Math.max(whatToCheck.split("[ ,;.]").length,withWhatToCheck.split("[ ,;.]").length);
    }
}
