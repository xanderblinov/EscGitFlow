package net.inference.database.dto;

/**
 * Date: 12/19/2014
 * Time: 3:15 PM
 *
 * @author xanderblinov
 */

public interface Author extends Entity
{
	public static final String TABLE_NAME = "author";

	public static class Column
	{
		public static final String id = "_id";
		public static final String surname = "surname";
		public static final String name = "name";
		public static final String encoding = "encoding";
		public static final String click = "click";
	}

	public String getName();

	public void setName(final String name);

	public String getSurname();

	public void setSurname(final String surname);

	public String getEncoding();

	public void setEncoding(final String encoding);

    String getClick();
    void setClick(String click);
}
