package com.esc.common.util.Beautifier;

import java.util.ArrayList;

/**
 * Created by afirsov on 2/1/2016.
 */
public class ForGoogle extends BaseAffilationBeautifier {

    @Override
    String Modify(String str){
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
}
