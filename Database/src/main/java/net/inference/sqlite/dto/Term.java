package net.inference.sqlite.dto;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import net.inference.database.dto.ITerm;


@DatabaseTable(tableName = ITerm.TABLE_NAME)
public class Term implements ITerm
{

	@DatabaseField(columnName = Column.id, id = true)
	private int mId;
	@DatabaseField(columnName = Column.value)
	private String mValue;
	@DatabaseField(columnName = Column.counter)
	private int mCounter=1;

	public Term()
	{
		// ORMLite needs a no-arg constructor
	}


	public Term(int id, PrimitiveTerm termArg)
	{
		mId=id;
		mValue = termArg.getValue();
	}

	public  int getId(){ return mId; }

	public void setValue(final String value){ mValue = value; }

	public String getValue()
	{
		return mValue;
	}

	public void incCounter()
	{
		mCounter++;
	}
}
