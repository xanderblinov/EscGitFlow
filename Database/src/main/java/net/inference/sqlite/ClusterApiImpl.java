package net.inference.sqlite;

import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import net.inference.database.ClusterApi;
import net.inference.database.dto.Author;
import net.inference.database.dto.AuthorToCluster;
import net.inference.sqlite.dto.AuthorToClusterImpl;
import net.inference.sqlite.dto.ClusterImpl;

import java.sql.SQLException;
import java.util.List;

/**
 * @author gzheyts
 */
public class ClusterApiImpl extends  BaseApiImpl <ClusterImpl,Integer>  implements ClusterApi {
    private static Logger logger = LoggerFactory.getLogger(ClusterApiImpl.class);

    private PreparedQuery<ClusterImpl> clustersForAuthorQuery;

    private SqliteApi sqliteApi;
    public ClusterApiImpl(SqliteApi sqliteApi) {
        super(sqliteApi, ClusterImpl.class);
        this.sqliteApi = sqliteApi;

    }

    public List<ClusterImpl> findClustersForAuthor(Author author) {
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

    private PreparedQuery<ClusterImpl> buildClustersForAuthorQuery() throws SQLException {
        QueryBuilder<AuthorToClusterImpl,Integer> authorClusterQb;
        QueryBuilder<ClusterImpl, Integer> clusterQb;

        authorClusterQb = sqliteApi.<Integer>getAuthorToClusterDao().queryBuilder();
        authorClusterQb.selectColumns(AuthorToCluster.Column.cluster_id);
        SelectArg authorSelectArg = new SelectArg();
        authorClusterQb.where().eq(AuthorToCluster.Column.author_id, authorSelectArg);


        clusterQb = sqliteApi.<Integer>getClusterDao().queryBuilder();
        clusterQb.where().in(Author.Column.id, authorClusterQb);
        return clusterQb.prepare();
    }

}
