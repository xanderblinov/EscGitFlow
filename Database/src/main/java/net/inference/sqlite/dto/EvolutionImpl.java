package net.inference.sqlite.dto;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import net.inference.database.dto.Evolution;
import net.inference.database.dto.ClusteringType;
import net.inference.database.dto.EvolutionSlice;

/**
 * Date: 2/1/2015
 * Time: 5:53 PM
 *
 * @author xanderblinov
 */
@DatabaseTable(tableName = Evolution.TABLE_NAME)
public class EvolutionImpl implements Evolution
{     
	
	@DatabaseField(columnName = Column.id, generatedId = true)
	private int mId;
	@DatabaseField(columnName = Column.time)
	private String mTime;
	@DatabaseField(columnName = Column.type_id, dataType = DataType.ENUM_STRING)
	private ClusteringType mType;
	@DatabaseField(columnName = Column.from_year)
	private String mFromYear;
	@DatabaseField(columnName = Column.to_year)
	private String mToYear;


    @ForeignCollectionField
    private ForeignCollection<EvolutionSliceImpl> slices;


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

	public ClusteringType getType()
	{
		return mType;
	}

	public void setType(final ClusteringType type)
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

    public ForeignCollection<EvolutionSliceImpl> getSlices() {
        return slices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EvolutionImpl evolution = (EvolutionImpl) o;

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
	public String toString() {
		return "EvolutionImpl{" +
				"mId=" + mId +
				", mTime='" + mTime + '\'' +
				", mType=" + mType +
				", mFromYear='" + mFromYear + '\'' +
				", mToYear='" + mToYear + '\'' +
				", slices=" + slices +
				'}';
	}
}
