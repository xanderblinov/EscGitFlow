package com.esc.common.Modules.AffiliationResolver;

import com.esc.common.util.Matrices.IMatrice2;

/**
 * Created by afirsov on 1/28/2016.
 */
public interface IAffiliationDistance {
    IMatrice2 GetDistanceMatrix(float multiplicationIndex);
}
