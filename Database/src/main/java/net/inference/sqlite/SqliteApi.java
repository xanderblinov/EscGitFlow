package net.inference.sqlite;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import net.inference.Config;
import net.inference.database.*;
import net.inference.database.dto.Article;
import net.inference.database.dto.Cluster;
import net.inference.database.dto.Evolution;
import net.inference.database.dto.EvolutionSlice;
import net.inference.sqlite.dto.*;

import java.sql.SQLException;
import java.util.List;

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
	private ClusterApi clusterApi = new ClusterApiImpl(this);
	private EvolutionApi evolutionApi = new EvolutionApiImpl(this);

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


	@Override
	public Article addArticle(final Article article)
	{
		try
		{
			return getArticleDao().createIfNotExists((ArticleImpl) article);
		}
		catch (SQLException e)
		{
			SqliteLog.log(e);
		}
		return null;
	}



	@Override
	public Cluster addCluster(final Cluster cluster)
	{
		try
		{
			return getClusterDao().createIfNotExists((ClusterImpl) cluster);
		}
		catch (SQLException e)
		{
			SqliteLog.log(e);
		}
		return null;
	}

	@Override
	public Evolution addEvolution(final Evolution evolution)
	{
		try
		{
			return getEvolutionDao().createIfNotExists((EvolutionImpl) evolution);
		}
		catch (SQLException e)
		{
			SqliteLog.log(e);
		}
		return null;
	}

	@Override
	public EvolutionSlice addEvolutionSlice(final EvolutionSlice evolutionSlice)
	{
		try
		{
			return getEvolutionSliceDao().createIfNotExists((EvolutionSliceImpl) evolutionSlice);
		}
		catch (SQLException e)
		{
			SqliteLog.log(e);
		}
		return null;
	}


	@Override
	public List<ArticleImpl> getAllArticles()
	{

		try
		{
			return getArticleDao().queryForAll();
		}
		catch (SQLException e)
		{
			SqliteLog.log(e);
		}

		return null;
	}

	@Override
	public ConnectionSource getConnection() {
		try {
			return mDbHelper.getConnection();
		} catch (SQLException e) {
			SqliteLog.log(e);
		}

		return null;
	}
}
