package com.esc.louvainalgorithm;


import java.io.IOException;
import java.util.Random;
import java.sql.SQLException;


public class LouvainAlgorithm
{


	public LouvainAlgorithm(String[] args, int year, int id, DBConnection connection, OutputInformationRecorder oir) throws IOException, SQLException, ClassNotFoundException
	{

		boolean printOutput, update;
		double modularity, maxModularity, resolution, resolution2;
		int i, j, nClusters, nIterations, nRandomStarts;
		int[] cluster;
		long beginTime, endTime, randomSeed;
		Network network;
		Random random;
		resolution = 1;				   //for algorithm
		nRandomStarts = Integer.parseInt(args[0]);           //number of starts
		nIterations = Integer.parseInt(args[1]);            //number of iteration
		randomSeed = Integer.parseInt(args[2]);               // Random argument
		printOutput = true;               //if true print data on screen, if false don't




		if (printOutput)
		{
			System.out.println("Reading input file...");
			System.out.println();
		}

		connection.coauthorDao.parse(year);
		network = connection.coauthorDao.getNetwork();

		if (printOutput)
		{
			System.out.format("Number of nodes: %d%n", network.getNNodes());
			System.out.format("Number of edges: %d%n", network.getNEdges() / 2);
			System.out.println();
			System.out.println();
		}

		resolution2 = resolution / network.getTotalEdgeWeight();

		beginTime = System.currentTimeMillis();
		cluster = null;
		nClusters = -1;
		maxModularity = Double.NEGATIVE_INFINITY;
		random = new Random(randomSeed);
		for (i = 0; i < nRandomStarts; i++)
		{
			if (printOutput && (nRandomStarts > 1))
				System.out.format("Random start: %d%n", i + 1);

			network.initSingletonClusters();

			j = 0;
			do
			{
				if (printOutput && (nIterations > 1))
					System.out.format("Iteration: %d%n", j + 1);
				update = network.runLouvainAlgorithm(resolution2, random);
				j++;
				modularity = network.calcQualityFunction(resolution2);

				if (printOutput && (nIterations > 1))
					System.out.format("Modularity: %.4f%n", modularity);
			}
			while ((j < nIterations) && update);

			if (modularity > maxModularity)
			{
				network.orderClustersByNNodes();
				cluster = network.getClusters();
				nClusters = network.getNClusters();
				maxModularity = modularity;
			}

			if (printOutput && (nRandomStarts > 1))
			{
				if (nIterations == 1)
					System.out.format("Modularity: %.4f%n", modularity);
				System.out.println();
			}
		}
		endTime = System.currentTimeMillis();

		if (printOutput)
		{
			if (nRandomStarts == 1)
			{
				if (nIterations > 1)
					System.out.println();
				System.out.format("Modularity: %.4f%n", maxModularity);
			}
			else

			System.out.format("Maximum modularity in %d random starts: %.4f%n", nRandomStarts, maxModularity);
			System.out.format("Number of communities: %d%n", nClusters);
			System.out.format("Elapsed time: %d milliseconds%n", Math.round((endTime - beginTime)));
			System.out.println();
			System.out.println("Writing output file...");
			System.out.println();
		}

		for(i = 0; i < cluster.length; i++)
		{

			oir.recordAuthorToClusterData(connection.coauthorDao.getPartauthor(i), cluster[i]);

		}
		oir.recordEvolutionSliceData(year, id);

	}


	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException
	{
		int firstYear, lastYear, evId;
		firstYear = 0;
		lastYear = 0;
		evId = 0;
		String[] LAdata = {"100", "1000", "40"};    //number of starts, number of iteration

		DBConnection connection = new DBConnection();
		System.out.print("Database connected \n");
		OutputInformationRecorder oir = new OutputInformationRecorder(connection);

		oir.recordEvolutionData(firstYear, lastYear);


		while(connection.evolutionDao.queryForId(evId + 1) != null)
			evId++;


		for(int i = firstYear; i <= lastYear; i++)
		{

			LouvainAlgorithm LA = new LouvainAlgorithm(LAdata, i, evId, connection, oir);


		}


	}

}
