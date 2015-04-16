package net.inference.database.dto;

/**
 * Date: 2/15/2015
 * Time: 6:45 PM
 *
 * @author xanderblinov
 */
public interface AuthorToCluster extends Entity
{
	public static final String TABLE_NAME = "author_to_cluster";

	public static class Column
	{
		public static final String cluster_id = "cluster_id";
		public static final String author_id = "author_id";
	}


    Author getAuthor();

    void setAuthor(Author author);

    Cluster getCluster();

    void setCluster(Cluster cluster);
}
