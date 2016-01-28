package com.esc.common.SimilarityFunctions;

/**
 * Created by afirsov on 1/28/2016.
 */
public interface IGetCoefficient {
    float GetCoefficient(String whatToCheck, String withWhatToCheck, boolean ignoreCase);
}
