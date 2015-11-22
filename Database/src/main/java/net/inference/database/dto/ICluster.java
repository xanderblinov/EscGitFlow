package net.inference.database.dto;

/**
 * Date: 12/19/2014
 * Time: 3:15 PM
 *
 * @author xanderblinov
 */

public interface ICluster extends IEntity
{
	public static final String TABLE_NAME = "cluster";

	public static class Column
	{
		public static final String id = "_id";
		public static final String slice_id = "slice_id";
	}

    IEvolutionSlice getEvolutionSlice();

    void setEvolutionSlice(IEvolutionSlice slice);


}
