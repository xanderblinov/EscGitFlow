package net.inference.database.dto;

/**
 * Date: 4/25/2015
 * Time: 4:00 PM
 *
 * @author xanderblinov
 */
public interface ICompany extends IEntity
{
	public static final String TABLE_NAME = "company";

	public static class Column
	{
		public static final String id = "_id";
		public static final String name = "name";
		public static final String articleId = "articleId";
	}

	public String getName();

	public void setName(final String name);

	public long getArticleId();

	public void setArticleId(final long id);
}
