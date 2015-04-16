package net.inference.database.dto;

/**
 * Date: 12/19/2014
 * Time: 3:15 PM
 *
 * @author xanderblinov
 */

public interface PrimitiveAuthor extends Entity
{
	public static final String TABLE_NAME = "primitive_author";

	public static class Column
	{
		public static final String id = "_id";
		public static final String surname = "surname";
		public static final String name = "name";
		public static final String encoding = "encoding";
		public static final String source = "source";
		public static final String article_id = "article_id";
		public static final String inference_id = "inference_id";
	}

	public String getName();

	public void setName(final String name);

	public String getSurname();

	public void setSurname(final String surname);

	public int getArticleId();

	public void setArticleId(final int articleId);

	public String getSource();

	public void setSource(final String source);

	public String getEncoding();

	public void setEncoding(final String encoding);

	public long getInferenceId();

	public void setInferenceId(final long inferenceId);
}
