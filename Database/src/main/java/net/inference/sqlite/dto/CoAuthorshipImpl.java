package net.inference.sqlite.dto;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import net.inference.database.dto.Author;
import net.inference.database.dto.CoAuthorship;
import net.inference.database.dto.PrimitiveCoAuthorship;

/**
 * Date: 12/19/2014
 * Time: 3:15 PM
 *
 * @author xanderblinov
 */

@DatabaseTable(tableName = CoAuthorship.TABLE_NAME)
public class CoAuthorshipImpl implements CoAuthorship {
	@DatabaseField(columnName = Column.id, generatedId = true)
	private int mId;
	@DatabaseField(foreign = true, columnName = Column.author)
	private AuthorImpl author;
	@DatabaseField(foreign = true, columnName = Column.coauthor)
	private AuthorImpl coauthor;
	@DatabaseField(columnName = PrimitiveCoAuthorship.Column.year)
	private int mYear;
	@DatabaseField(columnName = PrimitiveCoAuthorship.Column.article_id)
	private long mArticleId;

	public CoAuthorshipImpl()
	{
		// ORMLite needs a no-arg constructor
	}

	public CoAuthorshipImpl(final Author author, final Author coauthor)
	{
		this.author = (AuthorImpl) author;
		this.coauthor = (AuthorImpl) coauthor;
	}

    @Override
    public AuthorImpl getAuthor() {
        return author;
    }

    @Override
    public void setAuthor(Author author) {
        this.author = (AuthorImpl) author;
    }

    @Override
    public AuthorImpl getCoauthor() {
        return coauthor;
    }

    @Override
    public void setCoauthor(Author coauthor) {
        this.coauthor = (AuthorImpl) coauthor;
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
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		CoAuthorshipImpl that = (CoAuthorshipImpl) o;

		if (mId != that.mId) return false;
		if (mYear != that.mYear) return false;
		if (mArticleId != that.mArticleId) return false;
		if (author != null ? !author.equals(that.author) : that.author != null) return false;
		return !(coauthor != null ? !coauthor.equals(that.coauthor) : that.coauthor != null);

	}

	@Override
	public int hashCode() {
		int result = mId;
		result = 31 * result + (author != null ? author.hashCode() : 0);
		result = 31 * result + (coauthor != null ? coauthor.hashCode() : 0);
		result = 31 * result + mYear;
		result = 31 * result + (int) (mArticleId ^ (mArticleId >>> 32));
		return result;
	}

	@Override
	public String toString() {
		return "CoAuthorshipImpl{" +
				"mId=" + mId +
				", author=" + author +
				", coauthor=" + coauthor +
				", mYear=" + mYear +
				", mArticleId=" + mArticleId +
				'}';
	}
}
