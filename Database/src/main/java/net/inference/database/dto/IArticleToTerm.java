package net.inference.database.dto;

/**
 * Date: 20-Sep-15
 * Time: 4:48 PM
 *
 * @author xanderblinov
 */
public interface IArticleToTerm extends IEntity
{
	String TABLE_NAME = "article_to_term";

	class Column
	{
		public static final String id = "_id";
		public static final String article = "article";
		public static final String term = "term";
	}
}
