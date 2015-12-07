package net.inference.database.dto;

<<<<<<< HEAD
import java.util.List;
=======
import java.util.ArrayList;
import net.inference.sqlite.dto.Article;
>>>>>>> a9cc4415ea4d00a3a9eb1c48a102c7509f91dfdb


/**
 * Date: 20-Sep-15
 * Time: 4:40 PM
 *
 * @author xanderblinov
 */
public interface IPrimitiveTerm
{
	String TABLE_NAME = "primitive_term";

	class Column
	{
		public static final String value = "value";
		public static final String id = "id";
		public static final String type = "type";
		public static final String date = "date";
		public static final String publication = "publication";
		public static final String neighbors = "with_terms";
		public static final String term = "term";
	}

	String getPublication();

	void setPublication(final String publication);

<<<<<<< HEAD
	List<String> separatePrimitiveTerms(String keyWords);
=======
	ArrayList<String> separatePrimitiveTerms();
>>>>>>> a9cc4415ea4d00a3a9eb1c48a102c7509f91dfdb

}

