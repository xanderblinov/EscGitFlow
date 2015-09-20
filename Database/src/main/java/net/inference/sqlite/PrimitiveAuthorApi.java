package net.inference.sqlite;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;

import net.inference.database.IPrimitiveAuthorApi;
import net.inference.database.dto.IArticle;
import net.inference.database.dto.IAuthor;
import net.inference.database.dto.IPrimitiveAuthor;
import net.inference.database.dto.IPrimitiveAuthorToAuthor;
import net.inference.sqlite.dto.Article;
import net.inference.sqlite.dto.Author;
import net.inference.sqlite.dto.PrimitiveAuthor;
import net.inference.sqlite.dto.PrimitiveAuthorToAuthor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 13-Sep-15
 * Time: 11:20 PM
 *
 * @author xanderblinov
 */
public class PrimitiveAuthorApi extends BaseApi<PrimitiveAuthor, Integer> implements IPrimitiveAuthorApi
{
	private static Logger logger = LoggerFactory.getLogger(PrimitiveAuthor.class);

	private final DatagbasseApi mDatagbasseApi;


	public PrimitiveAuthorApi(DatagbasseApi datagbasseApi)
	{
		super(datagbasseApi, PrimitiveAuthor.class);
		mDatagbasseApi = datagbasseApi;
	}

	@Override
	public IPrimitiveAuthor addAuthor(final IPrimitiveAuthor author)
	{
		try
		{
			return getDao().createIfNotExists((PrimitiveAuthor) author);
		}
		catch (SQLException e)
		{
			logger.error(e, "");
		}
		return null;
	}


	@Override
	public IPrimitiveAuthorToAuthor addCoauthor(final PrimitiveAuthor author, final PrimitiveAuthor coauthor)
	{
		PrimitiveAuthorToAuthor primitiveAuthorToAuthor = new PrimitiveAuthorToAuthor(author, coauthor);
		try
		{
			return mDatagbasseApi.getPrimitiveAuthorToAuthorDao().createIfNotExists(primitiveAuthorToAuthor);
		}
		catch (SQLException e)
		{
			logger.error(e, "");
		}
		return null;
	}

	@Override
	public List<Article> findArticlesForAuthor(IAuthor author)
	{
		return null;
	}

	@Override
	public List<Author> findAuthorsForArticle(IArticle article)
	{
		return null;
	}

	@Override
	public ArrayList<PrimitiveAuthor> addAuthors(List<PrimitiveAuthor> primitiveAuthors) throws Exception
	{
		final Dao<PrimitiveAuthor, Integer> dao = getDao();

		//noinspection UnnecessaryLocalVariable
		final ArrayList<PrimitiveAuthor> authors = dao.callBatchTasks(() -> {

			ArrayList<PrimitiveAuthor> createdAuthors = new ArrayList<>();


			for (PrimitiveAuthor author : primitiveAuthors)
			{
				final PrimitiveAuthor primitiveAuthor = dao.createIfNotExists(author);
				createdAuthors.add(primitiveAuthor);
			}
			return createdAuthors;
		});
		return authors;
	}

	@Override
	public List<PrimitiveAuthorToAuthor> addCoauthors(List<PrimitiveAuthor> primitiveAuthors) throws Exception
	{
		List<PrimitiveAuthorToAuthor> primitiveAuthorToAuthors = new ArrayList<>();
		for (PrimitiveAuthor outer : primitiveAuthors)
		{
			for (PrimitiveAuthor inner : primitiveAuthors)
			{
				PrimitiveAuthorToAuthor primitiveAuthorToAuthor = new PrimitiveAuthorToAuthor(outer, inner);
				primitiveAuthorToAuthors.add(primitiveAuthorToAuthor);
			}
		}

		Dao<PrimitiveAuthorToAuthor, Integer> dao = mDatagbasseApi.<Integer>getPrimitiveAuthorToAuthorDao();

		//noinspection UnnecessaryLocalVariable
		final List<PrimitiveAuthorToAuthor> primitiveAuthorToAuthorList = dao.callBatchTasks(() -> {
			List<PrimitiveAuthorToAuthor> resultPrimitiveAuthorToAuthorList = new ArrayList<>();
			for (PrimitiveAuthorToAuthor authorToAuthor : primitiveAuthorToAuthors)
			{
				resultPrimitiveAuthorToAuthorList.add(dao.createIfNotExists(authorToAuthor));
			}
			return resultPrimitiveAuthorToAuthorList;
		});

		return primitiveAuthorToAuthorList;
	}


	private PreparedQuery<PrimitiveAuthor> buildCoauthorForAuthorQuery() throws SQLException
	{
		QueryBuilder<PrimitiveAuthorToAuthor, Integer> coauthorsQb;
		QueryBuilder<PrimitiveAuthor, Integer> authorQb;
		coauthorsQb = mDatagbasseApi.<Integer>getPrimitiveAuthorToAuthorDao().queryBuilder();
		coauthorsQb.selectColumns(IPrimitiveAuthorToAuthor.Column.coauthor);
		SelectArg authorSelectArg = new SelectArg();
		coauthorsQb.where().eq(IPrimitiveAuthorToAuthor.Column.author, authorSelectArg);


		authorQb = mDatagbasseApi.<Integer>getPrimitiveAuthorDao().queryBuilder();
		authorQb.where().in(IPrimitiveAuthor.Column.id, coauthorsQb);
		return authorQb.prepare();
	}

	private PreparedQuery<PrimitiveAuthor> coauthorForAuthorQuery;

	@Override
	public List<PrimitiveAuthor> findCoauthors(final IPrimitiveAuthor author)
	{
		try
		{
			if (coauthorForAuthorQuery == null)
			{
				coauthorForAuthorQuery = buildCoauthorForAuthorQuery();
			}
			coauthorForAuthorQuery.setArgumentHolderValue(0, author);
			return mDatagbasseApi.getPrimitiveAuthorDao().query(coauthorForAuthorQuery);

		}
		catch (SQLException ex)
		{
			logger.error(ex, "");
		}

		return null;
	}

	private PreparedQuery<Article> mNextUnprocessedArticleQuery;

	private final Object mNextUnassignedArticleLock = new Object();

	public List<PrimitiveAuthor> getNextUnassignedAuthors() throws SQLException
	{
		if (mNextUnprocessedArticleQuery == null)
		{
			mNextUnprocessedArticleQuery = buildNextUnprocessedArticleQuery();
		}
		final Article article;
		synchronized (mNextUnassignedArticleLock)
		{
			article = mDatagbasseApi.getArticleDao().queryForFirst(mNextUnprocessedArticleQuery);
			if (article != null)
			{
				article.setProcessed(true);
				mDatagbasseApi.getArticleDao().update(article);
			}
		}

		if (article == null)
		{
			return null;
		}

		return getDao().queryForEq(PrimitiveAuthor.Column.article, article);

	}

	private PreparedQuery<Article> buildNextUnprocessedArticleQuery() throws SQLException
	{
		return mDatagbasseApi.getArticleDao().queryBuilder().where().eq(Article.Column.processed_by_disambiguation_resolver, false).prepare();
	}

}
