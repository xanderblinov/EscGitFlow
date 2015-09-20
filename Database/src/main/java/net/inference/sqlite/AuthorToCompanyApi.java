package net.inference.sqlite;

import net.inference.database.IAuthorCompanyApi;
import net.inference.database.IDatabaseApi;
import net.inference.sqlite.dto.AuthorToCompany;

/**
 * Date: 4/25/2015
 * Time: 9:39 PM
 *
 * @author xanderblinov
 */
public class AuthorToCompanyApi extends BaseApi<AuthorToCompany, Integer> implements IAuthorCompanyApi
{
	public AuthorToCompanyApi(final IDatabaseApi api)
	{
		super(api, AuthorToCompany.class);
	}
}
