package com.esc.datacollector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;

public abstract class AbsParserEngine<T>
{
	private final String filename;
	private final T resulT = null;

	public AbsParserEngine(String filename)
	{
		this.filename = filename;
	}

	public void parseFile()
	{
		preExecute();
		File file = new File(filename);
		try
		{
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
			execute(reader, resulT);

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
		postExecute(resulT);


	}

	protected void preExecute()
	{

	}

	protected void postExecute(T resul)
	{

	}

	abstract protected void execute(BufferedReader reader, T result);

}
