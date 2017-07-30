package net.inference.sqlite;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;

import net.inference.database.ICompanyApi;
import net.inference.sqlite.dto.Company;

/**
 * Date: 4/25/2015
 * Time: 9:39 PM
 *
 * @author xanderblinov
 */
public class CompanyApi extends BaseApi<Company, Integer> implements ICompanyApi
{
	private final DatabaseApi mDatabaseApi;
	private static Logger logger = LoggerFactory.getLogger(Company.class);

	public CompanyApi(final DatabaseApi api)
	{
		super(api, Company.class);
		mDatabaseApi = api;
	}

	@Override
	public ArrayList<Company> addCompanies(List<Company> companies) throws Exception
	{
		final Dao<Company, Integer> dao = getDao();

		final ArrayList<Company> comps = dao.callBatchTasks(() ->
		{

			ArrayList<Company> createdCompanies = new ArrayList<>();


			for (Company comp : companies)
			{
				final Company company = dao.createIfNotExists(comp);
				createdCompanies.add(company);
			}
			return createdCompanies;
		});
		return comps;
	}

	@Override
	public Company addCompany(final Company company)
	{
		try
		{
			return getDao().createIfNotExists((Company) company);
		}
		catch (SQLException e)
		{
			logger.error(e, "");
		}
		return null;
	}
}
