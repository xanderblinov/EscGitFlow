package com.esc.louvainalgorithm;


import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.Arrays;

public class CoathorInformationParser
{

	private Dao<AuthorToAuthor, Integer> coauthorDao;
	private int nLines, nNodes;
	private int[] mFirstNeighborIndex;
	private int[] neighbor;
	private int[] mPartAuthor;
	double[] mEdgeWeight1, mEdgeWeight2, mNodeWeight;


	public CoathorInformationParser(ConnectionSource source) throws SQLException
	{

		coauthorDao = DaoManager.createDao(source, AuthorToAuthor.class);

	}

	public void countLines(int year) throws SQLException
	{

		this.nLines = 0;
		int i = 0;
		while(coauthorDao.queryForId(i + 1) != null)
		{

			if(coauthorDao.queryForId(i + 1).getYear() == year)
				this.nLines++;
			i++;

		}


	}

	public int getnLines()
	{

		return nLines;

	}

	public void countNodes(int year) throws SQLException
	{

		nNodes = 0;
		int i = 0;
		while((coauthorDao.queryForId(i + 1) != null))
		{
			if ((coauthorDao.queryForId(i + 1).getYear() == year) && (coauthorDao.queryForId(i + 1).getAuthor() > nNodes))
				nNodes = coauthorDao.queryForId(i + 1).getAuthor();
			i++;
		}

	}

	public int getnNodes()
	{

		return nNodes;

	}

	public void parse(int year) throws SQLException
	{
		int i, j, temp, nEdges;
		this.countLines(year);
		this.countNodes(year);
		int[] node1 = new int[nLines];
		int[] node2 = new int[nLines];
		this.mEdgeWeight1 = new double[nLines];

		for(i = 0; i < nLines; i++)
			this.mEdgeWeight1[i] = 0;

		for (i = 0; i < nLines; i++)
		{

			node1[i] = coauthorDao.queryForId(i + 1).getAuthor()- 1;
			node2[i] = coauthorDao.queryForId(i + 1).getCoauthor() - 1;
			this.mEdgeWeight1[i]++;

		}

		this.mPartAuthor = new int[nNodes];
		for(i = 0; i < nNodes; i++)
			this.mPartAuthor[i] = 0;

		i = 0;
		j = 0;
		temp = 1;

		while(i < 1)
		{

			if((this.mPartAuthor[i] < temp) && (coauthorDao.queryForId(j + 1).getYear() == year))
			{
				this.mPartAuthor[i] = temp;
				i++;
			}

			temp = coauthorDao.queryForId(j + 1).getAuthor();
			j++;

		}

		while(i < nNodes)
		{

			if((this.mPartAuthor[i - 1] < temp) && (coauthorDao.queryForId(j + 1).getYear() == year))
			{
				this.mPartAuthor[i] = temp;
				i++;
			}

			temp = coauthorDao.queryForId(j + 1).getAuthor();
			j++;

		}


		int[] nNeighbors = new int[nNodes];
		for (i = 0; i < nLines; i++)
			if (node1[i] < node2[i])
			{
				nNeighbors[node1[i]]++;
				nNeighbors[node2[i]]++;
			}

		this.mFirstNeighborIndex = new int[nNodes + 1];
		nEdges = 0;

		for (i = 0; i < nNodes; i++)
		{

			this.mFirstNeighborIndex[i] = nEdges;
			nEdges += nNeighbors[i];

		}
		this.mFirstNeighborIndex[nNodes] = nEdges;

		this.neighbor = new int[nEdges];
		this.mEdgeWeight2 = new double[nEdges];
		Arrays.fill(nNeighbors, 0);
		for (i = 0; i < nLines; i++)
			if (node1[i] < node2[i])
			{
				j = this.mFirstNeighborIndex[node1[i]] + nNeighbors[node1[i]];
				this.neighbor[j] = node2[i];
				this.mEdgeWeight2[j] = this.mEdgeWeight1[i];
				nNeighbors[node1[i]]++;
				j = mFirstNeighborIndex[node2[i]] + nNeighbors[node2[i]];
				this.neighbor[j] = node1[i];
				this.mEdgeWeight2[j] = this.mEdgeWeight1[i];
				nNeighbors[node2[i]]++;
			}


		this.mNodeWeight = new double[nNodes];

		for (i = 0; i < nEdges; i++)
			this.mNodeWeight[neighbor[i]] = 0;

		for (i = 0; i < nEdges; i++)
			this.mNodeWeight[neighbor[i]] += this.mEdgeWeight2[i];



	}

	public int[] getFirstNeighborIndex()
	{

		return this.mFirstNeighborIndex;

	}

	public int[] getNeighbor()
	{

		return this.neighbor;

	}

	public double[] getEdgeWeight()
	{

		return this.mEdgeWeight2;

	}

	public double[] getNodeWeight()
	{

		return this.mNodeWeight;

	}

	public int getPartAuthor(int i)
	{

		return this.mPartAuthor[i];

	}

	public Network getNetwork()
	{

		return new Network(nNodes, mFirstNeighborIndex, neighbor, mEdgeWeight2, mNodeWeight);


	}



}
