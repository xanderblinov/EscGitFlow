package com.esc.datacollector;

import java.util.ArrayList;
import java.util.List;

import com.esc.datacollector.app.Application;

import net.inference.Config;
import net.inference.database.IDatabaseApi;
import net.inference.sqlite.DatabaseApi;
import net.inference.sqlite.dto.Article;
import net.inference.sqlite.dto.PrimitiveTerm;
import net.inference.sqlite.dto.PrimitiveTermToPrimitiveTerm;
import net.inference.sqlite.dto.Term;
import net.inference.sqlite.dto.TermToTerm;

/**
 * Created by Мария on 12.04.2016.
 */

//здусь мы заносим в базу связи PrimTermToTerm, TermToTerm - так бысрее, чем из PubmedCardProcessor
public class TermCommunication
{
	public static void main(String [] args)
	{


		DatabaseApi api = new DatabaseApi(Config.Database.TEST, false);


		api.onStart();


		int k;


		//граница частоты употребления терминов, вычисляется эмпирически, глядя на базу отфильтрованных по частоте терминов
		double range = 28.0;

		List<PrimitiveTermToPrimitiveTerm> newPrToPr = new ArrayList<>();
		List<Article> articleList = api.article().findAll();
		List<PrimitiveTerm> primitiveTerms = api.primterm().findAll();
		List<Term> terms = api.term().findAll();
		int were = primitiveTerms.size();

		for (int i = 0; i < primitiveTerms.size(); i++)
		{
			if (terms.get(primitiveTerms.get(i).getTerm().getId() - 1).getCount() <= range)
			{
				primitiveTerms.remove(i);
				i--;
			}
		}
		System.out.println("Now us not " + were + "but " + primitiveTerms.size());
		for (int i = 0; i < primitiveTerms.size(); i++)
		{
			PrimitiveTerm A = primitiveTerms.get(i);
			for (int j = 0; j < primitiveTerms.size(); j++)
			{
				PrimitiveTerm B = primitiveTerms.get(j);
				if (A.getPublication().getId() == B.getPublication().getId() && j != i)
				{
					int num = (int) A.getPublication().getId();
					if (terms.get(A.getTerm().getId() - 1).getCount() > range && terms.get(B.getTerm().getId() - 1).getCount() > range)
					{
						newPrToPr.add(new PrimitiveTermToPrimitiveTerm(A, B, articleList.get(num - 1)));
					}
				}
			}
		}


		//module for term to term
		List<PrimitiveTerm> primitiveTermsForTerms = api.primterm().findAll();
		List<TermToTerm> termToTerms = new ArrayList<>();
		for(k = 0; k < newPrToPr.size(); k++)
		{
			PrimitiveTerm from = newPrToPr.get(k).getFrom(), to = newPrToPr.get(k).getTo();
			int a = from.getId(), b = to.getId();
			PrimitiveTerm aP = primitiveTermsForTerms.get(a-1), bP = primitiveTermsForTerms.get(b-1);
			a = aP.getTerm().getId();
			b = bP.getTerm().getId();
			Term A = terms.get(a-1), B = terms.get(b-1);
			TermToTerm newPair = new TermToTerm(A, B);
				if (newPair.getFrom() != newPair.getTo())
					if (!has(termToTerms, newPair))
						termToTerms.add(newPair);

		}
			try
			{
				newPrToPr = api.primTermToTerm().addTerms(newPrToPr);
				termToTerms = api.termToTerm().addTerms(termToTerms);

			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			api.onStop();

	}

	public static boolean has (List<TermToTerm> pairList, TermToTerm elem)
	{
		for (TermToTerm aPairList : pairList)
		{
			if (aPairList.equals(elem))
			{
				aPairList.incCount();
				return true;
			}
		}
		return false;

	}
}
