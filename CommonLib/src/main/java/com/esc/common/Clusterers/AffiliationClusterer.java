package com.esc.common.Clusterers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import com.esc.common.util.Matrices.IMatrice2;
import com.esc.common.util.Pair;
import com.sun.javaws.exceptions.InvalidArgumentException;

import algorithms.ClusteringAlgorithm;
import input.Dataset;
import input.FeatureVector;
import jxl.write.WriteException;
import output.Cluster;

/**
 * Created by afirsov on 1/28/2016.
 */

public class AffiliationClusterer{
    private Dataset set;
    public Dataset getSet() {
        return set;
    }
    private ClusteringAlgorithm clustering;
    private String[] initialArray;
    public String[] getInitialArray(){
        return initialArray;
    }
    public String simType = "Jaccard";
    public String getSimilarityType(){
        return simType;
    }
    public String clasterType = "DBSCAN";
    public String getClasterType(){
        return clasterType;
    }
    public long lastDuration;
    public long getDuration(){
        return lastDuration;
    }
    public ArrayList<Pair<String,String>> args;
    public ArrayList<Pair<String,String>> getArguments(){
        return args;
    }

    public AffiliationClusterer(String[] initialArr, IMatrice2<String,Float> countedMatrix, ClusteringAlgorithm alg)
    {
        args = new ArrayList<>();
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

    public void ToExcel(String path) throws IOException, WriteException, InvalidArgumentException {

    }
}