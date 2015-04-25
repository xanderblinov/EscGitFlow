package net.inference.database.dto;

/**
 * Date: 4/25/2015
 * Time: 4:22 PM
 *
 * @author xanderblinov
 */
public interface AuthorToArticle
{
	public static final String TABLE_NAME = "author_to_article";

	public static class Column
	{
		public static final String author_id = "author_id";
		public static final String article_id = "article_id";

	}
	long getId();

	Author getAuthor();

	void setAuthor(Author author);

	Article getArticle();

	void setArticle(Article article);
}
