package net.inference.sqlite;

import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import net.inference.Config;
import net.inference.database.ArticleApi;
import net.inference.database.AuthorApi;
import net.inference.database.ClusterApi;
import net.inference.database.DatabaseApi;
import net.inference.database.EvolutionApi;
import net.inference.sqlite.dto.ArticleImpl;
import net.inference.sqlite.dto.AuthorImpl;
import net.inference.sqlite.dto.AuthorToClusterImpl;
import net.inference.sqlite.dto.ClusterImpl;
import net.inference.sqlite.dto.CoAuthorshipImpl;
import net.inference.sqlite.dto.EvolutionImpl;
import net.inference.sqlite.dto.EvolutionSliceImpl;

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



	public ConnectionSource getConnection() {
		try {
			return mDbHelper.getConnection();
		} catch (SQLException e) {
			SqliteLog.log(e);
		}

		return null;
	}
}
