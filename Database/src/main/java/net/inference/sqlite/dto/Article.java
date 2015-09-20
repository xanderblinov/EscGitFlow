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
	@DatabaseField(columnName = Column.title)
	private String mTitle;
	@DatabaseField(columnName = Column.id_in_source)
	private String mIdInSource;
	@DatabaseField(columnName = Column.year)
	private int mYear;
	@DatabaseField(columnName = Column.source)
	private ArticleSource mArticleSource;
	@DatabaseField(columnName = Column.processed_by_disambiguation_resolver)
	private boolean mProcessed;


	public Article()
	{
		// ORMLite needs a no-arg constructor
	}

	public Article(final String title, final String idInSource, final int year, ArticleSource articleSource)
	{
		mTitle = title;
		mIdInSource = idInSource;
		mYear = year;
		mArticleSource = articleSource;
	}


	public String getTitle()
	{
		return mTitle;
	}

	public void setTitle(final String title)
	{
		mTitle = title;
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


	public ArticleSource getArticleSource()
	{
		return mArticleSource;
	}

	public void setArticleSource(ArticleSource articleSource)
	{
		mArticleSource = articleSource;
	}

	@Override
	public long getId()
	{
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

		Article article = (Article) o;

		if (mId != article.mId)
		{
			return false;
		}
		if (mYear != article.mYear)
		{
			return false;
		}
		if (mArticleSource != article.mArticleSource)
		{
			return false;
		}
		if (mTitle != null ? !mTitle.equals(article.mTitle) : article.mTitle != null)
		{
			return false;
		}
		return (mIdInSource != null ? !mIdInSource.equals(article.mIdInSource) : article.mIdInSource != null);

	}

	@Override
	public int hashCode()
	{
		int result = (int) mId;
		result = 31 * result + (mTitle != null ? mTitle.hashCode() : 0);
		result = 31 * result + (mIdInSource != null ? mIdInSource.hashCode() : 0);
		result = 31 * result + mYear;
		result = 31 * result + (mArticleSource != null ? mArticleSource.ordinal() : -1);
		return result;
	}

	@Override
	public String toString()
	{
		return "Article{" +
		"mId=" + mId +
		", mTitle='" + mTitle + '\'' +
		", mIdInSource='" + mIdInSource + '\'' +
		", mYear=" + mYear +
		", mSource=" + mArticleSource +
		'}';
	}

}
