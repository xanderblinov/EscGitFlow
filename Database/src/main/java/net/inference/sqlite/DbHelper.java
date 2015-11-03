package net.inference.sqlite;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import net.inference.Config;
import net.inference.sqlite.dto.Article;
import net.inference.sqlite.dto.ArticleToTerm;
import net.inference.sqlite.dto.Author;
import net.inference.sqlite.dto.AuthorToArticle;
import net.inference.sqlite.dto.AuthorToAuthor;
import net.inference.sqlite.dto.AuthorToCluster;
import net.inference.sqlite.dto.AuthorToCompany;
import net.inference.sqlite.dto.Cluster;
import net.inference.sqlite.dto.Company;
import net.inference.sqlite.dto.Evolution;
import net.inference.sqlite.dto.EvolutionSlice;
import net.inference.sqlite.dto.Parameter;
import net.inference.sqlite.dto.PrimitiveAuthor;
import net.inference.sqlite.dto.PrimitiveAuthorToAuthor;
import net.inference.sqlite.dto.PrimitiveTerm;
import net.inference.sqlite.dto.PrimitiveTermToPrimitiveTerm;
import net.inference.sqlite.dto.Term;


import java.io.File;
import java.sql.SQLException;

/**
 * Date: 12/21/2014
 * Time: 5:34 PM
 *
 * @author xanderblinov
 */


class DbHelper
{


	@SuppressWarnings("FieldCanBeLocal")
	private static final String sBaseUrl = "jdbc:sqlite:" + new File("Database/src/main/resources/").getAbsolutePath() + File.separator;
	private boolean mRecreateDatabase;

	private static Logger logger = LoggerFactory.getLogger(DbHelper.class);

	private ConnectionSource mConnectionSource;
	/*@formatter:off*/
    private static Class[] tablesClassList = new Class[]{
            Company.class,
            Cluster.class,
            Evolution.class,
            EvolutionSlice.class,
            Article.class,
            Author.class,
            AuthorToCluster.class,
            AuthorToAuthor.class,
            Parameter.class,
            PrimitiveAuthor.class,
            PrimitiveAuthorToAuthor.class,
            AuthorToArticle.class,
            AuthorToCompany.class,
		    Company.class,
			PrimitiveTerm.class,
			PrimitiveTermToPrimitiveTerm.class,
			ArticleToTerm.class,
			Term.class

    };
	/*@formatter:on*/
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

	ConnectionSource getConnection() throws SQLException
	{
		if (mConnectionSource == null)
		{
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
			if (mRecreateDatabase)
			{
				logger.info("recreating database");
				recreateDatabase(connectionSource);
			}
			else
			{
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

	private void recreateDatabase(ConnectionSource connectionSource) throws SQLException
	{
		clearDatabase(connectionSource);
		createDatabase(connectionSource);
	}

	private void clearDatabase(ConnectionSource connectionSource) throws SQLException
	{

		for (Class clazz : tablesClassList)
		{
			TableUtils.dropTable(connectionSource, clazz, false);
		}
	}

	private void createDatabase(ConnectionSource connectionSource) throws SQLException
	{

		for (Class clazz : tablesClassList)
		{
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
