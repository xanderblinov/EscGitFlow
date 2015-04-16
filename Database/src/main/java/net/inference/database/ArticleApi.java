package net.inference.database;

import java.util.List;

import net.inference.database.dto.Article;
import net.inference.sqlite.dto.ArticleImpl;

/**
 * Date: 12/23/2014
 * Time: 11:23 PM
 *
 * @author xanderblinov
 */
public interface ArticleApi
{
	public void addArticle(Article article);

	public List<ArticleImpl> getAllArticles();
}
