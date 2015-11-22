package net.inference.database;

import net.inference.database.dto.IArticle;
import net.inference.database.dto.IAuthor;
import net.inference.database.dto.ICluster;
import net.inference.database.dto.IAuthorToAuthor;
import net.inference.database.dto.ICompany;
import net.inference.sqlite.dto.Article;
import net.inference.sqlite.dto.Author;
import net.inference.sqlite.dto.AuthorToAuthor;
import net.inference.sqlite.dto.Cluster;
import net.inference.sqlite.dto.Company;

import java.sql.SQLException;
import java.util.List;

/**
 * @author gzheyts
 */
public interface IAuthorApi extends IBaseApi<Author,Integer>
{

    IAuthor addAuthor(final IAuthor author);
    List<Author> findCoauthors(final IAuthor author);

    List<Author> findAuthorsForCluster(final ICluster cluster);

    List<Author> addAuthors(List<Author> authors) throws Exception;

    public boolean addAuthorToCluster(final Author author, final Cluster cluster);

    IAuthorToAuthor addCoauthor(final Author author, final Author coauthor);

	List<Article> findArticlesForAuthor(final IAuthor author);

	List<Author> findAuthorsForArticle(final IArticle article);

	List<Company> findCompaniesForAuthor(final IAuthor author);

	List<Author> findAuthorsForCompany(final ICompany company);

    List<AuthorToAuthor> addAuthorToAuthors(List<AuthorToAuthor> authorToAuthors) throws Exception;
}
