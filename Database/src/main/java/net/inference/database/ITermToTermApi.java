package net.inference.database;

import net.inference.sqlite.dto.TermToTerm;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by M.Pankova on 15.12.15.
 */
public interface ITermToTermApi extends IBaseApi <TermToTerm,Integer>
{
	// TODO exist
	List<TermToTerm> addTerms(List<TermToTerm> terms) throws Exception;

	default TermToTerm addTerm(TermToTerm primitiveTerm)
	{
		return null;
	}

	boolean exists(TermToTerm elem) throws SQLException;

}