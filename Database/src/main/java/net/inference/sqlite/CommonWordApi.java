package net.inference.sqlite;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;

import net.inference.database.ICommonWordApi;

import net.inference.sqlite.dto.CommonWord;


public class CommonWordApi extends BaseApi<CommonWord, Integer> implements ICommonWordApi
{
	private static Logger logger = LoggerFactory.getLogger(CommonWord.class);
	private final DatabaseApi mDatabaseApi;

	public CommonWordApi(DatabaseApi databaseApi)
	{
		super(databaseApi, CommonWord.class);
		this.mDatabaseApi = databaseApi;
	}

	@Override
	public List<CommonWord> addCommonWords (List<CommonWord> commonWords) throws Exception
	{
		final Dao<CommonWord, Integer> dao = getDao();

		//noinspection UnnecessaryLocalVariable
		final ArrayList<CommonWord> arrCommonWords = dao.callBatchTasks(() -> {

			ArrayList<CommonWord> createdCommonWords = new ArrayList<>();


			for (CommonWord term : commonWords)
			{
				final CommonWord termForCreation = dao.createIfNotExists(term);
				createdCommonWords.add(term);
			}
			return createdCommonWords;
		});
		return commonWords;
	}
}