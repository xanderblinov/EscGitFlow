package net.inference.sqlite;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;

import net.inference.database.ITermYearApi;
import net.inference.database.dto.ITermYear;
import net.inference.sqlite.dto.TermYear;

/**
 * Created by Мария on 16.04.2016.
 */
public class TermYearApi extends BaseApi<TermYear, Integer> implements ITermYearApi
{
	private static Logger logger = LoggerFactory.getLogger(TermYear.class);
	private final DatabaseApi mDatabaseApi;

	public TermYearApi(DatabaseApi databaseApi)
	{
		super(databaseApi, TermYear.class);
		this.mDatabaseApi = databaseApi;
	}

	@Override
	public List<TermYear> addTerms (List<TermYear> terms) throws Exception
	{
		final Dao<TermYear, Integer> dao = getDao();

		//noinspection UnnecessaryLocalVariable
		final ArrayList<TermYear> arrTerms = dao.callBatchTasks(() -> {

			ArrayList<TermYear> createdTerms = new ArrayList<>();


			for (TermYear term : terms)
			{
				final TermYear termForCreation = dao.createIfNotExists(term);
				createdTerms.add(term);
			}
			return createdTerms;
		});
		return terms;
	}

	@Override
	public boolean exists(TermYear term) throws SQLException
	{
		return
				getDao().queryForFirst(getDao().queryBuilder().where().eq(ITermYear.Column.term, term.getTerm()).
						and().eq(ITermYear.Column.year, term.getYear()).prepare()) !=
						null;
	}
}
