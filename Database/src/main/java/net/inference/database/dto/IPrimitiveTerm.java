package net.inference.database.dto;

import java.util.ArrayList;

import net.inference.sqlite.dto.Article;
import net.inference.sqlite.dto.PrimitiveTermType;
import net.inference.sqlite.dto.Term;


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
		public static final String year = "year";
		public static final String publication = "publication";
		public static final String term = "term";
	}

	ArrayList<String> separatePrimitiveTerms();

	int getId();

	void setId(int id);

	String getValue();

	void setValue(String value);

	PrimitiveTermType getType();

	void setType(PrimitiveTermType type);

	String getTermValue();

	Term getTerm();

	void setTerm(Term inputTerm);

	int getYear();

	void setYear(int year);

	Article getPublication();

	void setPublication(Article publication);

	String toString();

}

