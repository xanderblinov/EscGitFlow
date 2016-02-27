package net.inference.database;

import java.util.List;

import net.inference.sqlite.dto.TermToTerm;

/**
 * Created by Мария on 15.12.15.
 */
public interface ITermToTermApi extends IBaseApi <TermToTerm,Integer>
{
	// TODO exist
	List<TermToTerm> addTerms(List<TermToTerm> terms) throws Exception;

	default TermToTerm addTerm(TermToTerm primitiveTerm)
	{
		return null;
	}
}