package net.inference.sqlite.dto;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import net.inference.database.dto.ITerm;

/**
 * Created by M.Pankova on 03.11.15.
 */

@DatabaseTable(tableName = ITerm.TABLE_NAME)
public class Term implements ITerm
{

	@DatabaseField(columnName = Column.id, generatedId = true)
	private int mId;
	@DatabaseField(columnName = Column.value)
	private String mValue;
	@DatabaseField(columnName = Column.counter)
	private double mCounter = 0.0;

	public Term()
	{
		// ORMLite needs a no-arg constructor
	}

	public Term(PrimitiveTerm termArg)
	{
		mValue = termArg.getValue();
	}

	public Term(String termArg)
	{
		mValue = termArg;
	}

	/*public Term(int id, String val, int count)
	{
		mId=id;
		mValue = val;
		mCounter = count;
	}*/

	public int getId()
	{
		return mId;
	}

	public String getValue()
	{
		return mValue;
	}

	public void setValue(final String value)
	{
		mValue = value;
	}

	public void incCount()
	{
		mCounter++;
	}

	public double getCount()
	{
		return mCounter;
	}

	;

	public void addCount(double add)
	{
		mCounter = mCounter + add;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof Term))
		{
			return false;
		}

		Term term = (Term) o;

		return !(getValue() != null ? !getValue().equals(term.getValue()) : term.getValue() != null);

	}
}
