package net.inference.database;

import net.inference.database.dto.Article;
import net.inference.database.dto.Author;
import net.inference.database.dto.PrimitiveAuthor;
import net.inference.database.dto.PrimitiveCoAuthorship;
import net.inference.sqlite.dto.ArticleImpl;
import net.inference.sqlite.dto.AuthorImpl;
import net.inference.sqlite.dto.PrimitiveAuthorImpl;

import java.util.List;

/**
 * @author xanderblinov
 */
public interface PrimitiveAuthorApi extends BaseApi<PrimitiveAuthorImpl,Integer>{

    PrimitiveAuthor addAuthor(final PrimitiveAuthor author);
    List<PrimitiveAuthorImpl> findCoauthors(final PrimitiveAuthor author);

    PrimitiveCoAuthorship addCoauthor(final PrimitiveAuthor author, final PrimitiveAuthor coauthor);

	List<ArticleImpl> findArticlesForAuthor(final Author author);

	List<AuthorImpl> findAuthorsForArticle(final Article article);
/*
    List<CompanyImpl> findCompaniesForAuthor(final Author author);

	List<AuthorImpl> findAuthorsForCompany(final Company company);*/
}
