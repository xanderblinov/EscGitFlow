package net.inference.database.dto;

import net.inference.sqlite.dto.ArticleSource;

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
		public static final String id_in_source = "id_in_source";
		public static final String title = "title";
		public static final String year = "year";
		public static final String source = "source";
		public static final String processed_by_disambiguation_resolver = "processed_by_disambiguation_resolver";
    }
	public String getTitle();

	public void setTitle(final String name);

	public String getIdInSource();

	public void setIdInSource(final String sourceId);

	public int getYear();

	public void setYear(final int year);

	public ArticleSource getArticleSource();

	public void setArticleSource(final ArticleSource articleSource);
}
