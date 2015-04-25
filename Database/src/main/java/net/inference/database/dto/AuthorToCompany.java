package net.inference.database.dto;

/**
 * Date: 4/25/2015
 * Time: 4:22 PM
 *
 * @author xanderblinov
 */
public interface AuthorToCompany
{
	public static final String TABLE_NAME = "author_to_company";

	public static class Column
	{
		public static final String author_id = "author_id";
		public static final String company_id = "company_id";

	}
	long getId();

	Author getAuthor();

	void setAuthor(Author author);

	Company getCompany();

	void setCompany(Company company);
}
