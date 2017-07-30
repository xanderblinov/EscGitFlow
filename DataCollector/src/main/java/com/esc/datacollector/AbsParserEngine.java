package com.esc.datacollector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;

public abstract class AbsParserEngine
{
	private final String filename;
	private ExecutorService mExecutorService = newExecutorService();

	protected abstract ExecutorService newExecutorService();

	protected ExecutorService getExecutorService()
	{
		return mExecutorService;
	}

	public AbsParserEngine(String filename)
	{
		this.filename = filename;
		//TODO:fix if filename wrong
	}

	public void parseFile()
	{
		onPreExecute();

		FutureTask<Void> futureTask = new FutureTask<>(new ParseFileCallable());
		mExecutorService.execute(futureTask);
		try
		{
			futureTask.get();
		}
		catch (InterruptedException | ExecutionException e)
		{
			//TODO onError
		}
		onPostExecute();


	}

	private void parseFileInternal()
	{

		File file = new File(filename);
		try
		{
			//noinspection ConstantConditions
			file = new File(ClassLoader.getSystemClassLoader().getResource(filename).toURI());
		}
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}
		BufferedReader reader = null;

		try
		{
			reader = new BufferedReader(new FileReader(file));
			execute(reader);

		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}

		finally
		{
			try
			{
				if (reader != null)
				{
					reader.close();
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

		}
	}

	private class ParseFileCallable implements Callable<Void>
	{
		@Override
		public Void call() throws Exception
		{
			parseFileInternal();
			return null;
		}
	}

	protected void onPreExecute()
	{

	}

	protected void onPostExecute()
	{

	}

	abstract protected void execute(BufferedReader reader);

}
