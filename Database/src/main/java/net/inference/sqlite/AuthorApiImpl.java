package net.inference.sqlite;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import net.inference.database.AuthorApi;
import net.inference.database.dto.Article;
import net.inference.database.dto.Author;
import net.inference.database.dto.AuthorToArticle;
import net.inference.database.dto.AuthorToCluster;
import net.inference.database.dto.AuthorToCompany;
import net.inference.database.dto.Cluster;
import net.inference.database.dto.CoAuthorship;
import net.inference.database.dto.Company;
import net.inference.sqlite.dto.ArticleImpl;
import net.inference.sqlite.dto.AuthorImpl;
import net.inference.sqlite.dto.AuthorToArticleImpl;
import net.inference.sqlite.dto.AuthorToClusterImpl;
import net.inference.sqlite.dto.AuthorToCompanyImpl;
import net.inference.sqlite.dto.CoAuthorshipImpl;
import net.inference.sqlite.dto.CompanyImpl;

/**
 * @author gzheyts
 */
public class AuthorApiImpl extends BaseApiImpl<AuthorImpl, Integer> implements AuthorApi
{
	private static Logger logger = LoggerFactory.getLogger(AuthorApiImpl.class);
	private SqliteApi sqliteApi;
	private PreparedQuery<AuthorImpl> authorForClusterQuery;
	private PreparedQuery<AuthorImpl> coauthorForAuthorQuery;

	public AuthorApiImpl(SqliteApi sqliteApi)
	{
		super(sqliteApi, AuthorImpl.class);
		this.sqliteApi = sqliteApi;
	}


	@Override
	public Author addAuthor(final Author author)
	{
		try
		{
			return getDao().createIfNotExists((AuthorImpl) author);
		}
		catch (SQLException e)
		{
			logger.error(e, "");
		}
		return null;
	}


	@Override
	public boolean addAuthorToCluster(final Author author, final Cluster cluster)
	{

		AuthorToClusterImpl authorToCluster = new AuthorToClusterImpl(author, cluster);
		try
		{
			return sqliteApi.getAuthorToClusterDao().create(authorToCluster) == 1;
		}
		catch (SQLException e)
		{
			SqliteLog.log(e);
		}
		return false;
	}

	@Override
	public CoAuthorship addCoauthor(final Author author, final Author coauthor)
	{
		CoAuthorshipImpl coAuthorship = new CoAuthorshipImpl(author, coauthor);
		try
		{
			return sqliteApi.getInferenceCoAuthorshipDao().createIfNotExists(coAuthorship);
		}
		catch (SQLException e)
		{
			logger.error(e, "");
		}
		return null;
	}

	@Override
	public List<AuthorImpl> findCoauthors(final Author author)
	{
		try
		{
			if (coauthorForAuthorQuery == null)
			{
				coauthorForAuthorQuery = buildCoauthorForAuthorQuery();
			}
			coauthorForAuthorQuery.setArgumentHolderValue(0, author);
			return sqliteApi.getInferenceAuthorDao().query(coauthorForAuthorQuery);

		}
		catch (SQLException ex)
		{
			logger.error(ex, "");
		}

		return null;
	}

	@Override
	public List<AuthorImpl> findAuthorsForCluster(final Cluster cluster)
	{

		try
		{
			if (authorForClusterQuery == null)
			{
				authorForClusterQuery = buildAuthorForClusterQuery();
			}

			authorForClusterQuery.setArgumentHolderValue(0, cluster);

			return sqliteApi.getInferenceAuthorDao().query(authorForClusterQuery);

		}
		catch (Exception e)
		{
			logger.error(e, "");
		}

		return null;
	}

	private PreparedQuery<AuthorImpl> buildAuthorForClusterQuery() throws SQLException
	{

		QueryBuilder<AuthorToClusterImpl, Integer> authorClusterQb;
		QueryBuilder<AuthorImpl, Integer> authorQb;

		authorClusterQb = sqliteApi.<Integer>getAuthorToClusterDao().queryBuilder();
		authorClusterQb.selectColumns(AuthorToCluster.Column.author_id);
		SelectArg clusterSelectArg = new SelectArg();
		authorClusterQb.where().eq(AuthorToCluster.Column.cluster_id, clusterSelectArg);


		authorQb = sqliteApi.<Integer>getInferenceAuthorDao().queryBuilder();
		authorQb.where().in(Author.Column.id, authorClusterQb);
		return authorQb.prepare();

	}

	private PreparedQuery<AuthorImpl> buildCoauthorForAuthorQuery() throws SQLException
	{
		QueryBuilder<CoAuthorshipImpl, Integer> coauthorsQb;
		QueryBuilder<AuthorImpl, Integer> authorQb;
		coauthorsQb = sqliteApi.<Integer>getInferenceCoAuthorshipDao().queryBuilder();
		coauthorsQb.selectColumns(CoAuthorship.Column.coauthor);
		SelectArg authorSelectArg = new SelectArg();
		coauthorsQb.where().eq(CoAuthorship.Column.author, authorSelectArg);


		authorQb = sqliteApi.<Integer>getInferenceAuthorDao().queryBuilder();
		authorQb.where().in(Author.Column.id, coauthorsQb);
		return authorQb.prepare();
	}

	private PreparedQuery<ArticleImpl> articleForAuthorQuery = null;
	private PreparedQuery<AuthorImpl> authorForArticleQuery = null;

	private PreparedQuery<CompanyImpl> companyForAuthorQuery = null;
	private PreparedQuery<AuthorImpl> authorForCompanyQuery = null;

	public List<ArticleImpl> findArticlesForAuthor(Author author)
	{
		try
		{
			if (articleForAuthorQuery == null)
			{
				articleForAuthorQuery = makeArticleForAuthorQuery();
			}
			articleForAuthorQuery.setArgumentHolderValue(0, author);
			return sqliteApi.article().getDao().query(articleForAuthorQuery);
		}
		catch (SQLException e)
		{
			logger.error(e, "");
		}

		return null;
	}

	public List<AuthorImpl> findAuthorsForArticle(Article article)
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

	public List<CompanyImpl> findCompaniesForAuthor(Author author)
	{
		try
		{
			if (companyForAuthorQuery == null)
			{
				companyForAuthorQuery = makeCompanyForAuthorQuery();
			}
			companyForAuthorQuery.setArgumentHolderValue(0, author);
			return sqliteApi.getCompanyDao().query(companyForAuthorQuery);
		}
		catch (SQLException e)
		{
			logger.error(e, "");
		}

		return null;
	}

	public List<AuthorImpl> findAuthorsForCompany(Company company)
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


	/**
	 * Build our query for ArticleImpl objects that match a AuthorImpl.
	 */
	private PreparedQuery<ArticleImpl> makeArticleForAuthorQuery() throws SQLException
	{
		QueryBuilder<AuthorToArticleImpl, Integer> authorArticleQb = sqliteApi.<Integer>getAuthorToArticleDao().queryBuilder();
		authorArticleQb.selectColumns(AuthorToArticleImpl.Column.article_id);
		SelectArg authorSelectArg = new SelectArg();
		authorArticleQb.where().eq(AuthorToArticle.Column.author_id, authorSelectArg);
		QueryBuilder<ArticleImpl, Integer> articleQb = sqliteApi.<Integer>getArticleDao().queryBuilder();
		articleQb.where().in(Article.Column.id, authorArticleQb);
		return articleQb.prepare();
	}

	/**
	 * Build our query for AuthorImpl objects that match a ArticleImpl
	 */
	private PreparedQuery<AuthorImpl> makeAuthorForArticleQuery() throws SQLException
	{
		QueryBuilder<AuthorToArticleImpl, Integer> authorArticleQb = sqliteApi.<Integer>getAuthorToArticleDao().queryBuilder();
		authorArticleQb.selectColumns(AuthorToArticle.Column.author_id);
		SelectArg articleSelectArg = new SelectArg();
		authorArticleQb.where().eq(AuthorToArticle.Column.article_id, articleSelectArg);
		QueryBuilder<AuthorImpl, Integer> authorQb = sqliteApi.author().getDao().queryBuilder();
		authorQb.where().in(Article.Column.id, authorArticleQb);
		return authorQb.prepare();
	}

	/**
	 * Build our query for ArticleImpl objects that match a AuthorImpl.
	 */
	private PreparedQuery<CompanyImpl> makeCompanyForAuthorQuery() throws SQLException
	{
		QueryBuilder<AuthorToCompanyImpl, Integer> authorCompanyQb = sqliteApi.<Integer>getAuthorToCompanyDao().queryBuilder();
		authorCompanyQb.selectColumns(AuthorToCompany.Column.company_id);
		SelectArg authorSelectArg = new SelectArg();
		authorCompanyQb.where().eq(AuthorToCompany.Column.author_id, authorSelectArg);
		QueryBuilder<CompanyImpl, Integer> companyQb = sqliteApi.<Integer>getCompanyDao().queryBuilder();
		companyQb.where().in(Article.Column.id, authorCompanyQb);
		return companyQb.prepare();
	}

	/**
	 * Build our query for AuthorImpl objects that match a ArticleImpl
	 */
	private PreparedQuery<AuthorImpl> makeAuthorForCompanyQuery() throws SQLException
	{
		QueryBuilder<AuthorToCompanyImpl, Integer> authorCompanyQb = sqliteApi.<Integer>getAuthorToCompanyDao().queryBuilder();
		authorCompanyQb.selectColumns(AuthorToArticle.Column.author_id);
		SelectArg companySelectArg = new SelectArg();
		authorCompanyQb.where().eq(AuthorToCompany.Column.company_id, companySelectArg);
		QueryBuilder<AuthorImpl, Integer> authorQb = sqliteApi.author().getDao().queryBuilder();
		authorQb.where().in(Company.Column.id, authorCompanyQb);
		return authorQb.prepare();
	}

}
