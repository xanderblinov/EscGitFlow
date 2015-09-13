package net.inference.sqlite;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

import net.inference.Config;
import net.inference.database.ArticleApi;
import net.inference.database.AuthorApi;
import net.inference.database.AuthorArticleApi;
import net.inference.database.AuthorCompanyApi;
import net.inference.database.ClusterApi;
import net.inference.database.CompanyApi;
import net.inference.database.DatabaseApi;
import net.inference.database.EvolutionApi;
import net.inference.database.PrimitiveAuthorApi;
import net.inference.sqlite.dto.ArticleImpl;
import net.inference.sqlite.dto.AuthorImpl;
import net.inference.sqlite.dto.AuthorToArticleImpl;
import net.inference.sqlite.dto.AuthorToClusterImpl;
import net.inference.sqlite.dto.AuthorToCompanyImpl;
import net.inference.sqlite.dto.ClusterImpl;
import net.inference.sqlite.dto.CoAuthorshipImpl;
import net.inference.sqlite.dto.CompanyImpl;
import net.inference.sqlite.dto.EvolutionImpl;
import net.inference.sqlite.dto.EvolutionSliceImpl;
import net.inference.sqlite.dto.PrimitiveAuthorImpl;
import net.inference.sqlite.dto.PrimitiveCoAuthorshipImpl;

import java.sql.SQLException;

/**
 * Date: 12/21/2014
 * Time: 9:33 PM
 *
 * @author xanderblinov
 */
public class SqliteApi implements DatabaseApi
{
	private final DbHelper mDbHelper;
	private ArticleApi mArticleApi = new ArticleApiImpl(this);
	private AuthorApi  authorApi = new AuthorApiImpl(this);
	private PrimitiveAuthorApi mPrimitiveAuthorApi = new PrimitiveAuthorApiImpl(this);
	private ClusterApi clusterApi = new ClusterApiImpl(this);
	private EvolutionApi evolutionApi = new EvolutionApiImpl(this);
	private AuthorCompanyApi authorCompanyApi = new AuthorToCompanyApiImpl(this);
	private AuthorArticleApi authorArticleApi = new AuthorToArticleApiImpl(this);
	private CompanyApi companyApi = new CompanyApiImpl(this);

	public SqliteApi(Config.Database database, boolean recreateDatabase)
	{
		mDbHelper = new DbHelper(database, recreateDatabase);

	}

	@Override
	public void onStart()
	{
		mDbHelper.onStart();
	}

	@Override
	public void onStop()
	{
		mDbHelper.onStop();
	}


	@Override
	public ArticleApi article()
	{
		return mArticleApi;
	}

    @Override
    public AuthorApi author() {
        return authorApi;
    }

	@Override
	public ClusterApi cluster() {
		return clusterApi;
	}

	@Override
	public EvolutionApi evolution() {
		return evolutionApi;
	}

	@Override
	public AuthorArticleApi authorArticle()
	{
		return authorArticleApi;
	}

	@Override
	public AuthorCompanyApi authorCompany()
	{
		return authorCompanyApi;
	}

	@Override
	public CompanyApi company()
	{
		return companyApi;
	}

	@Override
	public PrimitiveAuthorApi primitiveAuthor()
	{
		return mPrimitiveAuthorApi;
	}


	public <T> Dao<ArticleImpl, T> getArticleDao() throws SQLException
	{
		return DaoManager.createDao(mDbHelper.getConnection(), ArticleImpl.class);
	}

	<T> Dao<AuthorImpl, T> getInferenceAuthorDao() throws SQLException
	{
		return DaoManager.createDao(mDbHelper.getConnection(), AuthorImpl.class);
	}

	<T> Dao<CoAuthorshipImpl, T> getInferenceCoAuthorshipDao() throws SQLException
	{
		return DaoManager.createDao(mDbHelper.getConnection(), CoAuthorshipImpl.class);
	}

    <T> Dao<AuthorToClusterImpl, T> getAuthorToClusterDao() throws SQLException
	{
		return DaoManager.createDao(mDbHelper.getConnection(), AuthorToClusterImpl.class);
	}

    <T> Dao<ClusterImpl, T> getClusterDao() throws SQLException
	{
		return DaoManager.createDao(mDbHelper.getConnection(), ClusterImpl.class);
	}

	<T> Dao<EvolutionImpl, T> getEvolutionDao() throws SQLException
	{
		return DaoManager.createDao(mDbHelper.getConnection(), EvolutionImpl.class);
	}

    <T> Dao<EvolutionSliceImpl, T> getEvolutionSliceDao() throws SQLException
	{
		return DaoManager.createDao(mDbHelper.getConnection(), EvolutionSliceImpl.class);
	}

	<T> Dao<AuthorToArticleImpl, T> getAuthorToArticleDao() throws SQLException
	{
		return DaoManager.createDao(mDbHelper.getConnection(), AuthorToArticleImpl.class);
	}

	<T> Dao<AuthorToCompanyImpl, T> getAuthorToCompanyDao() throws SQLException
	{
		return DaoManager.createDao(mDbHelper.getConnection(), AuthorToCompanyImpl.class);
	}

	<T> Dao<CompanyImpl, T> getCompanyDao() throws SQLException
	{
		return DaoManager.createDao(mDbHelper.getConnection(), CompanyImpl.class);
	}

	<T> Dao<PrimitiveCoAuthorshipImpl, T> getPrimitiveCoAuthorshipDao() throws SQLException
	{
		return DaoManager.createDao(mDbHelper.getConnection(), PrimitiveCoAuthorshipImpl.class);
	}
	<T> Dao<PrimitiveAuthorImpl, T> getPrimitiveAuthorDao() throws SQLException
	{
		return DaoManager.createDao(mDbHelper.getConnection(), PrimitiveAuthorImpl.class);
	}
	public ConnectionSource getConnection() {
		try {
			return mDbHelper.getConnection();
		} catch (SQLException e) {
			SqliteLog.log(e);
		}

		return null;
	}

}
