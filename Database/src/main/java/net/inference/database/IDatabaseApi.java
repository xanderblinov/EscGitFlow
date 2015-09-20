package net.inference.database;

import com.j256.ormlite.support.ConnectionSource;

/**
 * Date: 12/21/2014
 * Time: 9:33 PM
 *
 * @author xanderblinov
 */
public interface IDatabaseApi
{

	IArticleApi article();

	IAuthorApi author();

	IClusterApi cluster();

	IEvolutionApi evolution();

	IAuthorArticleApi authorArticle();

	IAuthorCompanyApi authorCompany();

	ICompanyApi company();

	IPrimitiveAuthorApi primitiveAuthor();

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
