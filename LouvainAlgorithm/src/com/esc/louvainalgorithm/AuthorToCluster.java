package com.esc.louvainalgorithm;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "author_to_cluster")


public class AuthorToCluster
{

	@DatabaseField(columnName= "_id", generatedId = true)
	private int id;
	@DatabaseField
	private int author;
	@DatabaseField
	private int cluster;

	public AuthorToCluster()
	{

	}

	public void setId(int id)
	{

		this.id = id;

	}

	public int getId()
	{

		return id;

	}

	public void setAuthor(int author)
	{

		this.author = author;

	}

	public int getAuthor()
	{

		return author;

	}

	public void setCluster(int cluster)
	{

		this.cluster = cluster;

	}

	public int getCluster()
	{

		return cluster;

	}
}
