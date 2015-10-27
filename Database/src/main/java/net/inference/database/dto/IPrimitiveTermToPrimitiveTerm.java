package net.inference.database.dto;

import java.util.ArrayList;

import net.inference.sqlite.dto.PrimitiveTerm;

/**
 * Created by Мария on 24.10.15.
 */
public interface IPrimitiveTermToPrimitiveTerm
{
	String TABLE_NAME = "primitive_term_to_primitive_term";

	class Column
	{
		public static final String id = "id";
		public static final String from = "from";
		public static final String to = "to";
		public static final String count = "count";
	}

	PrimitiveTerm getFrom();

	void setFrom(final PrimitiveTerm from);

	PrimitiveTerm getTo();

	void setTo(final PrimitiveTerm to);

	int getCount();

	void incCount(final int count);

	long getId();

	String toString();

}
