package net.inference.sqlite;

import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;

import net.inference.database.IAuthorApi;
import net.inference.database.dto.IArticle;
import net.inference.database.dto.IAuthor;
import net.inference.database.dto.IAuthorToArticle;
import net.inference.database.dto.IAuthorToAuthor;
import net.inference.database.dto.IAuthorToCluster;
import net.inference.database.dto.IAuthorToCompany;
import net.inference.database.dto.ICluster;
import net.inference.database.dto.ICompany;
import net.inference.sqlite.dto.Article;
import net.inference.sqlite.dto.Author;
import net.inference.sqlite.dto.AuthorToArticle;
import net.inference.sqlite.dto.AuthorToAuthor;
import net.inference.sqlite.dto.AuthorToCluster;
import net.inference.sqlite.dto.AuthorToCompany;
import net.inference.sqlite.dto.Cluster;
import net.inference.sqlite.dto.Company;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author gzheyts
 */
public class AuthorApi extends BaseApi<Author, Integer> implements IAuthorApi
{
	private static Logger logger = LoggerFactory.getLogger(AuthorApi.class);
	private DatagbasseApi mDatagbasseApi;
	private PreparedQuery<Author> authorForClusterQuery;
	private PreparedQuery<Author> coauthorForAuthorQuery;

	public AuthorApi(DatagbasseApi datagbasseApi)
	{
		super(datagbasseApi, Author.class);
		this.mDatagbasseApi = datagbasseApi;
	}


	@Override
	public IAuthor addAuthor(final IAuthor author)
	{
		try
		{
			return getDao().createIfNotExists((Author) author);
		}
		catch (SQLException e)
		{
			logger.error(e, "");
		}
		return null;
	}

	@Override
	public List<Author> addAuthors(final List<Author> authors) throws Exception
	{

		return getDao().callBatchTasks((Callable<List<Author>>) () -> {

			List<Author> outAuthors = new ArrayList<>();

			for (Author author : authors)
			{
				outAuthors.add(getDao().createIfNotExists(author));
			}
			return outAuthors;
		});

	}

	@Override
	public boolean addAuthorToCluster(final Author author, final Cluster cluster)
	{

		AuthorToCluster authorToCluster = new AuthorToCluster(author, cluster);
		try
		{
			return mDatagbasseApi.getAuthorToClusterDao().create(authorToCluster) == 1;
		}
		catch (SQLException e)
		{
			SqliteLog.log(e);
		}
		return false;
	}

	@Override
	public IAuthorToAuthor addCoauthor(final Author author, final Author coauthor)
	{
		AuthorToAuthor authorToAuthor = new AuthorToAuthor(author, coauthor);
		try
		{
			return mDatagbasseApi.getAuthorToAuthorDao().createIfNotExists(authorToAuthor);
		}
		catch (SQLException e)
		{
			logger.error(e, "");
		}
		return null;
	}

	@Override
	public List<Author> findCoauthors(final IAuthor author)
	{
		try
		{
			if (coauthorForAuthorQuery == null)
			{
				coauthorForAuthorQuery = buildCoauthorForAuthorQuery();
			}
			coauthorForAuthorQuery.setArgumentHolderValue(0, author);
			return mDatagbasseApi.getInferenceAuthorDao().query(coauthorForAuthorQuery);

		}
		catch (SQLException ex)
		{
			logger.error(ex, "");
		}

		return null;
	}

	@Override
	public List<Author> findAuthorsForCluster(final ICluster cluster)
	{

		try
		{
			if (authorForClusterQuery == null)
			{
				authorForClusterQuery = buildAuthorForClusterQuery();
			}

			authorForClusterQuery.setArgumentHolderValue(0, cluster);

			return mDatagbasseApi.getInferenceAuthorDao().query(authorForClusterQuery);

		}
		catch (Exception e)
		{
			logger.error(e, "");
		}

		return null;
	}

	private PreparedQuery<Author> buildAuthorForClusterQuery() throws SQLException
	{

		QueryBuilder<AuthorToCluster, Integer> authorClusterQb;
		QueryBuilder<Author, Integer> authorQb;

		authorClusterQb = mDatagbasseApi.<Integer>getAuthorToClusterDao().queryBuilder();
		authorClusterQb.selectColumns(IAuthorToCluster.Column.author);
		SelectArg clusterSelectArg = new SelectArg();
		authorClusterQb.where().eq(IAuthorToCluster.Column.cluster, clusterSelectArg);


		authorQb = mDatagbasseApi.<Integer>getInferenceAuthorDao().queryBuilder();
		authorQb.where().in(IAuthor.Column.id, authorClusterQb);
		return authorQb.prepare();

	}

	private PreparedQuery<Author> buildCoauthorForAuthorQuery() throws SQLException
	{
		QueryBuilder<AuthorToAuthor, Integer> coauthorsQb;
		QueryBuilder<Author, Integer> authorQb;
		coauthorsQb = mDatagbasseApi.<Integer>getAuthorToAuthorDao().queryBuilder();
		coauthorsQb.selectColumns(IAuthorToAuthor.Column.coauthor);
		SelectArg authorSelectArg = new SelectArg();
		coauthorsQb.where().eq(IAuthorToAuthor.Column.author, authorSelectArg);


		authorQb = mDatagbasseApi.<Integer>getInferenceAuthorDao().queryBuilder();
		authorQb.where().in(IAuthor.Column.id, coauthorsQb);
		return authorQb.prepare();
	}

	private PreparedQuery<Article> articleForAuthorQuery = null;
	private PreparedQuery<Author> authorForArticleQuery = null;

	private PreparedQuery<Company> companyForAuthorQuery = null;
	private PreparedQuery<Author> authorForCompanyQuery = null;

	public List<Article> findArticlesForAuthor(IAuthor author)
	{
		try
		{
			if (articleForAuthorQuery == null)
			{
				articleForAuthorQuery = makeArticleForAuthorQuery();
			}
			articleForAuthorQuery.setArgumentHolderValue(0, author);
			return mDatagbasseApi.article().getDao().query(articleForAuthorQuery);
		}
		catch (SQLException e)
		{
			logger.error(e, "");
		}

		return null;
	}

	public List<Author> findAuthorsForArticle(IArticle article)
	{
		try
		{
			if (authorForArticleQuery == null)
			{
				authorForArticleQuery = makeAuthorForArticleQuery();
			}
			authorForArticleQuery.setArgumentHolderValue(0, article);
			return getDao().query(authorForArticleQuery);
		}
		catch (SQLException e)
		{
			logger.error(e, "");
		}
		return null;
	}

	public List<Company> findCompaniesForAuthor(IAuthor author)
	{
		try
		{
			if (companyForAuthorQuery == null)
			{
				companyForAuthorQuery = makeCompanyForAuthorQuery();
			}
			companyForAuthorQuery.setArgumentHolderValue(0, author);
			return mDatagbasseApi.getCompanyDao().query(companyForAuthorQuery);
		}
		catch (SQLException e)
		{
			logger.error(e, "");
		}

		return null;
	}

	public List<Author> findAuthorsForCompany(ICompany company)
	{
		try
		{
			if (authorForCompanyQuery == null)
			{
				authorForCompanyQuery = makeAuthorForCompanyQuery();
			}
			authorForCompanyQuery.setArgumentHolderValue(0, company);
			return getDao().query(authorForCompanyQuery);
		}
		catch (SQLException e)
		{
			logger.error(e, "");
		}
		return null;
	}

	@Override
	public List<AuthorToAuthor> addAuthorToAuthors(List<AuthorToAuthor> authorToAuthors) throws Exception
	{
		return mDatagbasseApi.getAuthorToAuthorDao().callBatchTasks(() -> {

			List<AuthorToAuthor> outAuthorToAuthors = new ArrayList<>();

			for (AuthorToAuthor authorToAuthor : authorToAuthors)
			{
				outAuthorToAuthors.add(mDatagbasseApi.getAuthorToAuthorDao().createIfNotExists(authorToAuthor));
			}
			return outAuthorToAuthors;
		});
	}


	/**
	 * Build our query for ArticleImpl objects that match a AuthorImpl.
	 */
	private PreparedQuery<Article> makeArticleForAuthorQuery() throws SQLException
	{
		QueryBuilder<AuthorToArticle, Integer> authorArticleQb = mDatagbasseApi.<Integer>getAuthorToArticleDao().queryBuilder();
		authorArticleQb.selectColumns(AuthorToArticle.Column.article_id);
		SelectArg authorSelectArg = new SelectArg();
		authorArticleQb.where().eq(IAuthorToArticle.Column.author, authorSelectArg);
		QueryBuilder<Article, Integer> articleQb = mDatagbasseApi.<Integer>getArticleDao().queryBuilder();
		articleQb.where().in(IArticle.Column.id, authorArticleQb);
		return articleQb.prepare();
	}

	/**
	 * Build our query for AuthorImpl objects that match a ArticleImpl
	 */
	private PreparedQuery<Author> makeAuthorForArticleQuery() throws SQLException
	{
		QueryBuilder<AuthorToArticle, Integer> authorArticleQb = mDatagbasseApi.<Integer>getAuthorToArticleDao().queryBuilder();
		authorArticleQb.selectColumns(IAuthorToArticle.Column.author);
		SelectArg articleSelectArg = new SelectArg();
		authorArticleQb.where().eq(IAuthorToArticle.Column.article_id, articleSelectArg);
		QueryBuilder<Author, Integer> authorQb = mDatagbasseApi.author().getDao().queryBuilder();
		authorQb.where().in(IArticle.Column.id, authorArticleQb);
		return authorQb.prepare();
	}

	/**
	 * Build our query for ArticleImpl objects that match a AuthorImpl.
	 */
	private PreparedQuery<Company> makeCompanyForAuthorQuery() throws SQLException
	{
		QueryBuilder<AuthorToCompany, Integer> authorCompanyQb = mDatagbasseApi.<Integer>getAuthorToCompanyDao().queryBuilder();
		authorCompanyQb.selectColumns(IAuthorToCompany.Column.company);
		SelectArg authorSelectArg = new SelectArg();
		authorCompanyQb.where().eq(IAuthorToCompany.Column.author, authorSelectArg);
		QueryBuilder<Company, Integer> companyQb = mDatagbasseApi.<Integer>getCompanyDao().queryBuilder();
		companyQb.where().in(IArticle.Column.id, authorCompanyQb);
		return companyQb.prepare();
	}

	/**
	 * Build our query for AuthorImpl objects that match a ArticleImpl
	 */
	private PreparedQuery<Author> makeAuthorForCompanyQuery() throws SQLException
	{
		QueryBuilder<AuthorToCompany, Integer> authorCompanyQb = mDatagbasseApi.<Integer>getAuthorToCompanyDao().queryBuilder();
		authorCompanyQb.selectColumns(IAuthorToArticle.Column.author);
		SelectArg companySelectArg = new SelectArg();
		authorCompanyQb.where().eq(IAuthorToCompany.Column.company, companySelectArg);
		QueryBuilder<Author, Integer> authorQb = mDatagbasseApi.author().getDao().queryBuilder();
		authorQb.where().in(ICompany.Column.id, authorCompanyQb);
		return authorQb.prepare();
	}

}
