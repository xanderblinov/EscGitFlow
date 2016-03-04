package net.inference.sqlite;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;

import net.inference.database.IPrimitiveTermToPrimitiveTermApi;
import net.inference.sqlite.dto.PrimitiveTermToPrimitiveTerm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by M.Pankova on 24.10.15.
 */
public class PrimitiveTermToPrimitiveTermApi extends BaseApi<PrimitiveTermToPrimitiveTerm, Integer> implements IPrimitiveTermToPrimitiveTermApi
{
	private static Logger logger = LoggerFactory.getLogger(PrimitiveTermToPrimitiveTerm.class);
	private final DatabaseApi mDatabaseApi;

	public PrimitiveTermToPrimitiveTermApi(DatabaseApi databaseApi)
	{
		super(databaseApi, PrimitiveTermToPrimitiveTerm.class);
		mDatabaseApi = databaseApi;
	}

	@Override
	public List<PrimitiveTermToPrimitiveTerm> addTerms(List<PrimitiveTermToPrimitiveTerm> terms) throws Exception{
		final Dao<PrimitiveTermToPrimitiveTerm, Integer> dao = getDao();

		//noinspection UnnecessaryLocalVariable
		final ArrayList<PrimitiveTermToPrimitiveTerm> primTerms = dao.callBatchTasks(() -> {

			ArrayList<PrimitiveTermToPrimitiveTerm> createdTerms = new ArrayList<>();


			for (PrimitiveTermToPrimitiveTerm term : terms)
			{
				final PrimitiveTermToPrimitiveTerm primitiveTermToPrimitiveTerm = dao.createIfNotExists(term);
				createdTerms.add(primitiveTermToPrimitiveTerm);
			}
			return createdTerms;
		});
		return terms;
	}

	@Override
	public PrimitiveTermToPrimitiveTerm addTerm(PrimitiveTermToPrimitiveTerm primitiveTerm)
		{try
		{
			return getDao().createIfNotExists((PrimitiveTermToPrimitiveTerm) primitiveTerm);
		}
		catch (SQLException e)
		{
			logger.error(e, "");
		}
			return null;
		};
}
