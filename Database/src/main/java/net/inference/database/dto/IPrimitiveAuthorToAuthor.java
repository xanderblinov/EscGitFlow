package net.inference.database.dto;

import net.inference.sqlite.dto.PrimitiveAuthor;

/**
 * Date: 12/19/2014
 * Time: 3:15 PM
 *
 * @author xanderblinov
 */

public interface IPrimitiveAuthorToAuthor extends IEntity
{
	public static final String TABLE_NAME = "primitive_author_to_author";

	public static class Column
	{
		public static final String id = "_id";
		public static final String author = "author";
		public static final String coauthor = "coauthor";
		public static final String year  = "year";
		public static final String article_id  = "article_id";
	}

	public PrimitiveAuthor getAuthor();

	public void setAuthor(final PrimitiveAuthor author);

	public PrimitiveAuthor getCoauthor();

	public void setCoauthor(final PrimitiveAuthor coauthor);

	public int getYear();

	public void setYear(final int year);

	public long getArticleId();

	public void setArticleId(final long articleId);

}
