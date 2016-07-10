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
	@DatabaseField(columnName = Column.year)
	private int mYear;
	@DatabaseField(columnName = Column.publication, foreign = true)
	private Article mPublication;
	@DatabaseField(columnName = Column.term, foreign = true)
	private Term mTerm;






	public PrimitiveTerm()
	{
		// ORMLite needs a no-arg constructor
	}


	public PrimitiveTerm(String value, String type, int year, Article article)
	{
		mType = type;
		mValue = value;
		mPublication = article;
		mYear = year;
	}

	public int getId(){ return mId;}

	public String getValue()
	{
		return mValue;
	}

	public void setTerm(Term inputTerm)
	{
		this.mTerm=inputTerm;
	}

	public void setValue(String value){ mValue = value; }

	public String getTermValue(){return mTerm.getValue();}

	public Term getTerm(){ return mTerm; }

	public int getYear(){ return mYear; }

	public void setYear(int year){ mYear = year; }

	@Override
	public Article getPublication()
	{
		return mPublication;
	}

	@Override
	public void setPublication(String publication)
	{

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
