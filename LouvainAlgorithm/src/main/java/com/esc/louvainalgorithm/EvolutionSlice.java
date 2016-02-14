package com.esc.louvainalgorithm;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "evolution_slice")


public class EvolutionSlice
{

	@DatabaseField(columnName= "_id", generatedId = true)
	private int id;
	@DatabaseField(columnName= "year")
	private String year;
	@DatabaseField(columnName= "evolution_id")
	private int evolutionId;

	public EvolutionSlice()
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

	public void setYear(String year)
	{

		this.year = year;

	}

	public String getYear()
	{

		return year;

	}

	public void setEvolutionId(int evolutionId)
	{

		this.evolutionId = evolutionId;

	}

	public int getEvolutionId()
	{

		return evolutionId;

	}
}
