package net.inference.sqlite;

import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;

import net.inference.database.PrimitiveAuthorApi;
import net.inference.database.dto.Article;
import net.inference.database.dto.Author;
import net.inference.database.dto.PrimitiveAuthor;
import net.inference.database.dto.PrimitiveCoAuthorship;
import net.inference.sqlite.dto.ArticleImpl;
import net.inference.sqlite.dto.AuthorImpl;
import net.inference.sqlite.dto.PrimitiveAuthorImpl;
import net.inference.sqlite.dto.PrimitiveCoAuthorshipImpl;

import java.sql.SQLException;
import java.util.List;

/**
 * Date: 13-Sep-15
 * Time: 11:20 PM
 *
 * @author xanderblinov
 */
public class PrimitiveAuthorApiImpl extends BaseApiImpl<PrimitiveAuthorImpl, Integer> implements PrimitiveAuthorApi
{
	private static Logger logger = LoggerFactory.getLogger(AuthorApiImpl.class);

	private final SqliteApi mSqliteApi;


	public PrimitiveAuthorApiImpl(SqliteApi sqliteApi)
	{
		super(sqliteApi, PrimitiveAuthorImpl.class);
		mSqliteApi = sqliteApi;
	}

	@Override
	public PrimitiveAuthor addAuthor(final PrimitiveAuthor author)
	{
		try
		{
			return getDao().createIfNotExists((PrimitiveAuthorImpl) author);
		}
		catch (SQLException e)
		{
			logger.error(e, "");
		}
		return null;
	}


	@Override
	public PrimitiveCoAuthorship addCoauthor(final PrimitiveAuthor author, final PrimitiveAuthor coauthor)
	{
		PrimitiveCoAuthorshipImpl coAuthorship = new PrimitiveCoAuthorshipImpl(author, coauthor);
		try
		{
			return mSqliteApi.getPrimitiveCoAuthorshipDao().createIfNotExists(coAuthorship);
		}
		catch (SQLException e)
		{
			logger.error(e, "");
		}
		return null;
	}

	@Override
	public List<ArticleImpl> findArticlesForAuthor(Author author)
	{
		return null;
	}

	@Override
	public List<AuthorImpl> findAuthorsForArticle(Article article)
	{
		return null;
	}


	private PreparedQuery<PrimitiveAuthorImpl> buildCoauthorForAuthorQuery() throws SQLException
	{
		QueryBuilder<PrimitiveCoAuthorshipImpl, Integer> coauthorsQb;
		QueryBuilder<PrimitiveAuthorImpl, Integer> authorQb;
		coauthorsQb = mSqliteApi.<Integer>getPrimitiveCoAuthorshipDao().queryBuilder();
		coauthorsQb.selectColumns(PrimitiveCoAuthorship.Column.coauthor);
		SelectArg authorSelectArg = new SelectArg();
		coauthorsQb.where().eq(PrimitiveCoAuthorship.Column.author, authorSelectArg);


		authorQb = mSqliteApi.<Integer>getPrimitiveAuthorDao().queryBuilder();
		authorQb.where().in(PrimitiveAuthor.Column.id, coauthorsQb);
		return authorQb.prepare();
	}

	private PreparedQuery<PrimitiveAuthorImpl> coauthorForAuthorQuery;

	@Override
	public List<PrimitiveAuthorImpl> findCoauthors(final PrimitiveAuthor author)
	{
		try
		{
			if (coauthorForAuthorQuery == null)
			{
				coauthorForAuthorQuery = buildCoauthorForAuthorQuery();
			}
			coauthorForAuthorQuery.setArgumentHolderValue(0, author);
			return mSqliteApi.getPrimitiveAuthorDao().query(coauthorForAuthorQuery);

		}
		catch (SQLException ex)
		{
			logger.error(ex, "");
		}

		return null;
	}

}
