package net.inference.database;

import java.util.ArrayList;
import java.util.List;

import net.inference.sqlite.dto.Company;

/**
 * Date: 12/23/2014
 * Time: 11:23 PM
 *
 * @author xanderblinov
 */
public interface ICompanyApi extends IBaseApi<Company,Integer>
{
    ArrayList<Company> addCompanies(List<Company> companies) throws Exception;
    Company addCompany(final Company company);
}
