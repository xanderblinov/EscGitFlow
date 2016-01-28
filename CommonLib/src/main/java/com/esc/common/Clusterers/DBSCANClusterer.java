package com.esc.common.Clusterers;

import algorithms.DBSCAN;
import com.esc.common.Modules.AffiliationResolver.AffiliationDistance;
import com.esc.common.Modules.AffiliationResolver.MatrixType;
import com.sun.org.apache.xpath.internal.operations.Variable;
import input.Dataset;
import input.FeatureVector;
import output.Cluster;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by afirsov on 1/28/2016.
 */

public class DBSCANClusterer{
    private Dataset set = new Dataset();
    private DBSCAN dbscan = new DBSCAN();

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
        String[][] matrix = dist.GetDistanceMatrixWithLabels(MatrixType.Jaccard);

        DBSCANClusterer dbscan = new DBSCANClusterer();
        dbscan.PerformClustering(matrix,0.4f,1);
        Dataset set = dbscan.GetDataset();
        long endTime = System.nanoTime();

        long duration = (endTime - startTime)/1000000;

        String output = "";
        for (int i = 0; i <  set.getClustermap().entrySet().toArray().length; i++){
            Map.Entry<Integer,Cluster> elem = (Map.Entry<Integer, Cluster>) set.getClustermap().entrySet().toArray()[i];
            output += "Cluster " + elem.getKey() + " contains: ";
            for (FeatureVector fv: elem.getValue().getClusterelements()) {
                output += arr[fv.getLabel()] + "|";
            }
            output += ";\n";
        }

        output += "Preformed in " + Long.toString(duration) + " milliseconds";
        System.out.println(output);
    }

    public void PerformClustering(String[][] arr, float epsilon, int minPoints){
        dbscan.setEpsilon(epsilon);
        dbscan.setMinPoints(minPoints);

        for (int i = 1; i<arr.length;i++) {
            set.add(new FeatureVector(arr[i],false));
        }

        dbscan.doClustering(set);
    }
}