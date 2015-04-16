package net.inference;

import net.inference.database.DatabaseApi;
import net.inference.sqlite.SqliteApi;

/**
 * Date: 4/16/2015
 * Time: 9:08 PM
 *
 * @author xanderblinov
 */
public class DatabaseApiFactory
{
	public static DatabaseApi getDatabaseApi(DatabaseType databaseType,Config.Database config, boolean reCreate)
	{
		switch (databaseType)
		{
			case Sqlite:
				return new SqliteApi(config, reCreate);
		}
		return null;
	}

	public enum DatabaseType
	{
		Sqlite
	}
}
