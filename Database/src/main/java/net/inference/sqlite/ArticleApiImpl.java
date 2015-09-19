package net.inference.sqlite;

import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;

import net.inference.database.ArticleApi;
import net.inference.database.dto.Article;
import net.inference.sqlite.dto.ArticleImpl;

import java.sql.SQLException;

/**
 * Date: 12/23/2014
 * Time: 11:25 PM
 *
 * @author xanderblinov
 */
public class ArticleApiImpl extends BaseApiImpl<ArticleImpl, Integer> implements ArticleApi
{
	private final SqliteApi mSqliteApi;
	private static Logger logger = LoggerFactory.getLogger(ArticleImpl.class);

	public ArticleApiImpl(final SqliteApi sqliteApi)
	{
		super(sqliteApi, ArticleImpl.class);
		mSqliteApi = sqliteApi;
	}

	@Override
	public ArticleImpl addArticle(ArticleImpl article) throws SQLException
	{
		return getDao().createIfNotExists(article);
	}

	@Override
	public boolean exists(ArticleImpl article) throws SQLException
	{
		return
		getDao().queryForFirst(getDao().queryBuilder().where().eq(Article.Column.source, article.getSource()).and().eq(Article.Column.sourceId, article.getSourceId()).prepare()) !=
		null;
	}
}
