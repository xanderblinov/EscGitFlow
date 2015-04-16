package net.inference.database;

import net.inference.database.dto.Author;
import net.inference.database.dto.Cluster;
import net.inference.database.dto.CoAuthorship;
import net.inference.sqlite.dto.AuthorImpl;
import net.inference.sqlite.dto.ClusterImpl;

import java.util.List;

/**
 * @author gzheyts
 */
public interface AuthorApi {
    Author addAuthor(final Author author);

    AuthorImpl findById(final Integer id);

    List<AuthorImpl> findAllAuthors();

    List<AuthorImpl> findCoauthors(final Author author);

    List<AuthorImpl> findAuthorsForCluster(final Cluster cluster);

    public boolean addAuthorToCluster(final Author author, final Cluster cluster);

    CoAuthorship addCoauthor(final Author author, final Author coauthor);
}
