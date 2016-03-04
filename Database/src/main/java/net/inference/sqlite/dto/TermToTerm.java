package net.inference.sqlite.dto;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import net.inference.database.dto.ITermToTerm;
import net.inference.database.dto.ITerm;

/**
 * Created by M.Pankova on 15.12.15.
 */
@DatabaseTable(tableName = ITermToTerm.TABLE_NAME)
public class TermToTerm implements ITermToTerm
{
	@DatabaseField(columnName = ITermToTerm.Column.id, generatedId = true)
	private int mId;
	@DatabaseField(columnName = Column.from, foreign = true)
	private Term mFrom;
	@DatabaseField(columnName = Column.to, foreign = true)
	private Term mTo;
	@DatabaseField(columnName = Column.count)
	private int mCount;

	public TermToTerm()
	{
		// ORMLite needs a no-arg constructor
	}

	public TermToTerm(final Term from, final Term to)
	{
		mFrom = from;
		mTo = to;
		mCount = 1;
	}

	@Override
	public Term getFrom(){ return mFrom; }

	@Override
	public void setFrom(final Term from){ mFrom = from; }

	@Override
	public Term getTo(){ return mTo; }

	@Override
	public void setTo(final Term to){ mTo = to; }

	@Override
	public long getId()
	{
		return mId;
	}

	@Override
	public int getCount(){ return mCount; }

	@Override
	public void incCount(){ mCount++; };

	@Override
	public void decCount(){ mCount--; };

	@Override
	public String toString()
	{
		return "TermToTerm{" +
				"mId=" + mId +
				", mFrom=" + mFrom +
				", mTo=" + mTo +
				", mCount=" + mCount +
			'}';
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof TermToTerm))
		{
			return false;
		}

		TermToTerm that = (TermToTerm) o;

		if (getId() != that.getId())
		{
			return false;
		}
		if (!getFrom().equals(that.getFrom()))
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
