package net.inference.sqlite.dto;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import net.inference.database.dto.IClusterToCluster;

/**
 * Date: 24-Oct-15
 * Time: 3:27 PM
 *
 * @author xanderblinov
 */
@DatabaseTable(tableName = IClusterToCluster.TABLE_NAME)
public class ClusterToCluster implements IClusterToCluster
{
	@DatabaseField(columnName = Column.id, generatedId = true)
	private long mId;

	@DatabaseField(columnName = Column.from, foreign = true)
	private Cluster mFrom;

	@DatabaseField(columnName = Column.to, foreign = true)
	private Cluster mTo;

	public ClusterToCluster(Cluster from, Cluster to)
	{
		mFrom = from;
		mTo = to;
	}

	public ClusterToCluster()
	{
		//TO dao
	}

	@Override
	public Cluster getFrom()
	{
		return mFrom;
	}

	@Override
	public Cluster getTo()
	{
		return mTo;
	}

	@Override
	public long getId()
	{
		return mId;
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

		ClusterToCluster that = (ClusterToCluster) o;

		return mId == that.mId;

	}

	@Override
	public int hashCode()
	{
		return (int) (mId ^ (mId >>> 32));
	}

	@Override
	public String toString()
	{
		return "ClusterToCluster{" +
		"mTo=" + mTo +
		", mFrom=" + mFrom +
		'}';
	}
}
