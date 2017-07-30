package com.esc.louvainalgorithm;


import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
/**
 * 
 * @deprecated should use {@link net.inference.sqlite.DatabaseApi}.
 *
 * no manual connection by jdbc
 *
 */
@Deprecated
public class DBConnection
{

	private final String url = "jdbc:sqlite:Database\\src\\main\\resources\\test.db";
	private ConnectionSource source;
	public CoathorInformationParser coauthorDao;
	public Dao<AuthorToCluster, Integer> clusterDao;
	public Dao<Evolution, Integer> evolutionDao;
	public Dao<EvolutionSlice, Integer> evolutionSliceDao;


	public DBConnection() throws SQLException
	{

		source = new JdbcConnectionSource(url);
		coauthorDao = new CoathorInformationParser(source);
		clusterDao = DaoManager.createDao(source, AuthorToCluster.class);
		evolutionDao = DaoManager.createDao(source, Evolution.class);
		evolutionSliceDao = DaoManager.createDao(source, EvolutionSlice.class);

	}







}
