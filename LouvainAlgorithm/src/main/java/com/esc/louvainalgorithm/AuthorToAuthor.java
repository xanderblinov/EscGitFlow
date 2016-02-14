package com.esc.louvainalgorithm;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @deprecated should use {@link net.inference.sqlite.dto.AuthorToAuthor}  with {@link net.inference.sqlite.AuthorApi}
 *
 */
@Deprecated
@DatabaseTable(tableName = "primitive_author_to_author")
public class AuthorToAuthor
{

	@DatabaseField(columnName= "_id", generatedId = true)
	private int id;
	@DatabaseField
	private int author;
	@DatabaseField
	private int coauthor;
	@DatabaseField
	private int year;
	@DatabaseField
	private int article_id;

	public AuthorToAuthor()
	{

	}

	public void setId(int id)
	{

		this.id = id;

	}

	public int getId()
	{

		return id;

	}

	public void setAuthor(int author)
	{

		this.author = author;

	}

	public int getAuthor()
	{

		return author;

	}

	public void setCoauthor(int coauthor)
	{

		this.coauthor = coauthor;

	}

	public int getCoauthor()
	{

		return coauthor;

	}

	public void setYear(int year)
	{

		this.year = year;

	}

	public int getYear()
	{

		return year;

	}

	public void setArticle_id(int article_id)
	{

		this.article_id = article_id;

	}

	public int getArticle_id()
	{

		return article_id;

	}
}
