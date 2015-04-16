package net.inference.sqlite.dto;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import net.inference.database.dto.Parameter;

/**
 * Date: 2/1/2015
 * Time: 5:53 PM
 *
 * @author xanderblinov
 */
@DatabaseTable(tableName = Parameter.TABLE_NAME)
public class ParameterImpl implements Parameter
{     
	
	@DatabaseField(columnName = Column.id, generatedId = true)
	private int mId;
	@DatabaseField(columnName = Column.name)
	private String mName;
	@DatabaseField(columnName = Column.description)
	private String mSurname;
	@DatabaseField(columnName = Column.value)
	private String mValue;

	@Override
	public String getName()
	{
		return mName;
	}

	@Override
	public void setName(final String name)
	{
		mName = name;
	}

	public String getSurname()
	{
		return mSurname;
	}

	public void setSurname(final String surname)
	{
		mSurname = surname;
	}

	public String getValue()
	{
		return mValue;
	}

	public void setValue(final String value)
	{
		mValue = value;
	}

    @Override
    public long getId() {
        return mId;
    }


	@Override
	public String toString() {
		return "ParameterImpl{" +
				"mId=" + mId +
				", mName='" + mName + '\'' +
				", mSurname='" + mSurname + '\'' +
				", mValue='" + mValue + '\'' +
				'}';
	}
}
