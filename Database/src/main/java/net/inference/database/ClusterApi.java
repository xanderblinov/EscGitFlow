package net.inference.database;

import net.inference.database.dto.Author;
import net.inference.sqlite.dto.ClusterImpl;

import java.util.List;

/**
 * @author gzheyts
 */
public interface ClusterApi {

    List<ClusterImpl> findAllClusters();

    List<ClusterImpl> findClustersForAuthor(Author author);

}
