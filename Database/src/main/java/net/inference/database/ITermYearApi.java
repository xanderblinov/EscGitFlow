package net.inference.database;

import java.sql.SQLException;
import java.util.List;

import net.inference.sqlite.dto.TermYear;

/**
 * Created by Мария on 16.04.2016.
 */
public interface ITermYearApi  extends IBaseApi <TermYear,Integer>
{
	List<TermYear> addTerms(List<TermYear> terms) throws Exception;

	default TermYear addTerm(TermYear term)
	{
		return null;
	}

	boolean exists(TermYear term) throws SQLException;
}
