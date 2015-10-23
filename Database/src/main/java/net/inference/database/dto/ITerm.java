package net.inference.database.dto;

import java.util.ArrayList;
import java.util.List;

import net.inference.sqlite.dto.Term;

/**
 * Date: 20-Sep-15
 * Time: 4:40 PM
 *
 * @author xanderblinov
 */
public interface ITerm
{
	String TABLE_NAME = "term";

	class Column
	{
		public static final String value = "value";
		public static final String publication = "publication";
		public static final String neighbors = "with_terms";

	}

	String getValue();

	void setValue(final String value);

	String getPublication();

	void setPublication(final String publication);

	String getNeighbors();

	void addNeighbors(final String [] neighbors);

	boolean existNeighbor(final String neighbor);

	ArrayList<String> setEncoding();

}
