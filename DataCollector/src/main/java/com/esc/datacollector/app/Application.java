package com.esc.datacollector.app;

import net.inference.Config;
import net.inference.database.DatabaseApi;
import net.inference.sqlite.SqliteApi;

/**
 * Date: 19-Sep-15
 * Time: 9:55 PM
 *
 * @author xanderblinov
 */
public class Application
{
	private static final Object sLock = new Object();

	private static Application sInstance;

	private static Application getInstance()
	{
		if (sInstance == null)
		{
			synchronized (sLock)
			{
				if (sInstance == null)
				{
					sInstance = new Application();
				}
			}
		}

		return sInstance;
	}

	private Application()
	{
		mDatabaseApi = new SqliteApi(Config.Database.TEST, false);
		mDatabaseApi.onStart();
	}

	private final DatabaseApi mDatabaseApi;

	private DatabaseApi getDatabaseApiNonStatic()
	{
		return mDatabaseApi;
	}

	public static DatabaseApi getDatabaseApi()
	{
		return getInstance().getDatabaseApiNonStatic();
	}

}
