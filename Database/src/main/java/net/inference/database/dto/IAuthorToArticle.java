package net.inference.database.dto;

/**
 * Date: 4/25/2015
 * Time: 4:22 PM
 *
 * @author xanderblinov
 */
public interface IAuthorToArticle
{
	public static final String TABLE_NAME = "author_to_article";

	public static class Column
	{
		public static final String author_id = "author_id";
		public static final String article_id = "article_id";

	}
	long getId();

	IAuthor getAuthor();

	void setAuthor(IAuthor author);

	IArticle getArticle();

	void setArticle(IArticle article);
}
