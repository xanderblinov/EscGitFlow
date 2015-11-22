package net.inference.database;

import java.util.List;

import net.inference.database.dto.IPrimitiveTermToPrimitiveTerm;
import net.inference.sqlite.dto.PrimitiveTermToPrimitiveTerm;
import net.inference.sqlite.dto.PrimitiveTermToPrimitiveTerm;


/**
 * Created by palen on 23.10.2015.
 */
public interface IPrimitiveTermToPrimitiveTermApi extends IBaseApi <PrimitiveTermToPrimitiveTerm,Integer>
{
	// TODO exist
	List<PrimitiveTermToPrimitiveTerm> addTerms(List<PrimitiveTermToPrimitiveTerm> terms) throws Exception;

	default PrimitiveTermToPrimitiveTerm addTerm(PrimitiveTermToPrimitiveTerm primitiveTerm)
	{
		return null;
	}
}
