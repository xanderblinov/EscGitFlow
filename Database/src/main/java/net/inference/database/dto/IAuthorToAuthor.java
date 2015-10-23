package net.inference.database.dto;

import net.inference.sqlite.dto.Author;

/**
 * Date: 12/19/2014
 * Time: 3:15 PM
 *
 * @author xanderblinov
 */

public interface IAuthorToAuthor extends IEntity
{
	public static final String TABLE_NAME = "author_to_author";

	public static class Column
	{
		public static final String id = "_id";
		public static final String author = "author";
		public static final String coauthor = "coauthor";
		public static final String year  = "year";
		public static final String article_id  = "article_id";
	}


    IAuthor getAuthor();


    void setAuthor(Author author);

    IAuthor getCoauthor();

    void setCoauthor(Author coauthor);

	public int getYear();

	public void setYear(final int year);

	public long getArticleId();

	public void setArticleId(final long articleId);
}
