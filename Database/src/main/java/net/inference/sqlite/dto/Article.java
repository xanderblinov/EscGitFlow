package net.inference.sqlite.dto;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import net.inference.database.dto.IArticle;

/**
 * Date: 12/19/2014
 * Time: 3:15 PM
 *
 * @author xanderblinov
 */

@DatabaseTable(tableName = IArticle.TABLE_NAME)
public class Article implements IArticle
{
	@DatabaseField(columnName = Column.id, generatedId = true)
	private long mId;
	@DatabaseField(columnName = Column.name)
	private String mName;
	@DatabaseField(columnName = Column.id_in_source)
	private String mIdInSource;
	@DatabaseField(columnName = Column.year)
	private int mYear;
	@DatabaseField(columnName = Column.source)
	private int mSource;
	@DatabaseField(columnName = Column.processed_by_disambiguation_resolver)
	private boolean mProcessed;

 	public Article()
	{
		// ORMLite needs a no-arg constructor
	}

	public Article(final String name, final String idInSource, final int year, final int source)
	{
		mName = name;
		mIdInSource = idInSource;
		mYear = year;
		mSource = source;
	}


	public String getName()
	{
		return mName;
	}

	public void setName(final String name)
	{
		mName = name;
	}

	public String getIdInSource()
	{
		return mIdInSource;
	}

	public void setIdInSource(final String idInSource)
	{
		mIdInSource = idInSource;
	}

	public int getYear()
	{
		return mYear;
	}

	public void setYear(final int year)
	{
		mYear = year;
	}

	public int getSource()
	{
		return mSource;
	}

	public void setSource(final int source)
	{
		mSource = source;
	}


    @Override
    public long getId() {
        return mId;
    }


	public boolean isProcessed()
	{
		return mProcessed;
	}

	public void setProcessed(boolean processed)
	{
		mProcessed = processed;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Article article = (Article) o;

		if (mId != article.mId) return false;
		if (mYear != article.mYear) return false;
		if (mSource != article.mSource) return false;
		if (mName != null ? !mName.equals(article.mName) : article.mName != null) return false;
		return  (mIdInSource != null ? !mIdInSource.equals(article.mIdInSource) : article.mIdInSource != null);

	}

	@Override
	public int hashCode() {
		int result = (int) mId;
		result = 31 * result + (mName != null ? mName.hashCode() : 0);
		result = 31 * result + (mIdInSource != null ? mIdInSource.hashCode() : 0);
		result = 31 * result + mYear;
		result = 31 * result + mSource;
		return result;
	}

	@Override
	public String toString() {
		return "ArticleImpl{" +
				"mId=" + mId +
				", mName='" + mName + '\'' +
				", mIdInSource='" + mIdInSource + '\'' +
				", mYear=" + mYear +
				", mSource=" + mSource +
				'}';
	}
}
