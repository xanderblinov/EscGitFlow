package net.inference.database.dto;

import net.inference.sqlite.dto.Article;
import net.inference.sqlite.dto.PrimitiveTerm;

/**
 * Created by M.Pankova on 24.10.15.
 */
public interface IPrimitiveTermToPrimitiveTerm
{
	String TABLE_NAME = "primitive_term_to_primitive_term";

	class Column
	{
		public static final String id = "id";
		public static final String from = "from";
		public static final String to = "to";
		public static final String article_id = "article_id";
	}

	PrimitiveTerm getFrom();

	void setFrom(final PrimitiveTerm from);

	PrimitiveTerm getTo();

	void setTo(final PrimitiveTerm to);

	Article getArticle();

	void setArticle(final Article article);

	long getId();

	String toString();

}
