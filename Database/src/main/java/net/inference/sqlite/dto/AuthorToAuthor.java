package net.inference.sqlite.dto;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import net.inference.database.dto.IAuthorToAuthor;
import net.inference.database.dto.IPrimitiveAuthorToAuthor;

/**
 * Date: 12/19/2014
 * Time: 3:15 PM
 *
 * @author xanderblinov
 */

@DatabaseTable(tableName = IAuthorToAuthor.TABLE_NAME)
public class AuthorToAuthor implements IAuthorToAuthor
{
	@DatabaseField(columnName = Column.id, generatedId = true)
	private int mId;
	@DatabaseField(foreign = true, columnName = Column.author)
	private Author mAuthor;
	@DatabaseField(foreign = true, columnName = Column.coauthor)
	private Author mCoauthor;
	@DatabaseField(columnName = IPrimitiveAuthorToAuthor.Column.year)
	private int mYear;
	@DatabaseField(columnName = IPrimitiveAuthorToAuthor.Column.article_id)
	private long mArticleId;

	public AuthorToAuthor()
	{
		// ORMLite needs a no-arg constructor
	}

	public AuthorToAuthor(final Author author, final Author coauthor)
	{
		this.mAuthor = author;
		this.mCoauthor = coauthor;
	}

	@Override
	public Author getAuthor()
	{
		return mAuthor;
	}

	@Override
	public void setAuthor(Author author)
	{
		this.mAuthor = author;
	}

	@Override
	public Author getCoauthor()
	{
		return mCoauthor;
	}

	@Override
	public void setCoauthor(Author coauthor)
	{
		this.mCoauthor = coauthor;
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
	public long getId()
	{
		return mId;
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

		AuthorToAuthor that = (AuthorToAuthor) o;

		if (mId != that.mId)
		{
			return false;
		}
		if (mYear != that.mYear)
		{
			return false;
		}
		if (mArticleId != that.mArticleId)
		{
			return false;
		}
		if (mAuthor != null ? !mAuthor.equals(that.mAuthor) : that.mAuthor != null)
		{
			return false;
		}
		return !(mCoauthor != null ? !mCoauthor.equals(that.mCoauthor) : that.mCoauthor != null);

	}

	@Override
	public int hashCode()
	{
		int result = mId;
		result = 31 * result + (mAuthor != null ? mAuthor.hashCode() : 0);
		result = 31 * result + (mCoauthor != null ? mCoauthor.hashCode() : 0);
		result = 31 * result + mYear;
		result = 31 * result + (int) (mArticleId ^ (mArticleId >>> 32));
		return result;
	}

	@Override
	public String toString()
	{
		return "AuthorToAuthor{" +
				"mId=" + mId +
				", mAuthor=" + mAuthor +
				", mCoauthor=" + mCoauthor +
				", mYear=" + mYear +
				", mArticleId=" + mArticleId +
				'}';
	}
}
