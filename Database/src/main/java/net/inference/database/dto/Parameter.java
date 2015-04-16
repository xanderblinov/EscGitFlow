package net.inference.database.dto;

/**
 * Date: 12/19/2014
 * Time: 3:15 PM
 *
 * @author xanderblinov
 */

public interface Parameter extends Entity
{
	public static final String TABLE_NAME = "parameter";

	public static class Column
	{
		public static final String id = "_id";
		public static final String name = "name";
		public static final String description = "description";
		public static final String value = "value";
	}
	public String getName();

	public void setName(final String name);

	public String getSurname();

	public void setSurname(final String surname);

	public String getValue();

	public void setValue(final String value);
}
