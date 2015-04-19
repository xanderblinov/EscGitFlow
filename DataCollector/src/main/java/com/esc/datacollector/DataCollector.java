package com.esc.datacollector;

import com.esc.datacollector.data.PubmedCard;
import com.esc.datacollector.medline.MedlineSource;
import com.esc.datacollector.medline.Medliner;

/**
 * Date: 4/16/2015
 * Time: 9:05 PM
 *
 * @author xanderblinov
 */
public class DataCollector
{
	private static final String PUBMED_START_LINE_REGEX = "(....)- (.*)";

	public static void main(String[] args)
	{
/*
		DatabaseApi api = DatabaseApiFactory.getDatabaseApi(DatabaseApiFactory.DatabaseType.Sqlite, Config.Database.TEST, false);

		api.onStart();
		for (Article article : api.article().getAllArticles())
		{
			System.out.println(article.toString());
		}

		api.onStop();
*/
		MedlineSource medlineSource = new MedlineSource();

		medlineSource.addField("PMID- ","24d1221");
		medlineSource.addField("b","bb");
		medlineSource.addField("b","ba");
		medlineSource.addField("c","cc");
		medlineSource.addField("c","dd");
		PubmedCard pubmedCard = Medliner.readMedline(medlineSource, PubmedCard.class);

		String string = " PMID- 23656783";
		String pattern  = "....- (.*)";
		if(string.matches(pattern)){
			System.out.println(pubmedCard.getPmid());
		}

		String resultString = "PMID- qqwqwdqwd".replaceAll(PUBMED_START_LINE_REGEX, "$1");
		System.out.println(resultString);
		resultString = "PMID- qqwqwdqwd".replaceAll(PUBMED_START_LINE_REGEX, "$2").trim();
		System.out.println(resultString);


		new PubMedParser("./test_pubmed.htm").parseFile();

	}
}
