package net.inference.sqlite;

import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import net.inference.database.AuthorApi;
import net.inference.database.dto.Author;
import net.inference.database.dto.AuthorToCluster;
import net.inference.database.dto.Cluster;
import net.inference.database.dto.CoAuthorship;
import net.inference.sqlite.dto.AuthorImpl;
import net.inference.sqlite.dto.AuthorToClusterImpl;
import net.inference.sqlite.dto.CoAuthorshipImpl;

import java.sql.SQLException;
import java.util.List;

/**
 * @author gzheyts
 */
public class AuthorApiImpl extends BaseApiImpl<AuthorImpl,Integer> implements AuthorApi {
    private static Logger logger = LoggerFactory.getLogger(AuthorApiImpl.class);
    private SqliteApi sqliteApi;
    private PreparedQuery<AuthorImpl> authorForClusterQuery;
    private PreparedQuery<AuthorImpl> coauthorForAuthorQuery;

    public AuthorApiImpl(SqliteApi sqliteApi) {
        super(sqliteApi, AuthorImpl.class);
        this.sqliteApi = sqliteApi;
    }


    @Override
    public Author addAuthor(final Author author) {
        try {
            return getDao().createIfNotExists((AuthorImpl) author);
        } catch (SQLException e) {
            logger.error(e, "");
        }
        return null;
    }


    @Override
    public boolean addAuthorToCluster(final Author author, final Cluster cluster) {

        AuthorToClusterImpl authorToCluster = new AuthorToClusterImpl(author, cluster);
        try {
            return sqliteApi.getAuthorToClusterDao().create(authorToCluster) == 1;
        } catch (SQLException e) {
            SqliteLog.log(e);
        }
        return false;
    }

    @Override
    public  CoAuthorship addCoauthor(final Author author, final Author coauthor) {
        CoAuthorshipImpl coAuthorship = new CoAuthorshipImpl(author, coauthor);
        try {
            return sqliteApi.getInferenceCoAuthorshipDao().createIfNotExists(coAuthorship);
        } catch (SQLException e) {
            logger.error(e, "");
        }
        return null;
    }

    @Override
    public List<AuthorImpl> findCoauthors(final Author author) {
        try {
            if (coauthorForAuthorQuery == null) {
                coauthorForAuthorQuery = buildCoauthorForAuthorQuery();
            }
            coauthorForAuthorQuery.setArgumentHolderValue(0, author);
            return sqliteApi.getInferenceAuthorDao().query(coauthorForAuthorQuery);

        } catch (SQLException ex) {
            logger.error(ex, "");
        }

        return null;
    }

    @Override
    public List<AuthorImpl> findAuthorsForCluster(final Cluster cluster) {

        try {
            if (authorForClusterQuery == null) {
                authorForClusterQuery = buildAuthorForClusterQuery();
            }

            authorForClusterQuery.setArgumentHolderValue(0, cluster);

            return sqliteApi.getInferenceAuthorDao().query(authorForClusterQuery);

        } catch (Exception e) {
            logger.error(e, "");
        }

        return null;
    }

    private PreparedQuery<AuthorImpl> buildAuthorForClusterQuery() throws SQLException{

        QueryBuilder<AuthorToClusterImpl,Integer>  authorClusterQb;
        QueryBuilder<AuthorImpl, Integer> authorQb;

        authorClusterQb = sqliteApi.<Integer>getAuthorToClusterDao().queryBuilder();
        authorClusterQb.selectColumns(AuthorToCluster.Column.author_id);
        SelectArg clusterSelectArg = new SelectArg();
        authorClusterQb.where().eq(AuthorToCluster.Column.cluster_id, clusterSelectArg);


        authorQb = sqliteApi.<Integer>getInferenceAuthorDao().queryBuilder();
        authorQb.where().in(Author.Column.id, authorClusterQb);
        return authorQb.prepare();

    }

    private PreparedQuery<AuthorImpl> buildCoauthorForAuthorQuery() throws SQLException {
        QueryBuilder<CoAuthorshipImpl, Integer> coauthorsQb;
        QueryBuilder<AuthorImpl, Integer> authorQb;
        coauthorsQb = sqliteApi.<Integer>getInferenceCoAuthorshipDao().queryBuilder();
        coauthorsQb.selectColumns(CoAuthorship.Column.coauthor);
        SelectArg authorSelectArg = new SelectArg();
        coauthorsQb.where().eq(CoAuthorship.Column.author, authorSelectArg);


        authorQb = sqliteApi.<Integer>getInferenceAuthorDao().queryBuilder();
        authorQb.where().in(Author.Column.id, coauthorsQb);
        return authorQb.prepare();
    }

}
