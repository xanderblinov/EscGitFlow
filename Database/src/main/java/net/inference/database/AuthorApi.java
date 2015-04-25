package net.inference.database;

import java.util.List;

import net.inference.database.dto.Article;
import net.inference.database.dto.Author;
import net.inference.database.dto.Cluster;
import net.inference.database.dto.CoAuthorship;
import net.inference.database.dto.Company;
import net.inference.sqlite.dto.ArticleImpl;
import net.inference.sqlite.dto.AuthorImpl;
import net.inference.sqlite.dto.CompanyImpl;

/**
 * @author gzheyts
 */
public interface AuthorApi extends BaseApi<AuthorImpl,Integer>{

    Author addAuthor(final Author author);
    List<AuthorImpl> findCoauthors(final Author author);

    List<AuthorImpl> findAuthorsForCluster(final Cluster cluster);

    public boolean addAuthorToCluster(final Author author, final Cluster cluster);

    CoAuthorship addCoauthor(final Author author, final Author coauthor);

	List<ArticleImpl> findArticlesForAuthor(final Author author);

	List<AuthorImpl> findAuthorsForArticle(final Article article);

	List<CompanyImpl> findCompaniesForAuthor(final Author author);

	List<AuthorImpl> findAuthorsForCompany(final Company company);
}
