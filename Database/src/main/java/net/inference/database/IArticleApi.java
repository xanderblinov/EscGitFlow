package net.inference.database;

import net.inference.sqlite.dto.Article;

import java.sql.SQLException;

/**
 * Date: 12/23/2014
 * Time: 11:23 PM
 *
 * @author xanderblinov
 */
public interface IArticleApi extends IBaseApi<Article,Integer>
{
	Article addArticle(Article article) throws SQLException;

	boolean exists(Article article) throws SQLException;
}
