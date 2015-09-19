package net.inference.database.dto;

import net.inference.sqlite.dto.ArticleImpl;

/**
 * Date: 12/19/2014
 * Time: 3:15 PM
 *
 * @author xanderblinov
 */

public interface PrimitiveAuthor extends Entity
{
	String TABLE_NAME = "primitive_author";

	public class Column
	{
		public static final String id = "_id";
		public static final String surname = "surname";
		public static final String name = "name";
		public static final String encoding = "encoding";
		public static final String source = "source";
		public static final String article = "article";
		public static final String inference_id = "inference_id";
	}

	String getName();

	void setName(final String name);

	String getSurname();

	void setSurname(final String surname);

	ArticleImpl getArticle();

	void setArticle(final ArticleImpl articleId);

	String getSource();

	void setSource(final String source);

	String getEncoding();

	void setEncoding(final String encoding);

	long getInferenceId();

	void setInferenceId(final long inferenceId);
}
