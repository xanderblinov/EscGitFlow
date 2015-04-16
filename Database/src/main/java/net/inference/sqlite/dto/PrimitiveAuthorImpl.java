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
	@DatabaseField(columnName = Column.article_id)
	private int mArticleId;
	@DatabaseField(columnName = Column.source)
	private String mSource;
	@DatabaseField(columnName = Column.encoding)
	private String mEncoding;
	@DatabaseField(columnName = Column.inference_id)
	private long mInferenceId;

	public PrimitiveAuthorImpl()
	{
		// ORMLite needs a no-arg constructor
	}

	public PrimitiveAuthorImpl(final int id, final String name, final String Surname, final int articleId)
	{
		mId = id;
		mName = name;
		mSurname = Surname;
		mArticleId = articleId;
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

	public int getArticleId()
	{
		return mArticleId;
	}

	public void setArticleId(final int articleId)
	{
		mArticleId = articleId;
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
    public long getId() {
        return mId;
    }

	@Override
	public String toString() {
		return "PrimitiveAuthorImpl{" +
				"mId=" + mId +
				", mName='" + mName + '\'' +
				", mSurname='" + mSurname + '\'' +
				", mArticleId=" + mArticleId +
				", mSource='" + mSource + '\'' +
				", mEncoding='" + mEncoding + '\'' +
				", mInferenceId=" + mInferenceId +
				'}';
	}
}
