package net.inference.sqlite;

import net.inference.database.ArticleApi;
import net.inference.sqlite.dto.ArticleImpl;

/**
 * Date: 12/23/2014
 * Time: 11:25 PM
 *
 * @author xanderblinov
 */
public class ArticleApiImpl extends BaseApiImpl<ArticleImpl,Integer> implements ArticleApi
{
	private final SqliteApi mSqliteApi;

	public ArticleApiImpl(final SqliteApi sqliteApi) {
		super(sqliteApi, ArticleImpl.class);
		mSqliteApi = sqliteApi;
	}

}
