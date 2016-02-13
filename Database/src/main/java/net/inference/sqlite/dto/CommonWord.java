package net.inference.sqlite.dto;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import net.inference.database.dto.ICommonWord;


@DatabaseTable(tableName = ICommonWord.TABLE_NAME)
public class CommonWord implements ICommonWord
{

	@DatabaseField(columnName = Column.id, generatedId = true)
	private int mId;
	@DatabaseField(columnName = Column.value)
	private String mValue;
	@DatabaseField(columnName = Column.counter)
	private int mCounter = 1;

	public CommonWord()
	{
		// ORMLite needs a no-arg constructor
	}


	public CommonWord(String commonWord)
	{
		//mId = id;
		mValue = commonWord;
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

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null || getClass() != o.getClass())
		{
			return false;
		}

		CommonWord commonWord = (CommonWord) o;

		if (mValue != null ? !mValue.equals(commonWord.mValue) : commonWord.mValue != null)
		{
			return false;
		}
		return true;

	}
}

