package net.inference.sqlite.dto;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import net.inference.database.dto.Company;

/**
 * Date: 4/25/2015
 * Time: 4:06 PM
 *
 * @author xanderblinov
 */
@DatabaseTable(tableName = Company.TABLE_NAME)
public class CompanyImpl implements Company
{
	@DatabaseField(columnName = Company.Column.id, generatedId = true)
	private long mId;
	@DatabaseField(columnName = Column.name)
	private String name;

	public CompanyImpl()
	{
	}

	public CompanyImpl(final String name)
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

		CompanyImpl company = (CompanyImpl) o;

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
