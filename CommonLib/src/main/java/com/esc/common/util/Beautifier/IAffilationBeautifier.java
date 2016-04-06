package com.esc.common.util.Beautifier;

import java.security.InvalidAlgorithmParameterException;
import java.util.ArrayList;

/**
 * Created by afirsov on 2/1/2016.
 */
public interface IAffilationBeautifier {
    String Beautify(String input);
    String[] Beautify(String[] input);
    String[] Beautify(ArrayList<String> input);
}
