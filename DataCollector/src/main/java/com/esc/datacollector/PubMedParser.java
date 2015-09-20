package com.esc.datacollector;

import com.esc.datacollector.data.PubmedCard;
import com.esc.datacollector.medline.MedlineSource;
import com.esc.datacollector.medline.Medliner;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class PubMedParser extends AbsParserEngine
{
	private static final long PARSING_DELAY = TimeUnit.SECONDS.toMillis(2);
	public static final int CARDS_BUFFER_SIZE = 200;
	public static final int DATABASE_THREAD_COUNT = 4;
	private static final String PUBMED_START_LINE_REGEX = "(....)- (.*)";

	private volatile boolean mFileReadingCompleted = false;

	private LinkedBlockingQueue<PubmedCard> mPubmedCards = new LinkedBlockingQueue<>();

	private IPubmedCardProcessor mPubmedCardProcessor = new PubmedCardProcessor();

	@Override
	protected ExecutorService newExecutorService()
	{
		return Executors.newFixedThreadPool(2);
	}

	public PubMedParser(String filename)
	{
		super(filename);
	}

	@Override
	protected void execute(BufferedReader reader)
	{
		for (int i = 0; i < DATABASE_THREAD_COUNT; i++)
		{
			getExecutorService().execute(new AddPubmedCardRunnable());
		}

		String currentString;
		StringBuilder builder = new StringBuilder();
		MedlineSource source = null;
		String currentField = null;
		try
		{
			while ((currentString = reader.readLine()) != null)
			{
				if (currentString.isEmpty())
				{
					if (source != null)
					{
						mPubmedCards.offer(Medliner.readMedline(source, PubmedCard.class));
						if (mPubmedCards.size() > CARDS_BUFFER_SIZE)
						{
							Thread.sleep(PARSING_DELAY);
						}
					}
					source = new MedlineSource();
					currentField = null;
				}
				else
				{
					if (currentString.matches(PUBMED_START_LINE_REGEX))
					{
						if (source != null && currentField != null)
						{
							source.addField(currentField, builder.toString());
						}
						builder = new StringBuilder(currentString.replaceAll(PUBMED_START_LINE_REGEX, "$2"));
						currentField = currentString.replaceAll(PUBMED_START_LINE_REGEX, "$1").trim();
					}
					else
					{
						builder.append(" ").append(currentString.trim());
					}
				}
			}
			mFileReadingCompleted = true;
		}
		catch (IOException | InterruptedException e)
		{
			e.printStackTrace();
		}


	}

	private class AddPubmedCardRunnable implements Runnable
	{

		@Override
		public void run()
		{
			while (true)
			{
				if (mFileReadingCompleted && mPubmedCards.size() == 0)
				{
					getExecutorService().shutdown();
					return;
				}

				final PubmedCard pubmedCard;
				try
				{
					pubmedCard = mPubmedCards.take();
				}
				catch (InterruptedException ignore)
				{
					continue;
				}
				mPubmedCardProcessor.execute(pubmedCard);

			}
		}
	}

	@Override
	protected void onPostExecute()
	{
		super.onPostExecute();
	}
}
