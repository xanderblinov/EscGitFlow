package net.inference.sqlite;

import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;

import net.inference.database.ITermApi;
import net.inference.database.dto.ITerm;
import net.inference.sqlite.dto.Term;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Мария on 15.10.15.
 */
public class TermApi extends BaseApi<Term, Integer> implements ITermApi
{
	private final DatabaseApi mDatabaseApi;
	private static Logger logger = LoggerFactory.getLogger(Term.class);

	public TermApi(DatabaseApi databaseApi)
	{
		super(databaseApi, Term.class);
		mDatabaseApi = databaseApi;
	}

	@Override
	public ITerm addTerm(final ITerm term)
	{try
	{
		return getDao().createIfNotExists((Term) term);
	}
	catch (SQLException e)
	{
		logger.error(e, "");
	}
		return null;
	};

	@Override
	public boolean exists(Term term) throws SQLException
	{
		return getDao().queryForFirst(getDao().queryBuilder().where().eq(ITerm.Column.value, term.getValue()).and().eq(ITerm.Column.publication, term.getPublication()).prepare()) !=
		null;
	};

	@Override
	public ArrayList<Term> addTerms(List<Term> newTerms) throws Exception
	{
		final Dao<Term, Integer> dao = getDao();

		//noinspection UnnecessaryLocalVariable
		final ArrayList<Term> terms = dao.callBatchTasks(() -> {

			ArrayList<Term> createdTerms = new ArrayList<>();


			for (Term term : newTerms)
			{
				final Term newTerm = dao.createIfNotExists(term);
				createdTerms.add(newTerm);
			}
			return createdTerms;
		});
		return terms;
	}



}
