package net.inference.sqlite.dto;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import net.inference.database.dto.Article;
import net.inference.database.dto.Author;
import net.inference.database.dto.AuthorToArticle;

/**
 * Date: 4/25/2015
 * Time: 4:20 PM
 *
 * @author xanderblinov
 */
@DatabaseTable(tableName = AuthorToArticle.TABLE_NAME)
public class AuthorToArticleImpl implements AuthorToArticle
{


	@DatabaseField(generatedId = true)
	private long id;

	@DatabaseField(foreign = true, columnName = Column.author_id)
	private AuthorImpl author;
	@DatabaseField(foreign = true, columnName = Column.article_id)
	private ArticleImpl article;


	public AuthorToArticleImpl()
	{
	}

	public AuthorToArticleImpl(final Author author, final Article mArticle)
	{
		setAuthor(author);
		setArticle(mArticle);
	}


	public Author getAuthor()
	{
		return author;
	}

	public void setAuthor(Author author)
	{
		this.author = (AuthorImpl) author;
	}

	public ArticleImpl getArticle()
	{
		return article;
	}

	@Override
	public void setArticle(Article article)
	{
		this.article = (ArticleImpl) article;
	}

	@Override
	public long getId()
	{
		return id;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null || getClass() != o.getClass())
		{
			return false;
		}

		AuthorToArticle that = (AuthorToArticle) o;

		if (id != that.getId())
		{
			return false;
		}
		if (author != null ? !author.equals(that.getAuthor()) : that.getAuthor() != null)
		{
			return false;
		}
		return !(article != null ? !article.equals(that.getArticle()) : that.getArticle() != null);

	}

	@Override
	public int hashCode()
	{
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (author != null ? author.hashCode() : 0);
		result = 31 * result + (article != null ? article.hashCode() : 0);
		return result;
	}

	@Override
	public String toString()
	{
		return "AuthorToArticleImpl{" +
				"id=" + id +
				", author=" + author +
				", article_id=" + article +
				'}';
	}
}
