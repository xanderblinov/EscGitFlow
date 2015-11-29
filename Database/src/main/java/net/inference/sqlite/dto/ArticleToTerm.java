package net.inference.sqlite.dto;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import net.inference.database.dto.IArticleToTerm;

/**
 * Date: 20-Sep-15
 * Time: 4:51 PM
 *
 * @author xanderblinov
 */
@DatabaseTable(tableName = ArticleToTerm.TABLE_NAME)
public class ArticleToTerm implements IArticleToTerm
{
	@DatabaseField(columnName = Column.id)
	private long mId;
	@DatabaseField(columnName = Column.article, foreign = true)
	private Article mArticle;
	@DatabaseField(columnName = Column.term, foreign = true)
	private PrimitiveTerm mTerm;

	@SuppressWarnings("unused")
	public ArticleToTerm()
	{
		//TO dao
	}

	public ArticleToTerm(Article article, PrimitiveTerm term)
	{
		mArticle = article;
		mTerm = term;
	}

	@Override
	public long getId()
	{
		return mId;
	}

	public Article getArticle()
	{
		return mArticle;
	}

	public PrimitiveTerm getTerm()
	{
		return mTerm;
	}
}
