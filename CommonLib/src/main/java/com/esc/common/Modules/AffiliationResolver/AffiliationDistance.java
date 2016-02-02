package com.esc.common.Modules.AffiliationResolver;

import com.esc.common.Modules.GooglePlaces.GooglePlacesAPI;
import com.esc.common.SimilarityFunctions.IGetCoefficient;
import com.esc.common.SimilarityFunctions.Levenshtein;
import com.esc.common.util.Matrices.IMatrice2;
import com.esc.common.util.Matrices.LabeledMatrice2Float;
import com.esc.common.util.Matrices.Matrice2Float;
import com.esc.common.util.Matrices.MatriceType;

/**
 * Created by afirsov on 1/28/2016.
 */
public class AffiliationDistance implements IAffiliationDistance {
    public static void main(String[] args)
    {
        String[] arr = new String[]{
            "University of Cansas",
            "Cansas City university",
            "Universuty of Winnipeg, physics department",
            "University of winnipeg",
            "Winnipeg university",
            "novosibirsk State university",
            "novosibirsk national institue",
            "Cansas university"};

        AffiliationDistance dist = new AffiliationDistance(arr, new Levenshtein(), MatriceType.LabeledMatrice2Float);
        IMatrice2 matrix = dist.GetDistanceMatrix();
    }

    private GooglePlacesAPI api;
    private IGetCoefficient method;
    private String[] affiliations;
    private IMatrice2 resultMatrix;

    public AffiliationDistance(String[] affilis, IGetCoefficient method, MatriceType type) {
        api = new GooglePlacesAPI();
        this.method = method;
        affiliations = affilis.clone();
        switch (type){
            case Matrice2Float:
                this.resultMatrix = new Matrice2Float(affilis.length, affilis.length);
                break;
            case LabeledMatrice2Float:
                this.resultMatrix = new LabeledMatrice2Float(affilis.length, affilis.length);
                break;
            default:
                break;
        }
    }

    /**
     * Returns a 2-dimensional string array without labels corresponding to how affiliations in constructor parameter are alike. For example:
     * <table>
     *     <tbody>
     *         <tr><td>0.1</td><td>0.0</td><td>1.0</td></tr>
     *         <tr><td>0.7</td><td>0.3</td><td>0.7</td></tr>
     *         <tr><td>0.5</td><td>0.2</td><td>0.8</td></tr>
     *        </tbody>
     * </table>
     * @return
     */
    @Override
    public IMatrice2 GetDistanceMatrix() {

        for (int i = 0; i < affiliations.length; i++) {
            for (int j = 0; j < affiliations.length;j++ ) {
                resultMatrix.Add(i,j,method.GetCoefficient(affiliations[i], affiliations[j],true));
            }
        }

        resultMatrix.NormalizeColumns();
        return resultMatrix;
    }
}
