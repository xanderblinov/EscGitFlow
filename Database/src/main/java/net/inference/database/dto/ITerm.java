package net.inference.database.dto;

/**
 * Date: 20-Sep-15
 * Time: 4:40 PM
 *
 * @author xanderblinov
 */
public interface ITerm
{
	String TABLE_NAME = "term";

	class Column
	{
		public static final String value = "value";
		public static final String id = "id";
		public static final String type = "type";
	}

	int getId();

	void setValue(final String value);

	String getValue();

	void setType(final String type);

	String getType();
}
