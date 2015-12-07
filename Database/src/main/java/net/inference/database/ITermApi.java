package net.inference.database;

import java.util.List;

import net.inference.database.dto.ITerm;
<<<<<<< HEAD
=======
import net.inference.sqlite.dto.PrimitiveTerm;
>>>>>>> a9cc4415ea4d00a3a9eb1c48a102c7509f91dfdb
import net.inference.sqlite.dto.Term;

/**
 * Created by palen on 23.10.2015.
 */
public interface ITermApi extends IBaseApi <Term,Integer>
{
<<<<<<< HEAD

=======
	List<Term> addTerms(List<Term> terms) throws Exception;

	default Term addTerm(Term term)
	{
		return null;
	}
>>>>>>> a9cc4415ea4d00a3a9eb1c48a102c7509f91dfdb
}
