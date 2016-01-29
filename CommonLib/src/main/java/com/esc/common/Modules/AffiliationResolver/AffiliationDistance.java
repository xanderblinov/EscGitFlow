package com.esc.common.Modules.AffiliationResolver;

import com.esc.common.Modules.GooglePlaces.GooglePlacesAPI;
import com.esc.common.SimilarityFunctions.GooglePlacesCoeff;
import com.esc.common.SimilarityFunctions.IGetCoefficient;
import com.esc.common.SimilarityFunctions.Jaccard;
import com.esc.common.SimilarityFunctions.Levenshtein;
import com.esc.common.util.LabeledMatrice2Float;
import com.esc.common.util.Matrice2Float;

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
        AffiliationDistance dist = new AffiliationDistance(arr);
        Matrice2Float matrix = dist.GetDistanceMatrix(MatrixType.Levenshtein);
    }

    private GooglePlacesAPI api;
    private Levenshtein levenshtein;
    private Jaccard jaccard;
    private GooglePlacesCoeff googlePlaces;
    private String[] affiliations;
    private Matrice2Float resultMatrix;
    private LabeledMatrice2Float resultMatrixWithLabels;

    public AffiliationDistance(String[] affilis) {
        api = new GooglePlacesAPI();
        levenshtein = new Levenshtein();
        jaccard = new Jaccard();
        googlePlaces = new GooglePlacesCoeff(levenshtein);
        affiliations = affilis.clone();
        resultMatrix = new Matrice2Float(affilis.length,affilis.length);
        resultMatrixWithLabels = new LabeledMatrice2Float(affilis.length,affilis.length);
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
     * @param type MatrixType type parameter indicating how to count values of cells (Levenshtein, Jaccard, etc).
     * @return
     */
    @Override
    public Matrice2Float GetDistanceMatrix(MatrixType type) {

        IGetCoefficient method = null;
        switch(type){
            case Levenshtein:
                method = levenshtein;
                break;
            case Jaccard:
                method = jaccard;
                break;
            case GooglePlaces:
                method = googlePlaces;
                break;
        }
        for (int i = 0; i < affiliations.length; i++) {
            for (int j = 0; j < affiliations.length;j++ ) {
                resultMatrix.Add(i,j,method.GetCoefficient(affiliations[i], affiliations[j],true));
            }
        }

        resultMatrix.NormalizeColumns();
        return resultMatrix;
    }

    /**
     * Returns a 2-dimensional string array with labels corresponding to how affiliations in constructor parameter are alike. For example:
     * <table>
     *     <tbody>
     *         <tr><td></td><td><b>1</b></td><td><b>2</b></td><td><b>3</b></td></tr>
     *         <tr><td><b>1</b></td><td>0.1</td><td>0.0</td><td>1.0</td></tr>
     *         <tr><td><b>2</b></td><td>0.7</td><td>0.3</td><td>0.7</td></tr>
     *         <tr><td><b>3</b></td><td>0.5</td><td>0.2</td><td>0.8</td></tr>
     *        </tbody>
     * </table>
     * @param type MatrixType type parameter indicating how to count values of cells (Levenshtein, Jaccard, etc).
     * @return
     */
    public LabeledMatrice2Float GetDistanceMatrixWithLabels(MatrixType type) {

        Matrice2Float floatMatrix = GetDistanceMatrix(type);
        resultMatrixWithLabels = new LabeledMatrice2Float(floatMatrix);

        resultMatrixWithLabels.NormalizeColumns();
        return resultMatrixWithLabels;
    }
}
