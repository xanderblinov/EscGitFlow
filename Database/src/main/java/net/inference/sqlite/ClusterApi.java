package net.inference.sqlite;

import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import net.inference.database.IClusterApi;
import net.inference.database.dto.IAuthor;
import net.inference.database.dto.IAuthorToCluster;
import net.inference.sqlite.dto.AuthorToCluster;
import net.inference.sqlite.dto.Cluster;

import java.sql.SQLException;
import java.util.List;

/**
 * @author gzheyts
 */
public class ClusterApi extends BaseApi<Cluster,Integer> implements IClusterApi
{
    private static Logger logger = LoggerFactory.getLogger(ClusterApi.class);

    private PreparedQuery<Cluster> clustersForAuthorQuery;

    private DatabaseApi mDatabaseApi;
    public ClusterApi(DatabaseApi databaseApi) {
        super(databaseApi, Cluster.class);
        this.mDatabaseApi = databaseApi;

    }

    public List<Cluster> findClustersForAuthor(IAuthor author) {
        try {
            if (clustersForAuthorQuery == null) {
                clustersForAuthorQuery = buildClustersForAuthorQuery();
            }

            clustersForAuthorQuery.setArgumentHolderValue(0, author);

            return getDao().query(clustersForAuthorQuery);

        } catch (SQLException e) {
            logger.error(e, "");
        }

        return null;
    }

    private PreparedQuery<Cluster> buildClustersForAuthorQuery() throws SQLException {
        QueryBuilder<AuthorToCluster,Integer> authorClusterQb;
        QueryBuilder<Cluster, Integer> clusterQb;

        authorClusterQb = mDatabaseApi.<Integer>getAuthorToClusterDao().queryBuilder();
        authorClusterQb.selectColumns(IAuthorToCluster.Column.cluster);
        SelectArg authorSelectArg = new SelectArg();
        authorClusterQb.where().eq(IAuthorToCluster.Column.author, authorSelectArg);


        clusterQb = mDatabaseApi.<Integer>getClusterDao().queryBuilder();
        clusterQb.where().in(IAuthor.Column.id, authorClusterQb);
        return clusterQb.prepare();
    }

}
