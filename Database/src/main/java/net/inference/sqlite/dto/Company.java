package net.inference.sqlite.dto;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import net.inference.database.dto.ICompany;

/**
 * Date: 4/25/2015
 * Time: 4:06 PM
 *
 * @author xanderblinov
 */
@DatabaseTable(tableName = ICompany.TABLE_NAME)
public class Company implements ICompany
{
	@DatabaseField(columnName = ICompany.Column.id, generatedId = true)
	private long mId;
	@DatabaseField(columnName = Column.name)
	private String name;

	public Company()
	{
	}

	public Company(final String name)
	{
		setName(name);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	public long getId()
	{
		return mId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Company company = (Company) o;

		if (mId != company.mId) return false;
		return !(name != null ? !name.equals(company.name) : company.name != null);

	}

	@Override
	public int hashCode() {
		int result = (int) (mId ^ (mId >>> 32));
		result = 31 * result + (name != null ? name.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "CompanyImpl{" +
				"mId=" + mId +
				", name=" + name +
				'}';
	}
}
