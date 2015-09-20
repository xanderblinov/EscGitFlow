package net.inference.sqlite.dto;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import net.inference.database.dto.ICluster;
import net.inference.database.dto.IEvolutionSlice;

/**
 * Date: 2/1/2015
 * Time: 5:53 PM
 *
 * @author xanderblinov
 */
@DatabaseTable(tableName = ICluster.TABLE_NAME)
public class Cluster implements ICluster
{     
	
	@DatabaseField(columnName = Column.id, generatedId = true)
	private long mId;
	@DatabaseField(foreign = true, columnName = Column.slice_id)
	private EvolutionSlice evolutionSlice;

	public Cluster()
	{
	}

	public Cluster(final IEvolutionSlice slice)
	{
		setEvolutionSlice(slice);
	}

    @Override
    public IEvolutionSlice getEvolutionSlice() {
        return evolutionSlice;
    }

    @Override
    public void setEvolutionSlice(IEvolutionSlice slice) {
        evolutionSlice = (EvolutionSlice) slice;
    }

    public long getId()
	{
		return mId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Cluster cluster = (Cluster) o;

		if (mId != cluster.mId) return false;
		return !(evolutionSlice != null ? !evolutionSlice.equals(cluster.evolutionSlice) : cluster.evolutionSlice != null);

	}

	@Override
	public int hashCode() {
		int result = (int) (mId ^ (mId >>> 32));
		result = 31 * result + (evolutionSlice != null ? evolutionSlice.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "ClusterImpl{" +
				"mId=" + mId +
				", evolutionSlice=" + evolutionSlice +
				'}';
	}
}
