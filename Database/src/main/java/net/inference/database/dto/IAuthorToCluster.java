package net.inference.database.dto;

/**
 * Date: 2/15/2015
 * Time: 6:45 PM
 *
 * @author xanderblinov
 */
public interface IAuthorToCluster extends IEntity
{
	public static final String TABLE_NAME = "author_to_cluster";

	public static class Column
	{
		public static final String cluster_id = "cluster_id";
		public static final String author_id = "author_id";
	}


    IAuthor getAuthor();

    void setAuthor(IAuthor author);

    ICluster getCluster();

    void setCluster(ICluster cluster);
}
