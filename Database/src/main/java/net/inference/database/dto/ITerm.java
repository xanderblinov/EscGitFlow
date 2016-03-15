package net.inference.database.dto;

/**
 * Created by M.Pankova on 03.11.15.
 */
public interface ITerm
{
	String TABLE_NAME = "term";

	class Column
	{
		public static final String value = "value";
		public static final String id = "id";
		public static final String year = "year";
		public static final String counter = "Number of PT";
	}

	int getId();

	void setValue(final String value);

	String getValue();

	void incCounter();

	int getCount();

	void setYear(final int year);

	int getYear();
}

