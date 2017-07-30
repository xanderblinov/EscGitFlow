package com.esc.common.Clusterers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.util.ArrayList;

import com.esc.common.Modules.AffiliationResolver.AffiliationDistance;
import com.esc.common.Modules.ExcelUtil.ClustersToExcel;
import com.esc.common.SimilarityFunctions.IGetCoefficient;
import com.esc.common.util.AffiliationSplitter.AffliationSplitStrategy;
import com.esc.common.util.AffiliationSplitter.Core.Interfaces.IAffiliationSplitStrategy;
import com.esc.common.util.Beautifier.IAffilationBeautifier;
import com.esc.common.util.Beautifier.JustLowercaseWords;
import com.esc.common.util.Matrices.IMatrice2;
import com.esc.common.util.Matrices.MatriceType;
import com.esc.common.util.Pair;
import com.sun.javaws.exceptions.InvalidArgumentException;

import algorithms.DBSCAN;
import jxl.write.WriteException;


/**
 * Created by demuanov on 11/10/2016.
 */
public class AffiliationsTests
{
	public static void main(String[] args) throws IOException, WriteException, InvalidArgumentException, InvalidAlgorithmParameterException
	{
		//itertor
		//
	    /*   ArrayList<String> arr = new ArrayList<>();
        final IDatabaseApi databaseApi = Application.getDatabaseApi();
        Iterator<Company> iterator = databaseApi.company().getIterator();
        while(iterator.hasNext()){
            Company out = iterator.next();
            arr.add(out.getName());
        }
        */
		long start = System.nanoTime();
		String fileName = new File("commonlib/src/main/Files/nsk_company_list_sorted.txt").getAbsolutePath();
		BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(fileName)));
		ArrayList<String> arr = new ArrayList<>();
		String line = "";
		IAffilationBeautifier beauty = new JustLowercaseWords();
		IAffiliationSplitStrategy split = new AffliationSplitStrategy();
		while ((line = bufferedReader.readLine()) != null)
		{
			arr.addAll(split.Split(beauty.Beautify(line)));
		}
		bufferedReader.close();


		float multiIndex = 1.50f;
		float maxEpsilon = 0.5f;
		float epsilon = 0.001f;
		int minPts = 2;

		//   (float)Math.pow(0.2,1);

		//   DoDBSCAN(arr,epsilon,minPts,0.00125f,maxEpsilon,multiIndex,
		//          new KMer(7),"C:/New/ESC","KMer" + System.nanoTime());

		//  DoKMeans(arr,62, new Jaccard(),"D:/desktop/ESC","SmithWaterman");
		long time = (System.nanoTime() - start);
		System.out.println("TIME OLOLOLOLOLO _>  "+ time);

	 /*	DoDBSCAN(arr, epsilon, minPts, 0.0125f, maxEpsilon, multiIndex,
				new Jaccard(), "C:/New/ESC", "Jaccard" + System.nanoTime());


       System.out.println("four");
        maxEpsilon = (float)Math.pow(0.04,1);
        epsilon = (float)Math.pow(0.01,1);
        DoDBSCAN(arr,epsilon,minPts,0.05f,maxEpsilon,multiIndex,
                new LevenshteinWorded(),"C:/New/ESC","LevenshteinWorded");*/
	}

	public static void DoDBSCAN(ArrayList<String> arr,
	                            float epsilon, int minPoints,
	                            float epsilonInc, float maxEpsilon, float multiplier,
	                            IGetCoefficient coeff, String pathToSave, String simType) throws InvalidArgumentException, IOException, WriteException
	{
		ClustersToExcel trans = new ClustersToExcel();
		//epsilon = epsilon * (float)Math.pow(multiplier,2) ;
		//maxEpsilon = maxEpsilon * (float)Math.pow(multiplier,2) ;
		System.out.println(arr.size() + "<---SIZE");

		do
		{

			long startTime = System.nanoTime();
			String[] input = arr.toArray(new String[arr.toArray().length]);
			IMatrice2<String, Float> matrix = new AffiliationDistance(input, coeff, MatriceType.LabeledMatrice2Float).GetDistanceMatrix(multiplier);
			//TODO: input rename

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
			System.out.println(epsilon + "<---EPS");
		}
		while (epsilon < maxEpsilon);
		trans.Transform(pathToSave);
	}
}


