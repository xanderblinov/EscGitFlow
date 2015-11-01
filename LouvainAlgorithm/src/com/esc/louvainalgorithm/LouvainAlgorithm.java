package com.esc.louvainalgorithm;


import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LouvainAlgorithm
{

	public static Connection conn;
	public static Statement statmt;
	public static ResultSet resSet;

	public LouvainAlgorithm(String[] args) throws IOException, SQLException, ClassNotFoundException
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

		network = readInputFile();

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
			update = true;
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

		writeClusterData(cluster);
	}

	private static Network readInputFile() throws SQLException
	{
		double[] edgeWeight1, edgeWeight2, nodeWeight;
		int i, j, nEdges, nLines, nNodes;
		int[] firstNeighborIndex, neighbor, nNeighbors, node1, node2;
		Network network;




		resSet = statmt.executeQuery("SELECT * FROM primitive_author_to_author");

		nLines = 0;

		while (resSet.next())
			nLines++;

		resSet = statmt.executeQuery("SELECT * FROM primitive_author_to_author");


		node1 = new int[nLines];
		node2 = new int[nLines];
		edgeWeight1 = new double[nLines];

		for(i = 0; i < nLines; i++)
			edgeWeight1[i] = 0;

		i = -1;

		for (j = 0; j < nLines; j++)
		{
			node1[j] = resSet.getInt("author") - 1;
			if (node1[j] > i)
				i = node1[j];
			node2[j] = resSet.getInt("coauthor") - 1;
			if (node2[j] > i)
				i = node2[j];
			edgeWeight1[j]++;
			resSet.next();
		}
		nNodes = i + 1; //Не забыть! При более сложной выборке авторов nNodes надо считать по-другому


		nNeighbors = new int[nNodes];
		for (i = 0; i < nLines; i++)
			if (node1[i] < node2[i])
			{
				nNeighbors[node1[i]]++;
				nNeighbors[node2[i]]++;
			}

		firstNeighborIndex = new int[nNodes + 1];
		nEdges = 0;
		for (i = 0; i < nNodes; i++)
		{
			firstNeighborIndex[i] = nEdges;
			nEdges += nNeighbors[i];
		}
		firstNeighborIndex[nNodes] = nEdges;

		neighbor = new int[nEdges];
		edgeWeight2 = new double[nEdges];
		Arrays.fill(nNeighbors, 0);
		for (i = 0; i < nLines; i++)
			if (node1[i] < node2[i])
			{
				j = firstNeighborIndex[node1[i]] + nNeighbors[node1[i]];
				neighbor[j] = node2[i];
				edgeWeight2[j] = edgeWeight1[i];
				nNeighbors[node1[i]]++;
				j = firstNeighborIndex[node2[i]] + nNeighbors[node2[i]];
				neighbor[j] = node1[i];
				edgeWeight2[j] = edgeWeight1[i];
				nNeighbors[node2[i]]++;
			}


		nodeWeight = new double[nNodes];
		for (i = 0; i < nEdges; i++)
			nodeWeight[neighbor[i]] += edgeWeight2[i];
		network = new Network(nNodes, firstNeighborIndex, neighbor, edgeWeight2, nodeWeight);

		return network;
	}

	private static void writeClusterData(int[] cluster) throws SQLException
	{
		statmt.execute("DELETE FROM author_to_cluster"); //!!!


		for (int i = 0; i < cluster.length; i++)
		{

			statmt.execute("INSERT into 'author_to_cluster' ('author', 'cluster') VALUES(" + String.valueOf(i + 1) + ", " + String.valueOf(cluster[i] + 1) + "); ");


		}


	}


	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException
	{

		Class.forName("org.sqlite.JDBC");
		conn = DriverManager.getConnection("jdbc:sqlite:Database\\src\\main\\resources\\test.db");
		System.out.print("Database connected \n");
		statmt = conn.createStatement();

		String[] LAdata = {"100", "1000", "40"};    //number of starts, number of iteration
		LouvainAlgorithm LA = new LouvainAlgorithm(LAdata);

		conn.close();
		statmt.close();
		resSet.close();

	}

}
