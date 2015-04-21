package net.inference.database;

import net.inference.database.dto.Author;
import net.inference.sqlite.dto.ClusterImpl;

import java.util.List;

/**
 * @author gzheyts
 */
public interface ClusterApi extends BaseApi<ClusterImpl,Integer> {

    List<ClusterImpl> findClustersForAuthor(Author author);

}
