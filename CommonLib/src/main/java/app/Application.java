package app;

import net.inference.Config;
import net.inference.database.IDatabaseApi;
import net.inference.sqlite.DatabaseApi;

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
		mDatabaseApi = new DatabaseApi(Config.Database.TEST, false);
		mDatabaseApi.onStart();
	}

	private final IDatabaseApi mDatabaseApi;

	private IDatabaseApi getDatabaseApiNonStatic()
	{
		return mDatabaseApi;
	}

	public static IDatabaseApi getDatabaseApi()
	{
		return getInstance().getDatabaseApiNonStatic();
	}

}
