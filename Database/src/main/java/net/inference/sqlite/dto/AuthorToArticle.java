package net.inference.sqlite.dto;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import net.inference.database.dto.IArticle;
import net.inference.database.dto.IAuthor;
import net.inference.database.dto.IAuthorToArticle;

/**
 * Date: 4/25/2015
 * Time: 4:20 PM
 *
 * @author xanderblinov
 */
@DatabaseTable(tableName = IAuthorToArticle.TABLE_NAME)
public class AuthorToArticle implements IAuthorToArticle
{


	@DatabaseField(generatedId = true)
	private long id;

	@DatabaseField(foreign = true, columnName = Column.author)
	private Author author;
	@DatabaseField(foreign = true, columnName = Column.article_id)
	private Article article;


	public AuthorToArticle()
	{
	}

	public AuthorToArticle(final IAuthor author, final IArticle mArticle)
	{
		setAuthor(author);
		setArticle(mArticle);
	}


	public IAuthor getAuthor()
	{
		return author;
	}

	public void setAuthor(IAuthor author)
	{
		this.author = (Author) author;
	}

	public Article getArticle()
	{
		return article;
	}

	@Override
	public void setArticle(IArticle article)
	{
		this.article = (Article) article;
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

		IAuthorToArticle that = (IAuthorToArticle) o;

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
		return "AuthorToArticle{" +
				"id=" + id +
				", author=" + author +
				", article_id=" + article +
				'}';
	}
}
