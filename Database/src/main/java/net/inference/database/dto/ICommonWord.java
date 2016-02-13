package net.inference.database.dto;

/**
 * Created by palen on 11.02.2016.
 */
public interface ICommonWord
{
	String TABLE_NAME = "commonWord";

	class Column
	{
		public static final String value = "value";
		public static final String id = "id";
		public static final String counter = "number of usages";
	}

	int getId();

	void setValue(final String value);

	String getValue();

	void incCounter();

}
