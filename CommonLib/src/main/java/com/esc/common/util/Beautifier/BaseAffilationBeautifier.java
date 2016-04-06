package com.esc.common.util.Beautifier;

import java.security.InvalidAlgorithmParameterException;
import java.util.ArrayList;

/**
 * Created by afirsov on 2/1/2016.
 */
public abstract class BaseAffilationBeautifier implements IAffilationBeautifier{

    abstract String Modify(String str);

    public String Beautify(String input){
        return Modify(input);
    }

    public String[] Beautify(String[] input){
        String[] res = new String[input.length];
        for (int i=0;i<input.length;i++) {
            res[i] = Beautify(input[i]);
        };
        return res;
    }
    public String[] Beautify(ArrayList<String> input) {
        String[] in = input.toArray(new String[input.toArray().length]);
        String[] res = new String[in.length];
        for (int i=0;i<in.length;i++) {
            res[i] = Beautify(in[i]);
        };
        return res;
    }
}
