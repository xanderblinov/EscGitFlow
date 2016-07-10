package net.inference.sqlite.dto;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import net.inference.database.dto.IPrimitiveTermToPrimitiveTerm;

/**
 * Created by M.Pankova on 24.10.15.
 */
@DatabaseTable(tableName = IPrimitiveTermToPrimitiveTerm.TABLE_NAME)
public class PrimitiveTermToPrimitiveTerm implements IPrimitiveTermToPrimitiveTerm
{
	@DatabaseField(columnName = IPrimitiveTermToPrimitiveTerm.Column.id, generatedId = true)
	private int mId;
	@DatabaseField(columnName = Column.from, foreign = true)
	private PrimitiveTerm mFrom;
	@DatabaseField(columnName = Column.to, foreign = true)
	private PrimitiveTerm mTo;
	@DatabaseField(columnName = Column.article_id, foreign = true)
	private Article mArticle;

	public PrimitiveTermToPrimitiveTerm()
	{
		// ORMLite needs a no-arg constructor
	}

	public PrimitiveTermToPrimitiveTerm(final PrimitiveTerm from, final PrimitiveTerm to, final Article article)
	{
		mFrom = from;
		mTo = to;
		mArticle = article;
	}



	@Override
	public PrimitiveTerm getFrom(){ return mFrom; }

	@Override
	public void setFrom(final PrimitiveTerm from){ mFrom = from; }

	@Override
	public PrimitiveTerm getTo(){ return mTo; }

	@Override
	public void setTo(final PrimitiveTerm to){ mTo = to; }

	@Override
	public Article getArticle(){ return mArticle; }

	@Override
	public void setArticle(final Article article){ mArticle = article; }

	@Override
	public long getId()
	{
		return mId;
	}

	@Override
	public String toString()
	{
		return "PrimitiveTermToTerm{" +
				"mId=" + mId +
				", mFrom=" + mFrom +
				", mTo=" + mTo +
				", mArticle=" + mArticle +
				'}';
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof PrimitiveTermToPrimitiveTerm))
		{
			return false;
		}

		PrimitiveTermToPrimitiveTerm that = (PrimitiveTermToPrimitiveTerm) o;

		if (getId() != that.getId())
		{
			return false;
		}
		if (!getFrom().equals(that.getFrom()))
		{
			return false;
		}
		if (!getArticle().equals(that.getArticle()))
		{
			return false;
		}
		if (!getTo().equals(that.getTo()))
		{
			return false;
		}
		//noinspection SimplifiableIfStatement
		return getTo().equals(that.getTo());

	}
}
