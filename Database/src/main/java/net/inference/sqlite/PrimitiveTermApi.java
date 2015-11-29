package net.inference.sqlite;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;

import net.inference.database.IPrimitiveTermApi;
import net.inference.database.dto.IPrimitiveTerm;
import net.inference.sqlite.dto.PrimitiveAuthor;
import net.inference.sqlite.dto.PrimitiveTerm;

/**
 * Created by palen on 23.10.2015.
 */
public class PrimitiveTermApi extends BaseApi<PrimitiveTerm, Integer> implements IPrimitiveTermApi
{
	private static Logger logger = LoggerFactory.getLogger(PrimitiveTerm.class);
	private final DatabaseApi mDatabaseApi;

	public PrimitiveTermApi(DatabaseApi databaseApi)
	{
		super(databaseApi, PrimitiveTerm.class);
		mDatabaseApi = databaseApi;
	}

	@Override
	public List<PrimitiveTerm> addTerms(List<PrimitiveTerm> primitiveTerms) throws Exception
	{
		final Dao<PrimitiveTerm, Integer> dao = getDao();

		//noinspection UnnecessaryLocalVariable
		final ArrayList<PrimitiveTerm> terms = dao.callBatchTasks(() -> {

			ArrayList<PrimitiveTerm> createdTerms = new ArrayList<>();


			for (PrimitiveTerm term : primitiveTerms)
			{
				final PrimitiveTerm primitiveTerm = dao.createIfNotExists(term);
				createdTerms.add(primitiveTerm);
			}
			return createdTerms;
		});
		return terms;
	}

	@Override

	public PrimitiveTerm addTerm(final PrimitiveTerm primitiveTerm)
	{try
	{
		return getDao().createIfNotExists((PrimitiveTerm) primitiveTerm);
	}
	catch (SQLException e)
	{
		logger.error(e, "");
	}
		return null;
	};
}
