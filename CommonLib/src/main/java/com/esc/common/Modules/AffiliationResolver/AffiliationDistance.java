package com.esc.common.Modules.AffiliationResolver;

import com.esc.common.Modules.GooglePlaces.GooglePlacesAPI;
import com.esc.common.SimilarityFunctions.GooglePlacesCoeff;
import com.esc.common.SimilarityFunctions.IGetCoefficient;
import com.esc.common.SimilarityFunctions.Jaccard;
import com.esc.common.SimilarityFunctions.Levenshtein;

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
        float[][] matrix = dist.GetDistanceMatrix(MatrixType.Levenshtein);
    }

    private GooglePlacesAPI api;
    private Levenshtein levenshtein;
    private Jaccard jaccard;
    private GooglePlacesCoeff googlePlaces;
    private String[] affiliations;
    private float[][] resultMatrix;
    private String[][] resultMatrixWithLabels;

    public AffiliationDistance(String[] affilis) {
        api = new GooglePlacesAPI();
        levenshtein = new Levenshtein();
        jaccard = new Jaccard();
        googlePlaces = new GooglePlacesCoeff();
        affiliations = affilis.clone();
        resultMatrix = new float[affilis.length][affilis.length];
        resultMatrixWithLabels = new String[affilis.length + 1][affilis.length + 1];
    }

    public float[][] GetCountedResult(){
        return resultMatrix.clone();
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
    public float[][] GetDistanceMatrix(MatrixType type) {

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
                resultMatrix[i][j] = method.GetCoefficient(affiliations[i], affiliations[j],true);
            }
        }

        return resultMatrix.clone();
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
    public String[][] GetDistanceMatrixWithLabels(MatrixType type) {

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
            resultMatrixWithLabels[i + 1][0] = Integer.toString(i);
            for (int j =0; j < affiliations.length;j++ ) {
                resultMatrixWithLabels[0][j + 1] = Integer.toString(j);
                resultMatrixWithLabels[i + 1][j + 1] = "Value:" + Float.toString(1.0f - method.GetCoefficient(affiliations[i], affiliations[j],true));
            }
        }

        return resultMatrixWithLabels.clone();
    }
}
