package com.esc.datacollector;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.esc.datacollector.data.PubmedCard;
import com.esc.datacollector.medline.MedlineSource;
import com.esc.datacollector.medline.Medliner;

public class PubMedParser extends AbsParserEngine<List<PubmedCard>>
{
	private static final String PUBMED_START_LINE_REGEX = "(....)- (.*)";

	public PubMedParser(String filename)
	{
		super(filename);
	}

	@Override
	protected void execute(BufferedReader reader, List<PubmedCard> mList)
	{
		List<PubmedCard> cards = new ArrayList<PubmedCard>();
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
						cards.add(Medliner.readMedline(source, PubmedCard.class));
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
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		for (PubmedCard pubmedCard : cards)
		{
			System.out.println(pubmedCard.getPmid() + " " + pubmedCard.getAU().length + " " +  pubmedCard.getFAU().length);
		}


	}
/*
	@SuppressWarnings("unused")
	private void addArticleToList(PubmedArticle pubMedArticle, List<PubmedCard> list)
	{
		if (pubMedArticle != null && needToAddArticle(pubMedArticle))
		{
			if (false)
			{
				list.add(pubMedArticle);
			}
			else if (mStorageApi != null && mConnection != null)
			{
				try
				{
					mStorageApi.addPubMedArticleToTempTable(mConnection, pubMedArticle);
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
		}

	}*/

	/**
	 * override this method to customize adding articles to list
	 *
	 * @param pubMedArticle
	 * @return true
	 */
	protected boolean needToAddArticle(PubmedCard pubMedArticle)
	{
		return true;
	}
}
