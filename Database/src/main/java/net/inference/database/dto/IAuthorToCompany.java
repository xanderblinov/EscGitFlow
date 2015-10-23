package net.inference.database.dto;

/**
 * Date: 4/25/2015
 * Time: 4:22 PM
 *
 * @author xanderblinov
 */
public interface IAuthorToCompany
{
	String TABLE_NAME = "author_to_company";

	class Column
	{
		public static final String author = "author";
		public static final String company = "company";

	}
	long getId();

	IAuthor getAuthor();

	void setAuthor(IAuthor author);

	ICompany getCompany();

	void setCompany(ICompany company);
}
