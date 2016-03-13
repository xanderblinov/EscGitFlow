package net.inference.sqlite;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;

import net.inference.database.ITermToTermApi;
import net.inference.database.dto.ITermToTerm;
import net.inference.sqlite.dto.TermToTerm;

/**
 * Created by M.Pankova on 15.12.15.
 */
public class TermToTermApi extends BaseApi<TermToTerm, Integer> implements ITermToTermApi
{
	private static Logger logger = LoggerFactory.getLogger(TermToTerm.class);
	private final DatabaseApi mDatabaseApi;

	public TermToTermApi(DatabaseApi databaseApi)
	{
		super(databaseApi, TermToTerm.class);
		mDatabaseApi = databaseApi;
	}

	@Override
	public List<TermToTerm> addTerms(List<TermToTerm> terms) throws Exception{
		final Dao<TermToTerm, Integer> dao = getDao();

		//noinspection UnnecessaryLocalVariable
		final ArrayList<TermToTerm> Terms = dao.callBatchTasks(() -> {

			ArrayList<TermToTerm> createdTerms = new ArrayList<>();


			for (TermToTerm term : terms)
			{
				final TermToTerm termToTerm = dao.createIfNotExists(term);
				createdTerms.add(termToTerm);
			}
			return createdTerms;
		});
		return terms;
	}

	@Override
	public TermToTerm addTerm(TermToTerm term)
	{try
	{
		return getDao().createIfNotExists((TermToTerm) term);
	}
	catch (SQLException e)
	{
		logger.error(e, "");
	}
		return null;
	};

	@Override
	public boolean exists(TermToTerm elem) throws SQLException
	{
		return
		getDao().queryForFirst(getDao().queryBuilder().where().eq(ITermToTerm.Column.from, elem.getFrom()).and().eq(ITermToTerm.Column.to, elem.getTo()).prepare()) !=
		null;
	}
}
