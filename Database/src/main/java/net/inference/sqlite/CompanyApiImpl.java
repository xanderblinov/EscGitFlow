package net.inference.sqlite;

import net.inference.database.CompanyApi;
import net.inference.database.DatabaseApi;
import net.inference.sqlite.dto.CompanyImpl;

/**
 * Date: 4/25/2015
 * Time: 9:39 PM
 *
 * @author xanderblinov
 */
public class CompanyApiImpl extends BaseApiImpl<CompanyImpl, Integer> implements CompanyApi
{
	public CompanyApiImpl(final DatabaseApi api)
	{
		super(api, CompanyImpl.class);
	}
}
