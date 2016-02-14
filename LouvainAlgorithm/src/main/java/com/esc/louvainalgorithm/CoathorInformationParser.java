package com.esc.louvainalgorithm;


import java.sql.SQLException;
import java.util.Arrays;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

public class CoathorInformationParser
{

	private Dao<AuthorToAuthor, Integer> coauthorDao;
	private int nLines, nNodes;
	private int[] firstNeighborIndex, neighbor, nNeighbors, node1, node2, partauthor;
	double[] edgeWeight1, edgeWeight2, nodeWeight;


	public CoathorInformationParser(ConnectionSource source) throws SQLException
	{

		coauthorDao = DaoManager.createDao(source, AuthorToAuthor.class);

	}

/*
	public void countLines() throws SQLException
	{

		int nLines = 0;
		while(coauthorDao.queryForId(nLines + 1) != null)
		nLines++;


	}*/

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
/*
	public void countNodes() throws SQLException
	{

		int nNodes = 0;
		int i = 0;
		while((coauthorDao.queryForId(i + 1) != null))
		{
			if (coauthorDao.queryForId(i + 1).getAuthor() > nNodes)
				nNodes = coauthorDao.queryForId(i + 1).getAuthor();
			i++;
		}

	}*/

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
		this.node1 = new int[nLines];
		this.node2 = new int[nLines];
		this.edgeWeight1 = new double[nLines];

		for(i = 0; i < nLines; i++)
			this.edgeWeight1[i] = 0;

		for (i = 0; i < nLines; i++)
		{

			this.node1[i] = coauthorDao.queryForId(i + 1).getAuthor()- 1;
			this.node2[i] = coauthorDao.queryForId(i + 1).getCoauthor() - 1;
			this.edgeWeight1[i]++;

		}

		this.partauthor = new int[nNodes];
		for(i = 0; i < nNodes; i++)
			this.partauthor[i] = 0;

		i = 0;
		j = 0;
		temp = 1;

		while(i < 1)
		{

			if((this.partauthor[i] < temp) && (coauthorDao.queryForId(j + 1).getYear() == year))
			{
				this.partauthor[i] = temp;
				i++;
			}

			temp = coauthorDao.queryForId(j + 1).getAuthor();
			j++;

		}

		while(i < nNodes)
		{

			if((this.partauthor[i - 1] < temp) && (coauthorDao.queryForId(j + 1).getYear() == year))
			{
				this.partauthor[i] = temp;
				i++;
			}

			temp = coauthorDao.queryForId(j + 1).getAuthor();
			j++;

		}



		this.nNeighbors = new int[nNodes];
		for (i = 0; i < nLines; i++)
			if (node1[i] < node2[i])
			{
				this.nNeighbors[node1[i]]++;
				this.nNeighbors[node2[i]]++;
			}

		this.firstNeighborIndex = new int[nNodes + 1];
		nEdges = 0;

		for (i = 0; i < nNodes; i++)
		{

			this.firstNeighborIndex[i] = nEdges;
			nEdges += nNeighbors[i];

		}
		this.firstNeighborIndex[nNodes] = nEdges;

		this.neighbor = new int[nEdges];
		this.edgeWeight2 = new double[nEdges];
		Arrays.fill(nNeighbors, 0);
		for (i = 0; i < nLines; i++)
			if (node1[i] < node2[i])
			{
				j = this.firstNeighborIndex[node1[i]] + this.nNeighbors[node1[i]];
				this.neighbor[j] = this.node2[i];
				this.edgeWeight2[j] = this.edgeWeight1[i];
				this.nNeighbors[node1[i]]++;
				j = firstNeighborIndex[node2[i]] + this.nNeighbors[node2[i]];
				this.neighbor[j] = this.node1[i];
				this.edgeWeight2[j] = this.edgeWeight1[i];
				this.nNeighbors[node2[i]]++;
			}


		this.nodeWeight = new double[nNodes];

		for (i = 0; i < nEdges; i++)
			this.nodeWeight[neighbor[i]] = 0;

		for (i = 0; i < nEdges; i++)
			this.nodeWeight[neighbor[i]] += this.edgeWeight2[i];



	}

	public int[] getFirstNeighborIndex()
	{

		return this.firstNeighborIndex;

	}

	public int[] getNeighbor()
	{

		return this.neighbor;

	}

	public double[] getEdgeWeight()
	{

		return this.edgeWeight2;

	}

	public double[] getNodeWeight()
	{

		return this.nodeWeight;

	}

	public int getPartauthor(int i)
	{

		return this.partauthor[i];

	}

	public Network getNetwork()
	{

		Network temp = new Network(nNodes, firstNeighborIndex, neighbor, edgeWeight2, nodeWeight);
		return temp;


	}



}
