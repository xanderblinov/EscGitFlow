package net.inference.database;

import net.inference.sqlite.dto.Term;

import java.util.List;

/**
 * Created by palen on 23.10.2015.
 */
public interface ITermApi extends IBaseApi<Term, Integer>
{
	List<Term> addTerms(List<Term> terms) throws Exception;

	default Term addTerm(Term term)
	{
		return null;
	}
}
