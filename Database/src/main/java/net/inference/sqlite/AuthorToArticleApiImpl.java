package net.inference.sqlite;

import net.inference.database.AuthorArticleApi;
import net.inference.database.DatabaseApi;
import net.inference.sqlite.dto.AuthorToArticleImpl;

/**
 * Date: 4/25/2015
 * Time: 9:39 PM
 *
 * @author xanderblinov
 */
public class AuthorToArticleApiImpl extends BaseApiImpl<AuthorToArticleImpl, Integer> implements AuthorArticleApi
{
	public AuthorToArticleApiImpl(final DatabaseApi api)
	{
		super(api, AuthorToArticleImpl.class);
	}
}
