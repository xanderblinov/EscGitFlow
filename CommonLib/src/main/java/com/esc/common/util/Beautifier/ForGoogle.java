package com.esc.common.util.Beautifier;

import java.util.ArrayList;

/**
 * Created by afirsov on 2/1/2016.
 */
public class ForGoogle implements IAffilationBeautifier{

    private String Modify(String str){
        String[] arr = str.split(",");
        String result = "";
        for (String s : arr) {
            String basedOnIter = new String(s).toLowerCase();
            if(basedOnIter.contains("inst") ||
                basedOnIter.contains("research center") ||
                basedOnIter.contains("researchcenter") ||
                basedOnIter.contains("university")){
                result += s.trim();
                continue;
            }
            if(basedOnIter.contains("novosibirsk") ||
                basedOnIter.contains("berlin") ||
                basedOnIter.contains("munich") ||
                basedOnIter.contains("tartu") ||
                basedOnIter.contains("koltsovo"))
            {
                result += "+" + s.split("\\.")[0].trim();
                return result;
            }
        }

        return result;
    }

    @Override
    public String Beautify(String input) {
        return Modify(input);
    }

    @Override
    public String[] Beautify(String[] input) {
        String[] res = new String[input.length];
        for (int i=0;i<input.length;i++) {
            res[i] = Modify(input[i]);
        };
        return res;
    }

    @Override
    public String[] Beautify(ArrayList<String> input) {
        String[] in = input.toArray(new String[input.toArray().length]);
        String[] res = new String[in.length];
        for (int i=0;i<in.length;i++) {
            res[i] = Modify(in[i]);
        };
        return res;
    }
}
