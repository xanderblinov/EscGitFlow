package com.esc.common.Modules.AffiliationResolver;

import com.esc.common.util.IMatrice2;
import com.esc.common.util.Matrice2Float;

/**
 * Created by afirsov on 1/28/2016.
 */
public interface IAffiliationDistance {
    IMatrice2 GetDistanceMatrix(MatrixType type);
}
