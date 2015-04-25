package net.inference.database;

import com.j256.ormlite.support.ConnectionSource;

/**
 * Date: 12/21/2014
 * Time: 9:33 PM
 *
 * @author xanderblinov
 */
public interface DatabaseApi
{

	 ArticleApi article();
     AuthorApi  author();
	 ClusterApi cluster();
	 EvolutionApi evolution();

	/**
	 * Init database and table
	 */
	public void onStart();

	/**
	 * release connection etc.
	 */
	public void onStop();

	ConnectionSource getConnection();
}
