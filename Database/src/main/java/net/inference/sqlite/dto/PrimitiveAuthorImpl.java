package net.inference.sqlite.dto;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import net.inference.database.dto.PrimitiveAuthor;

/**
 * Date: 12/19/2014
 * Time: 3:15 PM
 *
 * @author xanderblinov
 */

@DatabaseTable(tableName = PrimitiveAuthor.TABLE_NAME)
public class PrimitiveAuthorImpl implements PrimitiveAuthor
{
	@DatabaseField(columnName = Column.id, generatedId = true)
	private int mId;
	@DatabaseField(columnName = Column.name)
	private String mName;
	@DatabaseField(columnName = Column.surname)
	private String mSurname;
	@DatabaseField(columnName = Column.article, foreign = true)
	private ArticleImpl mArticle;
	@DatabaseField(columnName = Column.source)
	private String mSource;
	@DatabaseField(columnName = Column.encoding)
	private String mEncoding;
	@DatabaseField(columnName = Column.inference_id)
	private long mInferenceId;

	@SuppressWarnings("unused")
	public PrimitiveAuthorImpl()
	{
		// ORMLite needs a no-arg constructor
	}

	public PrimitiveAuthorImpl(final String name, final String surname, final ArticleImpl article)
	{
		mName = name;
		mSurname = surname;
		mArticle = article;
	}

	public String getName()
	{
		return mName;
	}

	public void setName(final String name)
	{
		mName = name;
	}

	public String getSurname()
	{
		return mSurname;
	}

	public void setSurname(final String surname)
	{
		mSurname = surname;
	}

	public ArticleImpl getArticle()
	{
		return mArticle;
	}

	public void setArticle(final ArticleImpl article)
	{
		mArticle = article;
	}

	public String getSource()
	{
		return mSource;
	}

	public void setSource(final String source)
	{
		mSource = source;
	}

	public String getEncoding()
	{
		return mEncoding;
	}

	public void setEncoding(final String encoding)
	{
		mEncoding = encoding;
	}

	public long getInferenceId()
	{
		return mInferenceId;
	}

	public void setInferenceId(final long inferenceId)
	{
		mInferenceId = inferenceId;
	}

	@Override
	public long getId()
	{
		return mId;
	}

	@Override
	public String toString()
	{
		return "PrimitiveAuthorImpl{" +
		"mId=" + mId +
		", mName='" + mName + '\'' +
		", mSurname='" + mSurname + '\'' +
		", mArticleId=" + (mArticle == null ? -1 : mArticle.getId()) +
		", mSource='" + mSource + '\'' +
		", mEncoding='" + mEncoding + '\'' +
		", mInferenceId=" + mInferenceId +
		'}';
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof PrimitiveAuthorImpl))
		{
			return false;
		}

		PrimitiveAuthorImpl that = (PrimitiveAuthorImpl) o;

		if (getId() != that.getId())
		{
			return false;
		}
		if (getArticle() != that.getArticle())
		{
			return false;
		}
		if (!getName().equals(that.getName()))
		{
			return false;
		}
		//noinspection SimplifiableIfStatement
		if (!getSurname().equals(that.getSurname()))
		{
			return false;
		}
		return !(getSource() != null ? !getSource().equals(that.getSource()) : that.getSource() != null);

	}

	@Override
	public int hashCode()
	{
		int result = (int) getId();
		result = 31 * result + getName().hashCode();
		result = 31 * result + getSurname().hashCode();
		result = 31 * result + (int) (mArticle == null ? 0 : mArticle.getId());
		result = 31 * result + (getSource() != null ? getSource().hashCode() : 0);
		return result;
	}
}
