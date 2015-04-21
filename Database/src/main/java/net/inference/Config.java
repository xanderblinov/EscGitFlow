package net.inference;

/**
 * Date: 12/21/2014
 * Time: 11:56 PM
 *
 * @author xanderblinov
 */
public class Config
{
	private static final String sqLitedatabaseExtension = ".db";

	public static enum Database
	{
		LIVE("netinference"),
		TEST("test");

		private final String mName;


		Database(final String name)
		{
			mName = name + sqLitedatabaseExtension;
		}

		public String getName()
		{
			return mName;
		}
	}
}
