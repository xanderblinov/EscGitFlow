package net.inference.database;

import java.sql.SQLException;
import java.util.List;

import net.inference.database.dto.ITerm;
import net.inference.sqlite.dto.PrimitiveTerm;
import net.inference.sqlite.dto.Term;

/**
 * Created by palen on 23.10.2015.
 */
public interface ITermApi extends IBaseApi <Term,Integer>
{
	List<Term> addTerms(List<Term> terms) throws Exception;

	default Term addTerm(Term term)
	{
		return null;
	}

	boolean exists(Term term) throws SQLException;

}
