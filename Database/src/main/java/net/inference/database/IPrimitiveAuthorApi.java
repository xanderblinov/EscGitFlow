package net.inference.database;

import net.inference.database.dto.IArticle;
import net.inference.database.dto.IAuthor;
import net.inference.database.dto.IPrimitiveAuthor;
import net.inference.database.dto.IPrimitiveAuthorToAuthor;
import net.inference.sqlite.dto.Article;
import net.inference.sqlite.dto.Author;
import net.inference.sqlite.dto.PrimitiveAuthor;
import net.inference.sqlite.dto.PrimitiveAuthorToAuthor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xanderblinov
 */
public interface IPrimitiveAuthorApi extends IBaseApi<PrimitiveAuthor, Integer>
{

	IPrimitiveAuthor addAuthor(final IPrimitiveAuthor author);

	List<PrimitiveAuthor> findCoauthors(final IPrimitiveAuthor author);

	IPrimitiveAuthorToAuthor addCoauthor(final PrimitiveAuthor author, final PrimitiveAuthor coauthor);

	List<Article> findArticlesForAuthor(final IAuthor author);

	List<Author> findAuthorsForArticle(final IArticle article);

	ArrayList<PrimitiveAuthor> addAuthors(List<PrimitiveAuthor> primitiveAuthors) throws Exception;

	List<PrimitiveAuthorToAuthor> addCoauthors(List<PrimitiveAuthor> primitiveAuthors) throws Exception;

	/**
	 * @return list of coauthors for next unprocessed article or null in case there is no unprocessed article
	 * @throws SQLException
	 */
	List<PrimitiveAuthor> getNextUnassignedAuthors() throws SQLException;


/*
    List<CompanyImpl> findCompaniesForAuthor(final Author author);

	List<AuthorImpl> findAuthorsForCompany(final Company company);*/
}
