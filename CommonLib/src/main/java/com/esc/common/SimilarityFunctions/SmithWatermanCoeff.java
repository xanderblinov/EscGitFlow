package com.esc.common.SimilarityFunctions;

import com.esc.common.SimilarityFunctions.AlignmentAlgs.SmithWaterman;

/**
 * Created by afirsov on 2/18/2016.
 */
public class SmithWatermanCoeff implements IGetCoefficient {
    @Override
    public float GetCoefficient(String whatToCheck, String withWhatToCheck, boolean ignoreCase) {
        if(ignoreCase){
            whatToCheck = whatToCheck.toLowerCase();
            withWhatToCheck = withWhatToCheck.toLowerCase();
        }

        return new SmithWaterman(whatToCheck,withWhatToCheck).computeSmithWaterman();
    }
}
