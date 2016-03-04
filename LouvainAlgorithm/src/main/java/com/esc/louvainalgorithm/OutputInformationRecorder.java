package com.esc.louvainalgorithm;


import java.sql.SQLException;

public class OutputInformationRecorder
{

	DBConnection conn;

	public OutputInformationRecorder(DBConnection conn)
	{

		this.conn = conn;

	}

	public void recordAuthorToClusterData(int author, int cluster) throws SQLException
	{

		AuthorToCluster atc = new AuthorToCluster();
		atc.setAuthor(author);
		atc.setCluster(cluster);
		conn.clusterDao.create(atc);

	}

	public void recordEvolutionData(int firstYear, int lastYear) throws SQLException
	{

		Evolution evol = new Evolution();
		evol.setFirstYear(String.valueOf(firstYear));
		evol.setLastYear(String.valueOf(lastYear));
		evol.setTypeId("LouvainAlgorithm");
		conn.evolutionDao.create(evol);

	}

	public void recordEvolutionSliceData(int year, int evolid) throws SQLException
	{

		EvolutionSlice evolsl = new EvolutionSlice();
		evolsl.setYear(String.valueOf(year));
		evolsl.setEvolutionId(evolid);
		conn.evolutionSliceDao.create(evolsl);

	}


}
