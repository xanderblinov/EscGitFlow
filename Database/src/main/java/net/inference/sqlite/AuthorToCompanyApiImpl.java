package net.inference.sqlite;

import net.inference.database.AuthorCompanyApi;
import net.inference.database.DatabaseApi;
import net.inference.sqlite.dto.AuthorToCompanyImpl;

/**
 * Date: 4/25/2015
 * Time: 9:39 PM
 *
 * @author xanderblinov
 */
public class AuthorToCompanyApiImpl extends BaseApiImpl<AuthorToCompanyImpl, Integer> implements AuthorCompanyApi
{
	public AuthorToCompanyApiImpl(final DatabaseApi api)
	{
		super(api, AuthorToCompanyImpl.class);
	}
}
