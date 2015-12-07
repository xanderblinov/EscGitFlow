package net.inference.sqlite.dto;

<<<<<<< HEAD
import com.j256.ormlite.field.DataType;
=======
import java.util.ArrayList;

>>>>>>> a9cc4415ea4d00a3a9eb1c48a102c7509f91dfdb
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import net.inference.database.dto.IPrimitiveTerm;

<<<<<<< HEAD
import java.util.Arrays;
import java.util.List;

=======
>>>>>>> a9cc4415ea4d00a3a9eb1c48a102c7509f91dfdb
/**
 * Date: 12/19/2014
 * Time: 3:15 PM
 *
 * @author xanderblinov
 */

@DatabaseTable(tableName = IPrimitiveTerm.TABLE_NAME)
public class PrimitiveTerm implements IPrimitiveTerm
{
<<<<<<< HEAD
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

=======
>>>>>>> a9cc4415ea4d00a3a9eb1c48a102c7509f91dfdb
	@DatabaseField(columnName = Column.id, generatedId = true)
	private int mId;
	@DatabaseField(columnName = Column.value)
	private String mValue;
<<<<<<< HEAD
	@DatabaseField(columnName = Column.type, dataType = DataType.ENUM_STRING)
	private TermType mType;
	@DatabaseField(columnName = Column.date)
	private String mDate;
	@DatabaseField(columnName = Column.publication, foreign = true)
	private Article mPublication;
	@DatabaseField(columnName = Column.term, foreign = true)
	private Term mTerm;
=======
	@DatabaseField(columnName = Column.type)
	private String mType;
	@DatabaseField(columnName = Column.date)
	private int mDate;
	@DatabaseField(columnName = Column.publication, foreign = true)
	private Article mPublication;
	@DatabaseField(columnName = Column.term, foreign = true)
	private Term mTerm;                     //пока что String, заменить на Term (когда будет Term)



>>>>>>> a9cc4415ea4d00a3a9eb1c48a102c7509f91dfdb


	public PrimitiveTerm()
	{
		// ORMLite needs a no-arg constructor
	}


<<<<<<< HEAD
	public PrimitiveTerm(String value, TermType type, Article article)
=======
	public PrimitiveTerm(String value, String type, int date, Article article)
>>>>>>> a9cc4415ea4d00a3a9eb1c48a102c7509f91dfdb
	{
		mType = type;
		mValue = value;
		mPublication = article;
<<<<<<< HEAD
=======
		mDate = date;
>>>>>>> a9cc4415ea4d00a3a9eb1c48a102c7509f91dfdb
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

<<<<<<< HEAD
	public static List<String> separatePrimitiveTerms(String keyWords)
	{
		String[] primitiveTermsArr = keyWords.split(PRIMITIVE_TERM_SEPARATOR);

		return Arrays.asList(primitiveTermsArr);
=======
	public ArrayList<String> separatePrimitiveTerms()
	{
		ArrayList<String> createdPrimitiveTerms = new ArrayList<>();
		String[] primitiveTermsArr = this.getValue().split(",");
		for (int i = 0; i < primitiveTermsArr.length; i++)
			createdPrimitiveTerms.add(primitiveTermsArr[i]);
		return createdPrimitiveTerms;
>>>>>>> a9cc4415ea4d00a3a9eb1c48a102c7509f91dfdb
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
