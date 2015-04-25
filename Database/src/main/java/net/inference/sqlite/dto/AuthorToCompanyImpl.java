package net.inference.sqlite.dto;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import net.inference.database.dto.Author;
import net.inference.database.dto.AuthorToCompany;
import net.inference.database.dto.Company;

/**
 * Date: 4/25/2015
 * Time: 4:20 PM
 *
 * @author xanderblinov
 */
@DatabaseTable(tableName = AuthorToCompany.TABLE_NAME)
public class AuthorToCompanyImpl implements AuthorToCompany
{


	@DatabaseField(generatedId = true)
	private long id;

	@DatabaseField(foreign = true, columnName = Column.author_id)
	private AuthorImpl author;
	@DatabaseField(foreign = true, columnName = Column.company_id)
	private CompanyImpl company;


	public AuthorToCompanyImpl()
	{
	}

	public AuthorToCompanyImpl(final Author author, final Company company)
	{
		setAuthor(author);
		setCompany(company);
	}


	public Author getAuthor()
	{
		return author;
	}

	public void setAuthor(Author author)
	{
		this.author = (AuthorImpl) author;
	}

	public Company getCompany()
	{
		return company;
	}

	@Override
	public void setCompany(final Company company)
	{
		this.company = (CompanyImpl) company;
	}


	@Override
	public long getId()
	{
		return id;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null || getClass() != o.getClass())
		{
			return false;
		}

		AuthorToCompany that = (AuthorToCompany) o;

		if (id != that.getId())
		{
			return false;
		}
		if (author != null ? !author.equals(that.getAuthor()) : that.getAuthor() != null)
		{
			return false;
		}
		return !(company != null ? !company.equals(that.getCompany()) : that.getCompany() != null);

	}

	@Override
	public int hashCode()
	{
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (author != null ? author.hashCode() : 0);
		result = 31 * result + (company != null ? company.hashCode() : 0);
		return result;
	}

	@Override
	public String toString()
	{
		return "AuthorToCompanyImpl{" +
				"id=" + id +
				", author=" + author +
				", company=" + company +
				'}';
	}
}
