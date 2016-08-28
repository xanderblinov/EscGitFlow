package net.inference.database;

import net.inference.sqlite.dto.Company;
import net.inference.sqlite.dto.PrimitiveAuthor;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 12/23/2014
 * Time: 11:23 PM
 *
 * @author xanderblinov
 */
public interface ICompanyApi extends IBaseApi<Company,Integer>
{
    ArrayList<Company> addCompanies(List<Company> companies) throws Exception;
}
