package net.inference.database.dto;

import net.inference.sqlite.dto.Term;

/**
 * Created by Мария on 16.04.2016.
 */
public interface ITermYear
{
	String TABLE_NAME = "term_year";

	class Column
	{
		public static final String id = "id";
		public static final String term = "term";
		public static final String year = "year";
		public static final String count = "count";
	}

	int getId();

	void setTerm(final Term term);

	Term getTerm();

	void setYear(final int year);

	int getYear();

	void incCounter();

	int getCount();

	void addCount(int add);
}
