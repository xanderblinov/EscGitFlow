package com.esc.datacollector;

import net.inference.Config;
import net.inference.DatabaseApiFactory;
import net.inference.database.DatabaseApi;
import net.inference.database.dto.Article;

/**
 * Date: 4/16/2015
 * Time: 9:05 PM
 *
 * @author xanderblinov
 */
public class DataCollector
{
	public static void main(String[] args)
	{
		DatabaseApi api = DatabaseApiFactory.getDatabaseApi(DatabaseApiFactory.DatabaseType.Sqlite, Config.Database.TEST, false);

		api.onStart();
		for (Article article : api.article().getAllArticles())
		{
			System.out.println(article.toString());
		}

		api.onStop();
	}
}
