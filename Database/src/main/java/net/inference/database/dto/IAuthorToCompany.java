package net.inference.database.dto;

/**
 * Date: 4/25/2015
 * Time: 4:22 PM
 *
 * @author xanderblinov
 */
public interface IAuthorToCompany
{
	public static final String TABLE_NAME = "author_to_company";

	public static class Column
	{
		public static final String author_id = "author_id";
		public static final String company_id = "company_id";

	}
	long getId();

	IAuthor getAuthor();

	void setAuthor(IAuthor author);

	ICompany getCompany();

	void setCompany(ICompany company);
}
