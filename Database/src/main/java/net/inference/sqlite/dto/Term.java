package net.inference.sqlite.dto;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import net.inference.database.dto.ITerm;

/**
 * Date: 12/19/2014
 * Time: 3:15 PM
 *
 * @author xanderblinov
 */

@DatabaseTable(tableName = ITerm.TABLE_NAME)
public class Term implements ITerm
{
	@DatabaseField(columnName = Column.value, id = true)
	private String mValue;

	public Term()
	{
		// ORMLite needs a no-arg constructor
	}


	public Term(String value)
	{
		mValue = value;
	}

	public String getValue()
	{
		return mValue;
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
