package net.inference.sqlite;

import java.io.File;
import java.sql.SQLException;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import net.inference.Config;
import net.inference.sqlite.dto.ArticleImpl;
import net.inference.sqlite.dto.ClusterImpl;
import net.inference.sqlite.dto.EvolutionImpl;
import net.inference.sqlite.dto.EvolutionSliceImpl;
import net.inference.sqlite.dto.AuthorImpl;
import net.inference.sqlite.dto.AuthorToClusterImpl;
import net.inference.sqlite.dto.CoAuthorshipImpl;
import net.inference.sqlite.dto.ParameterImpl;
import net.inference.sqlite.dto.PrimitiveAuthorImpl;
import net.inference.sqlite.dto.PrimitiveCoAuthorshipImpl;

/**
 * Date: 12/21/2014
 * Time: 5:34 PM
 *
 * @author xanderblinov
 */
class DbHelper
{
	@SuppressWarnings("FieldCanBeLocal")
	private static final String sBaseUrl = "jdbc:sqlite:" + System.getProperty("user.home") + File.separator;
    private boolean mRecreateDatabase;

    private static Logger logger = LoggerFactory.getLogger(DbHelper.class);

	private ConnectionSource mConnectionSource;
    private static Class[] tablesClassList = new Class[]{
            ArticleImpl.class,
            ClusterImpl.class,
            EvolutionImpl.class,
            EvolutionSliceImpl.class,
            AuthorImpl.class,
            AuthorToClusterImpl.class,
            CoAuthorshipImpl.class,
            ParameterImpl.class,
            PrimitiveAuthorImpl.class,
            PrimitiveCoAuthorshipImpl.class
    };

    private final Config.Database mDatabase;

	public DbHelper(final Config.Database database, boolean recreateDatabase)
	{
		mDatabase = database;
        mRecreateDatabase = recreateDatabase;
    }

	private String getUrl()
	{
		return sBaseUrl + mDatabase.getName();
	}

	ConnectionSource getConnection() throws SQLException {
        if (mConnectionSource == null) {
            mConnectionSource = new JdbcPooledConnectionSource(getUrl());
        }

        return mConnectionSource;
    }

	/**
	 * int tables
	 */
	public void onStart()
	{

		try
		{
			ConnectionSource connectionSource = getConnection();
            if (mRecreateDatabase) {
                logger.info("recreating database");
                recreateDatabase(connectionSource);
            } else {
                logger.info("create database");
                createDatabase(connectionSource);
            }

			//TODO add other tables
		}
		catch (SQLException e)
		{
			SqliteLog.log(e);
            System.exit(1);
		}
	}

    private void recreateDatabase(ConnectionSource connectionSource) throws SQLException {
        clearDatabase(connectionSource);
        createDatabase(connectionSource);
    }

    private void clearDatabase(ConnectionSource connectionSource) throws SQLException{

        for (Class clazz : tablesClassList) {
            TableUtils.dropTable(connectionSource, clazz, false);
        }
    }

    private void createDatabase(ConnectionSource connectionSource) throws SQLException {

        for (Class clazz : tablesClassList) {
            TableUtils.createTableIfNotExists(connectionSource, clazz);
        }

    }

	public void onStop()
	{
		try
		{
			mConnectionSource.close();
		}
		catch (SQLException e)
		{
			SqliteLog.log(e);
		}
	}
}
