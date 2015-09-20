package com.esc.datacollector.disambiguation;

import com.esc.datacollector.app.Application;

import net.inference.database.IDatabaseApi;
import net.inference.sqlite.dto.Author;
import net.inference.sqlite.dto.PrimitiveAuthor;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Date: 20-Sep-15
 * Time: 2:43 PM
 *
 * @author xanderblinov
 */
public class SimpleDisambiguationResolver implements IDisambiguationResolver
{

	private static final int THREAD_COUNT = 8;

	private final ExecutorService mExecutorService = Executors.newFixedThreadPool(THREAD_COUNT);

	@Override
	public void start()
	{

	}

	private class ResolveNextPartOfAuthrosRunnable implements Runnable
	{
		@Override
		public void run()
		{
			while (true)
			{
				if (mExecutorService.isShutdown())
				{
					return;
				}

				final IDatabaseApi databaseApi = Application.getDatabaseApi();

				final List<PrimitiveAuthor> authors;
				try
				{
					authors = databaseApi.primitiveAuthor().getNextUnassignedAuthors();
				}
				catch (SQLException e)
				{
					continue;
				}

				if (authors == null)
				{
					mExecutorService.shutdown();
				}

				processAuthors(authors);

			}
		}
	}

	private void processAuthors(List<PrimitiveAuthor> primitiveAuthors)
	{
		for (PrimitiveAuthor primitiveAuthor : primitiveAuthors)
		{
			Author author = new Author();
		}
	}
}
