package net.inference.sqlite.dto;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import net.inference.database.dto.PrimitiveCoAuthorship;

/**
 * Date: 12/19/2014
 * Time: 3:15 PM
 *
 * @author xanderblinov
 */

@DatabaseTable(tableName = PrimitiveCoAuthorship.TABLE_NAME)
public class PrimitiveCoAuthorshipImpl implements PrimitiveCoAuthorship
{
	@DatabaseField(columnName = Column.id, generatedId = true)
	private int mId;
	@DatabaseField(columnName = Column.author)
	private String mAuthor;
	@DatabaseField(columnName = Column.coauthor)
	private String mCoauthor;
	@DatabaseField(columnName = Column.year)
	private int mYear;
	@DatabaseField(columnName = Column.article_id)
	private long mArticleId;

	public PrimitiveCoAuthorshipImpl()
	{
		// ORMLite needs a no-arg constructor
	}

	public PrimitiveCoAuthorshipImpl(final String author, final String coauthor)
	{
		mAuthor = author;
		mCoauthor = coauthor;
	}

	public String getAuthor()
	{
		return mAuthor;
	}

	public void setAuthor(final String author)
	{
		mAuthor = author;
	}

	public String getCoauthor()
	{
		return mCoauthor;
	}

	public void setCoauthor(final String coauthor)
	{
		mCoauthor = coauthor;
	}

	public int getYear()
	{
		return mYear;
	}

	public void setYear(final int year)
	{
		mYear = year;
	}

	public long getArticleId()
	{
		return mArticleId;
	}

	public void setArticleId(final long articleId)
	{
		mArticleId = articleId;
	}

    @Override
    public long getId() {
        return mId;
    }

	@Override
	public String toString() {
		return "PrimitiveCoAuthorshipImpl{" +
				"mId=" + mId +
				", mAuthor='" + mAuthor + '\'' +
				", mCoauthor='" + mCoauthor + '\'' +
				", mYear=" + mYear +
				", mArticleId=" + mArticleId +
				'}';
	}
}
