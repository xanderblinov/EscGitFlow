package com.esc.common.util.Beautifier;

import java.util.ArrayList;

/**
 * Created by afirsov on 4/6/2016.
 */
public class JustLowercaseWords extends BaseAffilationBeautifier {

    @Override
    String Modify(String str){
        return str.replace(" ","")
                .replace(",","")
                .replace(".","")
                .replace("&","and").toLowerCase();
    }
}
