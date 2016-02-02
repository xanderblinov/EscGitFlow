package com.esc.common.Clusterers;

import algorithms.ClusteringAlgorithm;
import algorithms.DBSCAN;
import algorithms.KMeans;
import com.esc.common.Modules.AffiliationResolver.AffiliationDistance;
import com.esc.common.SimilarityFunctions.GooglePlacesCoeff;
import com.esc.common.SimilarityFunctions.Jaccard;
import com.esc.common.SimilarityFunctions.LevenshteinWorded;
import com.esc.common.util.Matrices.IMatrice2;
import com.esc.common.util.Matrices.MatriceType;
import distance.EuclideanDistance;
import input.Dataset;
import input.FeatureVector;
import output.Cluster;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by afirsov on 1/28/2016.
 */

public class AffiliationClusterer{
    private Dataset set;
    private ClusteringAlgorithm clustering;
    private String[] initialArray;
    public Dataset GetDataset(){
        return set;
    }

    public static void main(String[] args) throws IOException {
        long startTime = System.nanoTime();

        String fileName = new File("CommonLib/src/main/Files/nsk_company_list_sorted.txt").getAbsolutePath();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(fileName)));

        ArrayList<String>arr = new ArrayList<>();
        String line = "";
        while((line = bufferedReader.readLine()) != null) {
            arr.add(line);
        }

        bufferedReader.close();

        AffiliationDistance dist = new AffiliationDistance(arr.toArray(new String[arr.size()]),
                new LevenshteinWorded(),
                MatriceType.LabeledMatrice2Float);
        IMatrice2<String, Float> matrix = dist.GetDistanceMatrix();


        DBSCAN dbscan = new DBSCAN();
        dbscan.setEpsilon(0.1f);
        dbscan.setMinPoints(1);

        KMeans kmeans = new KMeans(new EuclideanDistance(), 30);

        AffiliationClusterer clstrr = new AffiliationClusterer(arr.toArray(new String[arr.size()]), matrix, kmeans);
        clstrr.PerformClustering();


        long endTime = System.nanoTime();
        long duration = (endTime - startTime)/1000000;

        System.out.println(clstrr.ToString() + "\n" + "Preformed in " + Long.toString(duration) + " milliseconds");
    }

    public AffiliationClusterer(String[] initialArr, IMatrice2<String,Float> countedMatrix, ClusteringAlgorithm alg)
    {
        String[][] matrice = countedMatrix.GetMatrice();
        this.set = new Dataset();
        this.initialArray = initialArr;
        for (int i = 1; i < matrice[0].length;i++) {
            String[] fv = new String[matrice[0].length];
            for(int j = 0; j<matrice.length;j++)
            {
                fv[j] = matrice[j][i];
            }
            this.set.add(new FeatureVector(fv,false));
        }
        this.clustering = alg;
    }
    public void PerformClustering(){
        set.reset();
        clustering.doClustering(set);
    }

    public String ToString(){
        String output = "";
        for (int i = 0; i <  set.getClustermap().entrySet().toArray().length; i++){
            Map.Entry<Integer,Cluster> elem = (Map.Entry<Integer, Cluster>) set.getClustermap().entrySet().toArray()[i];
            output += "Cluster " + elem.getKey() + " contains: ";
            for (FeatureVector fv: elem.getValue().getClusterelements()) {
                output += initialArray[fv.getLabel() - 1] + "|";
            }
            output += ";\n";
        }
        return output;
    }
}