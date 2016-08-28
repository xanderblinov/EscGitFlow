package com.esc.common.Clusterers;

import algorithms.DBSCAN;
import algorithms.KMeans;
import app.Application;
import com.esc.common.Modules.AffiliationResolver.AffiliationDistance;
import com.esc.common.Modules.ExcelUtil.ClustersToExcel;
import com.esc.common.SimilarityFunctions.IGetCoefficient;
import com.esc.common.SimilarityFunctions.KMer;
import com.esc.common.util.AffiliationSplitter.AffliationSplitStrategy;
import com.esc.common.util.AffiliationSplitter.Core.Interfaces.IAffiliationSplitStrategy;
import com.esc.common.util.Beautifier.IAffilationBeautifier;
import com.esc.common.util.Beautifier.JustLowercaseWords;
import com.esc.common.util.Matrices.IMatrice2;
import com.esc.common.util.Matrices.MatriceType;
import com.esc.common.util.Pair;
import com.sun.javaws.exceptions.InvalidArgumentException;
import distance.EuclideanDistance;
import jxl.write.WriteException;
import net.inference.sqlite.DatabaseApi;
import net.inference.sqlite.dto.Company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by afirsov on 2/18/2016.
 */
public class AffiliationsTests {
    public static void main(String[] args) throws IOException, WriteException, InvalidArgumentException, InvalidAlgorithmParameterException {
        String fileName = new File("CommonLib/src/main/Files/nsk_company_list_real.txt").getAbsolutePath();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(fileName)));
        ArrayList<String> arr = new ArrayList<>();
        String line = "";
        IAffilationBeautifier beauty = new JustLowercaseWords();
        IAffiliationSplitStrategy split = new AffliationSplitStrategy();
        while((line = bufferedReader.readLine()) != null) {
            arr.addAll(split.Split(beauty.Beautify(line)));
        }
        bufferedReader.close();

        //todo: ASAP select by year

        float multiIndex = 1.0f;
        int minPts = 1;
        float maxEpsilon = 0.4f;
        float epsilon = (float)Math.pow(0.4,1);

        DoDBSCAN(arr,epsilon,minPts,0.01f,maxEpsilon,multiIndex,
                new KMer(10),"D:/desktop/ESC","KMer" + 10);

        /*DoKMeans(arr,62, new Jaccard(),"D:/desktop/ESC","SmithWaterman");
        DoDBSCAN(arr,epsilon,minPts,0.01f,maxEpsilon,multiIndex,
                new SmithWatermanCoeff(),"D:/desktop/ESC","SmithWaterman");
        maxEpsilon = (float)Math.pow(0.04,1);
        epsilon = (float)Math.pow(0.004,1);
        DoDBSCAN(arr,epsilon,minPts,0.005f,maxEpsilon,multiIndex,
                new Jaccard(),"D:/desktop/ESC","Jaccard");
        maxEpsilon = (float)Math.pow(0.14,1);
        epsilon = (float)Math.pow(0.04,1);
        DoDBSCAN(arr,epsilon,minPts,0.005f,maxEpsilon,multiIndex,
                new Levenshtein(),"D:/desktop/ESC","Levenshtein");
        maxEpsilon = (float)Math.pow(0.04,1);
        epsilon = (float)Math.pow(0.01,1);
        DoDBSCAN(arr,epsilon,minPts,0.005f,maxEpsilon,multiIndex,
                new LevenshteinWorded(),"D:/desktop/ESC","LevenshteinWorded");*/
    }

    public static void DoDBSCAN(ArrayList<String> arr,
                                float epsilon, int minPoints,
                                float epsilonInc, float maxEpsilon, float multiplier,
                                IGetCoefficient coeff, String pathToSave, String simType) throws InvalidArgumentException, IOException, WriteException {
        ClustersToExcel trans = new ClustersToExcel();
        epsilon = epsilon * (float)Math.pow(multiplier,2) ;
        maxEpsilon = maxEpsilon * (float)Math.pow(multiplier,2) ;
        do {
            long startTime = System.nanoTime();
            String[] input = arr.toArray(new String[arr.toArray().length]);
            IMatrice2<String, Float> matrix = new AffiliationDistance(input, coeff, MatriceType.LabeledMatrice2Float).GetDistanceMatrix(multiplier);
            DBSCAN dbscan = new DBSCAN();
            dbscan.setEpsilon(epsilon);
            dbscan.setMinPoints(minPoints);
            AffiliationClusterer clstrr = new AffiliationClusterer(arr.toArray(new String[arr.size()]), matrix, dbscan);
            clstrr.simType = simType;
            clstrr.clasterType = dbscan.toString();
            clstrr.args.add(new Pair<String, String>("epsilon", Float.toString(epsilon)));
            clstrr.args.add(new Pair<String, String>("minPts", Integer.toString(minPoints)));
            clstrr.PerformClustering();
            clstrr.lastDuration = (System.nanoTime() - startTime) / 1000000;
            trans.Add(clstrr);
            epsilon = epsilon + epsilonInc;
        }while(epsilon < maxEpsilon);
        trans.Transform(pathToSave);
    }

    public static void DoKMeans(ArrayList<String> arr,int number,
                                IGetCoefficient coeff, String pathToSave, String simType) throws InvalidArgumentException, IOException, WriteException {
        ClustersToExcel trans = new ClustersToExcel();
        long startTime = System.nanoTime();
        String[] input = arr.toArray(new String[arr.toArray().length]);
        IMatrice2<String, Float> matrix = new AffiliationDistance(input, coeff, MatriceType.LabeledMatrice2Float).GetDistanceMatrix(1.0f);
        KMeans kmeans = new KMeans(new EuclideanDistance(),number);
        AffiliationClusterer clstrr = new AffiliationClusterer(arr.toArray(new String[arr.size()]), matrix, kmeans);
        clstrr.simType = simType;
        clstrr.clasterType = kmeans.toString();
        clstrr.args.add(new Pair<String, String>("number", Integer.toString(number)));
        clstrr.PerformClustering();
        clstrr.lastDuration = (System.nanoTime() - startTime) / 1000000;
        trans.Add(clstrr);
        trans.Transform(pathToSave);
    }
}
