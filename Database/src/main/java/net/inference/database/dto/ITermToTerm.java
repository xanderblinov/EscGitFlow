package net.inference.database.dto;
import net.inference.sqlite.dto.Term;

/**
 * Created by M.Pankova on 15.12.15.
 */
public interface ITermToTerm
{
	String TABLE_NAME = "term_to_term";

	class Column
	{
		public static final String id = "id";
		public static final String from = "from";
		public static final String to = "to";
		public static final String count = "count";
	}

	Term getFrom();

	void setFrom(final Term from);

	Term getTo();

	void setTo(final Term to);

	long getId();

	int getCount();

	void incCount();

	void decCount();

	String toString();

}
