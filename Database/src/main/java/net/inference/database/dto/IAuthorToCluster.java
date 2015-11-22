package net.inference.database.dto;

/**
 * Date: 2/15/2015
 * Time: 6:45 PM
 *
 * @author xanderblinov
 */
public interface IAuthorToCluster extends IEntity
{
	String TABLE_NAME = "author_to_cluster";

	class Column
	{
		public static final String cluster = "cluster";
		public static final String author = "author";
	}


    IAuthor getAuthor();

    void setAuthor(IAuthor author);

    ICluster getCluster();

    void setCluster(ICluster cluster);
}
