package net.inference.sqlite.dto;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import net.inference.database.dto.IPrimitiveTerm;

import java.util.Arrays;
import java.util.List;

/**
 * Date: 12/19/2014
 * Time: 3:15 PM
 *
 * @author xanderblinov
 */

@DatabaseTable(tableName = IPrimitiveTerm.TABLE_NAME)
public class PrimitiveTerm implements IPrimitiveTerm
{
	public static final String PRIMITIVE_TERM_SEPARATOR = ",";

	public enum TermType
	{
		OT("OT"), MH("MH");

		private final String mAbbreviation;

		TermType(String abbreviation)
		{

			mAbbreviation = abbreviation;
		}

		public String getAbbreviation()
		{
			return mAbbreviation;
		}
	}

	@DatabaseField(columnName = Column.id, generatedId = true)
	private int mId;
	@DatabaseField(columnName = Column.value)
	private String mValue;
	@DatabaseField(columnName = Column.type, dataType = DataType.ENUM_STRING)
	private TermType mType;
	@DatabaseField(columnName = Column.date)
	private String mDate;
	@DatabaseField(columnName = Column.publication, foreign = true)
	private Article mPublication;
	@DatabaseField(columnName = Column.term, foreign = true)
	private Term mTerm;


	public PrimitiveTerm()
	{
		// ORMLite needs a no-arg constructor
	}


	public PrimitiveTerm(String value, TermType type, Article article)
	{
		mType = type;
		mValue = value;
		mPublication = article;
	}

	public String getValue()
	{
		return mValue;
	}

	@Override
	public String getPublication()
	{
		return null;
	}

	@Override
	public void setPublication(String publication)
	{

	}

	public static List<String> separatePrimitiveTerms(String keyWords)
	{
		String[] primitiveTermsArr = keyWords.split(PRIMITIVE_TERM_SEPARATOR);

		return Arrays.asList(primitiveTermsArr);
	}


	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof PrimitiveTerm))
		{
			return false;
		}

		PrimitiveTerm term = (PrimitiveTerm) o;

		return !(getValue() != null ? !getValue().equals(term.getValue()) : term.getValue() != null);

	}

	@Override
	public int hashCode()
	{
		return getValue() != null ? getValue().hashCode() : 0;
	}

	@Override
	public String toString()
	{
		return mValue;
	}
}
