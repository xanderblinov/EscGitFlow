package net.inference.sqlite;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import net.inference.database.ITermApi;

import net.inference.sqlite.dto.Term;

/**
 * Created by Мария on 03.11.15.
 */
public class TermApi extends BaseApi<Term, Integer> implements ITermApi
{
	private static Logger logger = LoggerFactory.getLogger(Term.class);
	private final DatabaseApi mDatabaseApi;

	public TermApi(DatabaseApi databaseApi)
	{
		super(databaseApi, Term.class);
		this.mDatabaseApi = databaseApi;
	}
}
