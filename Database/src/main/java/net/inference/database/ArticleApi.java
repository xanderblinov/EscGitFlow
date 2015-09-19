package net.inference.database;

import net.inference.sqlite.dto.ArticleImpl;

import java.sql.SQLException;

/**
 * Date: 12/23/2014
 * Time: 11:23 PM
 *
 * @author xanderblinov
 */
public interface ArticleApi extends BaseApi<ArticleImpl,Integer>
{
	ArticleImpl addArticle(ArticleImpl article) throws SQLException;

	boolean exists(ArticleImpl article) throws SQLException;
}
