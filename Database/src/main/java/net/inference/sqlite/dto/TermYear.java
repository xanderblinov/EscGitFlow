package net.inference.sqlite.dto;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import net.inference.database.dto.ITermYear;

/**
 * Created by Мария on 16.04.2016.
 */

@DatabaseTable(tableName = ITermYear.TABLE_NAME)
public class TermYear
{

	@DatabaseField(columnName = ITermYear.Column.id, generatedId = true)
	private int mId;
	@DatabaseField(columnName = ITermYear.Column.term, foreign = true)
	private Term mTerm;
	@DatabaseField(columnName = ITermYear.Column.year)
	private int mYear;
	@DatabaseField(columnName = ITermYear.Column.count)
	private int mCount = 1;

	public TermYear()
	{
		// ORMLite needs a no-arg constructor
	}

	public TermYear(Term term, int year)
	{
		mTerm = term;
		mYear = year;
	}

	public int getId(){ return mId; }

	public void setTerm(final Term term){ mTerm = term; }

	public Term getTerm(){ return mTerm; }

	public void setYear(final int year){ mYear = year; }

	public int getYear(){ return mYear; }

	public void incCounter(){ mCount++; }

	public int getCount(){ return mCount; }

	public void addCount(int add){ mCount = mCount + add;};

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof TermYear))
		{
			return false;
		}

		TermYear term = (TermYear) o;

		if (getTerm() != term.getTerm())
		{
			return false;
		}
		if (getYear() != term.getYear())
		{
			return false;
		}
		return true;
	}

}
