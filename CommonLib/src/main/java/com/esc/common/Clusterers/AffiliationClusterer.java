package com.esc.common.Clusterers;

import algorithms.ClusteringAlgorithm;
import algorithms.DBSCAN;
import algorithms.KMeans;
import com.esc.common.Modules.AffiliationResolver.AffiliationDistance;
import com.esc.common.Modules.AffiliationResolver.MatrixType;
import com.esc.common.util.LabeledMatrice2Float;
import com.sun.org.apache.xpath.internal.operations.Variable;
import distance.DistanceMeasure;
import distance.EuclideanDistance;
import input.Dataset;
import input.FeatureVector;
import output.Cluster;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by afirsov on 1/28/2016.
 */

public class AffiliationClusterer{
    private Dataset set = new Dataset();
    private ClusteringAlgorithm clustering;
    private String[] initialArray;
    public Dataset GetDataset(){
        return set;
    }

    public static void main(String[] args)
    {
        long startTime = System.nanoTime();
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
        LabeledMatrice2Float matrix = dist.GetDistanceMatrixWithLabels(MatrixType.GooglePlaces);


        AffiliationClusterer clstrr = new AffiliationClusterer(arr, matrix.GetMatrice());
        clstrr.PerformDBSCANClustering(0.05f,1);
        //clstrr.PerformKMeansClustering(new EuclideanDistance(), 3);


        long endTime = System.nanoTime();
        long duration = (endTime - startTime)/1000000;

        System.out.println(clstrr.ToString() + "\n" + "Preformed in " + Long.toString(duration) + " milliseconds");
    }

    public AffiliationClusterer(String[] initialArr, String[][] countedMatrix)
    {
        initialArray = initialArr;
        for (int i = 1; i<initialArray.length;i++) {
            set.add(new FeatureVector(countedMatrix[i],false));
        }
    }
    public void PerformDBSCANClustering(float epsilon, int minPoints){
        set.reset();

        clustering = new DBSCAN();

        ((DBSCAN)clustering).setEpsilon(epsilon);
        ((DBSCAN)clustering).setMinPoints(minPoints);

        clustering.doClustering(set);
    }

    public void PerformKMeansClustering(DistanceMeasure msr, int numberOfClusters){
        set.reset();

        clustering = new KMeans(msr,numberOfClusters);

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