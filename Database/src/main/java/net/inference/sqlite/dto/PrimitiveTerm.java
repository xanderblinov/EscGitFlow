package net.inference.sqlite.dto;

import java.util.ArrayList;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import net.inference.database.dto.IPrimitiveTerm;

/**
 * Date: 12/19/2014
 * Time: 3:15 PM
 *
 * @author xanderblinov
 */

@DatabaseTable(tableName = IPrimitiveTerm.TABLE_NAME)
public class PrimitiveTerm implements IPrimitiveTerm
{
	@DatabaseField(columnName = Column.id, generatedId = true)
	private int mId;
	@DatabaseField(columnName = Column.value)
	private String mValue;
	@DatabaseField(columnName = Column.type)
	private String mType;
	@DatabaseField(columnName = Column.date)
	private String mDate;
	@DatabaseField(columnName = Column.publication)
	private String mPublication;




	public PrimitiveTerm()
	{
		// ORMLite needs a no-arg constructor
	}


	public PrimitiveTerm(String value)
	{
		mValue = value;
	}

	public String getValue()
	{
		return mValue;
	}

	@Override
	public String getPublication()
	{
		return null;
	}

	@Override
	public void setPublication(String publication)
	{

	}

	@Override
	public String getNeighbors()
	{
		return null;
	}

	@Override
	public void addNeighbors(String[] neighbors)
	{

	}

	@Override
	public boolean existNeighbor(String neighbor)
	{
		return false;
	}

	public ArrayList<String> separatePrimitiveTerms()
	{
		ArrayList<String> createdPrimitiveTerms = new ArrayList<>();
		String[] primitiveTermsArr = this.getValue().split(",");
		for (int i = 0; i < primitiveTermsArr.length; i++)
			createdPrimitiveTerms.add(primitiveTermsArr[i]);
		return createdPrimitiveTerms;
	}


	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof PrimitiveTerm))
		{
			return false;
		}

		PrimitiveTerm term = (PrimitiveTerm) o;

		return !(getValue() != null ? !getValue().equals(term.getValue()) : term.getValue() != null);

	}

	@Override
	public int hashCode()
	{
		return getValue() != null ? getValue().hashCode() : 0;
	}

	@Override
	public String toString()
	{
		return mValue;
	}
}
