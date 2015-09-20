package net.inference.sqlite.dto;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import net.inference.database.dto.IAuthor;
import net.inference.database.dto.IAuthorToCompany;
import net.inference.database.dto.ICompany;

/**
 * Date: 4/25/2015
 * Time: 4:20 PM
 *
 * @author xanderblinov
 */
@DatabaseTable(tableName = IAuthorToCompany.TABLE_NAME)
public class AuthorToCompany implements IAuthorToCompany
{


	@DatabaseField(generatedId = true)
	private long id;

	@DatabaseField(foreign = true, columnName = Column.author)
	private Author author;
	@DatabaseField(foreign = true, columnName = Column.company)
	private Company company;


	public AuthorToCompany()
	{
	}

	public AuthorToCompany(final IAuthor author, final ICompany company)
	{
		setAuthor(author);
		setCompany(company);
	}


	public IAuthor getAuthor()
	{
		return author;
	}

	public void setAuthor(IAuthor author)
	{
		this.author = (Author) author;
	}

	public ICompany getCompany()
	{
		return company;
	}

	@Override
	public void setCompany(final ICompany company)
	{
		this.company = (Company) company;
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

		IAuthorToCompany that = (IAuthorToCompany) o;

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
