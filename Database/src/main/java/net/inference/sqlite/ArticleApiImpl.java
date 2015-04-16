package net.inference.sqlite;

import java.sql.SQLException;
import java.util.List;

import net.inference.database.ArticleApi;
import net.inference.database.dto.Article;
import net.inference.sqlite.dto.ArticleImpl;

/**
 * Date: 12/23/2014
 * Time: 11:25 PM
 *
 * @author xanderblinov
 */
public class ArticleApiImpl implements ArticleApi
{
	private final SqliteApi mSqliteApi;

	public ArticleApiImpl(final SqliteApi sqliteApi)
	{
		mSqliteApi = sqliteApi;
	}

	@Override
	public void addArticle(final Article article)
	{
		try
		{
			mSqliteApi.getArticleDao().createIfNotExists((ArticleImpl) article);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}


	@Override
	public List<ArticleImpl> getAllArticles()
	{

		try
		{
			return mSqliteApi.getArticleDao().queryForAll();
		}
		catch (SQLException e)
		{
			SqliteLog.log(e);
		}

		return null;
	}
}
