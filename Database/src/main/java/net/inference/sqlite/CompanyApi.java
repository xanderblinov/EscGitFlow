package net.inference.sqlite;

import net.inference.database.ICompanyApi;
import net.inference.database.IDatabaseApi;
import net.inference.sqlite.dto.Company;

/**
 * Date: 4/25/2015
 * Time: 9:39 PM
 *
 * @author xanderblinov
 */
public class CompanyApi extends BaseApi<Company, Integer> implements ICompanyApi
{
	public CompanyApi(final IDatabaseApi api)
	{
		super(api, Company.class);
	}
}
