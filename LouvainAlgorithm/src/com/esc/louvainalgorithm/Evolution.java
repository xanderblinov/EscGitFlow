package com.esc.louvainalgorithm;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "evolution")


public class Evolution
{

	@DatabaseField(columnName= "_id", generatedId = true)
	private int id;
	@DatabaseField(columnName= "type_id")
	private String typeId;
	@DatabaseField(columnName= "from_year")
	private String firstYear;
	@DatabaseField(columnName= "to_year")
	private String lastYear;

	public Evolution()
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

	public void setTypeId(String typeId)
	{

		this.typeId = typeId;

	}

	public String getTypeId()
	{

		return typeId;

	}

	public void setFirstYear(String firstYear)
	{

		this.firstYear = firstYear;

	}

	public String getFirstYear()
	{

		return firstYear;

	}

	public void setLastYear(String lastYear)
	{

		this.lastYear = lastYear;

	}

	public String getLastYear()
	{

		return lastYear;

	}
}
