package net.inference.sqlite.dto;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import net.inference.database.dto.Article;

/**
 * Date: 12/19/2014
 * Time: 3:15 PM
 *
 * @author xanderblinov
 */

@DatabaseTable(tableName = Article.TABLE_NAME)
public class ArticleImpl implements Article
{
	@DatabaseField(columnName = Column.id, generatedId = true)
	private long mId;
	@DatabaseField(columnName = Column.name)
	private String mName;
	@DatabaseField(columnName = Column.sourceId)
	private String mSourceId;
	@DatabaseField(columnName = Column.year)
	private int mYear;
	@DatabaseField(columnName = Column.source)
	private int mSource;

 	public ArticleImpl()
	{
		// ORMLite needs a no-arg constructor
	}

	public ArticleImpl(final String name, final String sourceId, final int year, final int source)
	{
		mName = name;
		mSourceId = sourceId;
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

	public String getSourceId()
	{
		return mSourceId;
	}

	public void setSourceId(final String sourceId)
	{
		mSourceId = sourceId;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ArticleImpl article = (ArticleImpl) o;

		if (mId != article.mId) return false;
		if (mYear != article.mYear) return false;
		if (mSource != article.mSource) return false;
		if (mName != null ? !mName.equals(article.mName) : article.mName != null) return false;
		return  (mSourceId != null ? !mSourceId.equals(article.mSourceId) : article.mSourceId != null);

	}

	@Override
	public int hashCode() {
		int result = (int) mId;
		result = 31 * result + (mName != null ? mName.hashCode() : 0);
		result = 31 * result + (mSourceId != null ? mSourceId.hashCode() : 0);
		result = 31 * result + mYear;
		result = 31 * result + mSource;
		return result;
	}

	@Override
	public String toString() {
		return "ArticleImpl{" +
				"mId=" + mId +
				", mName='" + mName + '\'' +
				", mSourceId='" + mSourceId + '\'' +
				", mYear=" + mYear +
				", mSource=" + mSource +
				'}';
	}
}
