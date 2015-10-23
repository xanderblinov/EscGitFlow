package net.inference.sqlite;

import net.inference.database.IAuthorArticleApi;
import net.inference.database.IDatabaseApi;
import net.inference.sqlite.dto.AuthorToArticle;

/**
 * Date: 4/25/2015
 * Time: 9:39 PM
 *
 * @author xanderblinov
 */
public class AuthorToArticleApi extends BaseApi<AuthorToArticle, Integer> implements IAuthorArticleApi
{
	public AuthorToArticleApi(final IDatabaseApi api)
	{
		super(api, AuthorToArticle.class);
	}
}
