package com.esc.datacollector.disambiguation;

import com.esc.datacollector.app.Application;

import net.inference.database.IDatabaseApi;
import net.inference.sqlite.dto.Author;
import net.inference.sqlite.dto.AuthorToAuthor;
import net.inference.sqlite.dto.PrimitiveAuthor;

import java.sql.SQLException;
import java.util.ArrayList;
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

	private static final int THREAD_COUNT = 4;

	private final ExecutorService mExecutorService = Executors.newFixedThreadPool(THREAD_COUNT);

	@Override
	public void start()
	{
		for (int i = 0; i < THREAD_COUNT; i++)
		{
			mExecutorService.execute(new ResolveNextPartOfAuthrosRunnable());
		}
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
					return;
				}

				try
				{
					processAuthors(authors);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}

			}
		}
	}

	private void processAuthors(List<PrimitiveAuthor> primitiveAuthors) throws Exception
	{
		List<Author> authors = new ArrayList<>();

		for (PrimitiveAuthor primitiveAuthor : primitiveAuthors)
		{
			Author author = new Author();
			author.setEncoding(primitiveAuthor.getEncoding());

			authors.add(author);
		}
		final IDatabaseApi databaseApi = Application.getDatabaseApi();

		authors = databaseApi.author().addAuthors(authors);


		List<AuthorToAuthor> authorToAuthors = new ArrayList<>();

		for (Author outer : authors)
		{
			for (Author inner : authors)
			{
				AuthorToAuthor authorToAuthor = new AuthorToAuthor(outer, inner);
				authorToAuthors.add(authorToAuthor);
			}
		}
		databaseApi.author().addAuthorToAuthors(authorToAuthors);
	}
}
