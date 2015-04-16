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
	private int mId;
	@DatabaseField(columnName = Column.name)
	private String mName;
	@DatabaseField(columnName = Column.sourceId)
	private String mSourceId;
	@DatabaseField(columnName = Column.year)
	private int mYear;
	@DatabaseField(columnName = Column.source)
	private int mSource;

    @DatabaseField(foreign = true, columnName = Column.AUTHOR_ID_FIELD_NAME)
    private AuthorImpl author;

	public ArticleImpl()
	{
		// ORMLite needs a no-arg constructor
	}

	public ArticleImpl(AuthorImpl author, final String name, final String sourceId, final int year, final int source)
	{
		mName = name;
		mSourceId = sourceId;
		mYear = year;
		mSource = source;
        setAuthor(author);
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

    public AuthorImpl getAuthor() {
        return author;
    }

    public void setAuthor(AuthorImpl author) {
        this.author = author;
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
		if (mSourceId != null ? !mSourceId.equals(article.mSourceId) : article.mSourceId != null) return false;
		return !(author != null ? !author.equals(article.author) : article.author != null);

	}

	@Override
	public int hashCode() {
		int result = mId;
		result = 31 * result + (mName != null ? mName.hashCode() : 0);
		result = 31 * result + (mSourceId != null ? mSourceId.hashCode() : 0);
		result = 31 * result + mYear;
		result = 31 * result + mSource;
		result = 31 * result + (author != null ? author.hashCode() : 0);
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
				", author=" + author +
				'}';
	}
}
