package net.inference.sqlite.dto;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import net.inference.database.dto.IClusteringType;
import net.inference.database.dto.IEvolution;

<<<<<<< HEAD
import java.util.Arrays;

=======
>>>>>>> a9cc4415ea4d00a3a9eb1c48a102c7509f91dfdb
/**
 * Date: 2/1/2015
 * Time: 5:53 PM
 *
 * @author xanderblinov
 */
@DatabaseTable(tableName = IEvolution.TABLE_NAME)
public class Evolution implements IEvolution
{

	@DatabaseField(columnName = Column.id, generatedId = true)
	private int mId;
	@DatabaseField(columnName = Column.time)
	private String mTime;
	@DatabaseField(columnName = Column.type_id, dataType = DataType.ENUM_STRING)
	private IClusteringType mType;
	@DatabaseField(columnName = Column.from_year)
	private String mFromYear;
	@DatabaseField(columnName = Column.to_year)
	private String mToYear;
<<<<<<< HEAD
	@DatabaseField(columnName = Column.params,dataType = DataType.SERIALIZABLE)
	private double[] mParams;
=======


>>>>>>> a9cc4415ea4d00a3a9eb1c48a102c7509f91dfdb
    @ForeignCollectionField
    private ForeignCollection<EvolutionSlice> slices;


	public long getId()
	{
		return mId;
	}

	public String getTime()
	{
		return mTime;
	}

	public void setTime(final String time)
	{
		mTime = time;
	}

	public IClusteringType getType()
	{
		return mType;
	}

	public void setType(final IClusteringType type)
	{
		mType = type;
	}

	public String getFromYear()
	{
		return mFromYear;
	}

	public void setFromYear(final String fromYear)
	{
		mFromYear = fromYear;
	}

	public String getToYear()
	{
		return mToYear;
	}

	public void setToYear(final String toYear)
	{
		mToYear = toYear;
	}

    public ForeignCollection<EvolutionSlice> getSlices() {
        return slices;
    }

<<<<<<< HEAD
	public double[] getParams()
	{
		return mParams;
	}

	public void setParams(double[] params)
	{
		mParams = params;
	}

	@Override
=======
    @Override
>>>>>>> a9cc4415ea4d00a3a9eb1c48a102c7509f91dfdb
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Evolution evolution = (Evolution) o;

        if (mFromYear != null ? !mFromYear.equals(evolution.mFromYear) : evolution.mFromYear != null) return false;
        if (mTime != null ? !mTime.equals(evolution.mTime) : evolution.mTime != null) return false;
        if (mToYear != null ? !mToYear.equals(evolution.mToYear) : evolution.mToYear != null) return false;
        if (mType != evolution.mType) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mTime != null ? mTime.hashCode() : 0;
        result = 31 * result + (mType != null ? mType.hashCode() : 0);
        result = 31 * result + (mFromYear != null ? mFromYear.hashCode() : 0);
        result = 31 * result + (mToYear != null ? mToYear.hashCode() : 0);
        return result;
    }

	@Override
<<<<<<< HEAD
	public String toString()
	{
=======
	public String toString() {
>>>>>>> a9cc4415ea4d00a3a9eb1c48a102c7509f91dfdb
		return "Evolution{" +
				"mId=" + mId +
				", mTime='" + mTime + '\'' +
				", mType=" + mType +
				", mFromYear='" + mFromYear + '\'' +
				", mToYear='" + mToYear + '\'' +
<<<<<<< HEAD
				", mParams=" + Arrays.toString(mParams) +
=======
>>>>>>> a9cc4415ea4d00a3a9eb1c48a102c7509f91dfdb
				", slices=" + slices +
				'}';
	}
}
