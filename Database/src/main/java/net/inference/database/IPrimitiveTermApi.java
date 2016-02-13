package net.inference.database;

import java.util.List;


import net.inference.sqlite.dto.PrimitiveTerm;

/**
 * Created by palen on 23.10.2015.
 */
public interface IPrimitiveTermApi extends IBaseApi <PrimitiveTerm,Integer>
{
	// TODO exist
	List<PrimitiveTerm> addTerms(List<PrimitiveTerm> terms) throws Exception;

	default PrimitiveTerm addTerm(PrimitiveTerm primitiveTerm)
	{
		return null;
	}
}
