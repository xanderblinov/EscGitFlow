package net.inference.database;

import net.inference.database.dto.ITerm;
import net.inference.sqlite.dto.Article;
import net.inference.sqlite.dto.Term;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Мария on 15.10.15.
 */
public interface ITermApi  extends IBaseApi<Term,Integer>
{
	ITerm addTerm(final ITerm term);

	public boolean exists(Term term) throws SQLException;

	ArrayList<Term> addTerms(List<Term> terms) throws Exception;


}
