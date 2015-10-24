package net.inference.database.dto;

/**
 * Date: 12/19/2014
 * Time: 3:15 PM
 *
 * @author xanderblinov
 */

public interface IEvolution extends IEntity
{
	public static final String TABLE_NAME = "evolution";

	public static class Column
	{
		public static final String id = "_id";
		public static final String time = "time";
		public static final String type_id = "type_id";
		public static final String from_year = "from_year";
		public static final String to_year = "to_year";
		public static final String params = "params";
	}

	public long getId();

	public String getTime();

	public void setTime(final String time);

	public IClusteringType getType();

	public void setType(final IClusteringType type);

	public String getFromYear();

	public void setFromYear(final String fromYear);

	public String getToYear();

	public void setToYear(final String toYear);

	public double[] getParams();

	public void setParams(double[] params);
}
