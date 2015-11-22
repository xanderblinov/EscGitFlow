package net.inference.database;

import net.inference.database.dto.IAuthor;
import net.inference.sqlite.dto.Cluster;

import java.util.List;

/**
 * @author gzheyts
 */
public interface IClusterApi extends IBaseApi<Cluster,Integer>
{

    List<Cluster> findClustersForAuthor(IAuthor author);

}
