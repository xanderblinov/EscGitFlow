package net.inference.database.dto;

/**
 * Date: 12/19/2014
 * Time: 3:15 PM
 *
 * @author xanderblinov
 */

public interface PrimitiveCoAuthorship extends Entity
{
	public static final String TABLE_NAME = "primitive_co_authorship";

	public static class Column
	{
		public static final String id = "_id";
		public static final String author = "author";
		public static final String coauthor = "coauthor";
		public static final String year  = "year";
		public static final String article_id  = "article_id";
	}

	public String getAuthor();

	public void setAuthor(final String author);

	public String getCoauthor();

	public void setCoauthor(final String coauthor);

	public int getYear();

	public void setYear(final int year);

	public long getArticleId();

	public void setArticleId(final long articleId);

}
