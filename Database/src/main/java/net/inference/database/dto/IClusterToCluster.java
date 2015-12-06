package net.inference.database.dto;

import net.inference.sqlite.dto.Cluster;

/**
 * Date: 24-Oct-15
 * Time: 3:27 PM
 *
 * @author xanderblinov
 */
public interface IClusterToCluster
{

	Cluster getFrom();

	Cluster getTo();
	String TABLE_NAME = "clister_to_cluster";

	class Column
	{
		public static final String from = "from";
		public static final String to = "to";

		public static final String id = "id";
	}
	long getId();

}
