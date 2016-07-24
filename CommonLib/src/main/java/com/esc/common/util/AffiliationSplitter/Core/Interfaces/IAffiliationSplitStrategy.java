package com.esc.common.util.AffiliationSplitter.Core.Interfaces;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by afirsov on 7/9/2016.
 */
public interface IAffiliationSplitStrategy {
    public ArrayList<String> Split(String affiliationName) throws IOException;
}
