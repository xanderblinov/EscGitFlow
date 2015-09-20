package net.inference.database.dto;

/**
 * Date: 12/19/2014
 * Time: 3:15 PM
 *
 * @author xanderblinov
 */

public interface IArticle extends IEntity
{
	public static final String TABLE_NAME = "article";

	public static class Column
	{
		public static final String id = "_id";
		public static final String sourceId = "sourceId";
		public static final String name = "name";
		public static final String year = "year";
		public static final String source = "source";
    }
	public String getName();

	public void setName(final String name);

	public String getSourceId();

	public void setSourceId(final String sourceId);

	public int getYear();

	public void setYear(final int year);

	public int getSource();

	public void setSource(final int source);
}
