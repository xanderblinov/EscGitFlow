package net.inference.database;

import net.inference.database.dto.Article;
import net.inference.database.dto.Author;
import net.inference.database.dto.PrimitiveAuthor;
import net.inference.database.dto.PrimitiveCoAuthorship;
import net.inference.sqlite.dto.ArticleImpl;
import net.inference.sqlite.dto.AuthorImpl;
import net.inference.sqlite.dto.PrimitiveAuthorImpl;
import net.inference.sqlite.dto.PrimitiveCoAuthorshipImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xanderblinov
 */
public interface PrimitiveAuthorApi extends BaseApi<PrimitiveAuthorImpl,Integer>{

    PrimitiveAuthor addAuthor(final PrimitiveAuthor author);
    List<PrimitiveAuthorImpl> findCoauthors(final PrimitiveAuthor author);

    PrimitiveCoAuthorship addCoauthor(final PrimitiveAuthorImpl author, final PrimitiveAuthorImpl coauthor);

	List<ArticleImpl> findArticlesForAuthor(final Author author);

	List<AuthorImpl> findAuthorsForArticle(final Article article);

	ArrayList<PrimitiveAuthorImpl> addAuthors(List<PrimitiveAuthorImpl> primitiveAuthors) throws Exception;

	List<PrimitiveCoAuthorshipImpl> addCoauthors(List<PrimitiveAuthorImpl> primitiveAuthors) throws Exception;
/*
    List<CompanyImpl> findCompaniesForAuthor(final Author author);

	List<AuthorImpl> findAuthorsForCompany(final Company company);*/
}
