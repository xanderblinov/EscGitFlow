package com.esc.datacollector;

import com.esc.common.util.Checks;
import com.esc.datacollector.app.Application;
import com.esc.datacollector.data.PubmedCard;

import net.inference.database.IDatabaseApi;
import net.inference.sqlite.dto.Article;
import net.inference.sqlite.dto.ArticleSource;
import net.inference.sqlite.dto.PrimitiveAuthor;
import net.inference.sqlite.dto.PrimitiveAuthorToAuthor;
import net.inference.sqlite.dto.PrimitiveTerm;
import net.inference.sqlite.dto.PrimitiveTermToPrimitiveTerm;
import net.inference.sqlite.dto.Term;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.smu.tspell.wordnet.*;

/**
 * Date: 13-Sep-15
 * Time: 9:49 PM
 *
 * @author xanderblinov
 */
public class PubmedCardProcessor implements IPubmedCardProcessor
{
	List<Term> terms = new ArrayList<>();
	@Override
	public boolean execute(PubmedCard pubmedCard)
	{
		if (!check(pubmedCard))
		{
			return false;
		}


		final IDatabaseApi databaseApi = Application.getDatabaseApi();
		final WordNetDatabase database = WordNetDatabase.getFileInstance();

		final boolean hasKeyOt = !Checks.isEmpty(pubmedCard.getKeyOt());
		final boolean hasKeyMh = !Checks.isEmpty(pubmedCard.getKeyMh());

		final boolean singleOrganyzation = pubmedCard.getAU().length != pubmedCard.getDP().length();

		List<PrimitiveAuthor> primitiveAuthors = new ArrayList<>();
		List<PrimitiveTerm> primitiveTerms = new ArrayList<>();
		List<PrimitiveTermToPrimitiveTerm> primTermToTerms = new ArrayList<>();

		Article article = new Article(pubmedCard.getTitle(), pubmedCard.getPmid(), pubmedCard.getYear(), ArticleSource.PUBMED);

		//System.out.println(Arrays.toString(pubmedCard.getKeyWords()));
		try
		{
			if (databaseApi.article().exists(article))
			{
				return false;
			}
			article = databaseApi.article().addArticle(article);
		}
		catch (SQLException e)
		{
			return false;
		}

		for (int i = 0; i < pubmedCard.getAU().length; i++)
		{
			final String au = pubmedCard.getAU()[i];
			final String fau = pubmedCard.getFAU()[i];
			PrimitiveAuthor primitiveAuthor = new PrimitiveAuthor(au, fau, article);
			//TODO calculate encoding
			primitiveAuthor.setEncoding(fau);

			primitiveAuthors.add(primitiveAuthor);

			System.out.println(primitiveAuthor.toString());
		}
		if (hasKeyOt)
			for (int i = 0; i < pubmedCard.getKeyOt().length ; i++)
			{
				final String keyWords = pubmedCard.getKeyOt()[i];
				final int year = pubmedCard.getYear();
				PrimitiveTerm primitiveTerm = new PrimitiveTerm(keyWords,"OT", year, article);
				if(primitiveTerm.getValue().contains(",")){
					ArrayList<String> primitiveTermsArray=primitiveTerm.separatePrimitiveTerms();
					for (int j = 0; j < primitiveTermsArray.size() ; j++)
					{
						PrimitiveTerm separatedPrimitiveTerm=
								new PrimitiveTerm(primitiveTermsArray.get(j).trim(),"MH",year, article);
						primitiveTerms.add(separatedPrimitiveTerm);
					}
				}
				else
					primitiveTerms.add(primitiveTerm);
				System.out.println(primitiveTerm.toString());
			}

		if (hasKeyMh)
			for (int i = 0; i < pubmedCard.getKeyMh().length ; i++)
			{
				final String keyWords = pubmedCard.getKeyMh()[i];
				final int year = pubmedCard.getYear();
				PrimitiveTerm primitiveTerm = new PrimitiveTerm(keyWords,"MH", year, article);
				if(primitiveTerm.getValue().contains(",")){
					ArrayList<String> primitiveTermsArray=primitiveTerm.separatePrimitiveTerms();
					for (int j = 0; j < primitiveTermsArray.size(); j++)
					{
						PrimitiveTerm separatedPrimitiveTerm=
								new PrimitiveTerm(primitiveTermsArray.get(j).trim(),"MH",year, article);
						primitiveTerms.add(separatedPrimitiveTerm);
					}
				}
				else
					primitiveTerms.add(primitiveTerm);
				System.out.println(primitiveTerm.toString());
			}

		for(int i = 0; i < primitiveTerms.size(); i++)
		{
			for(int j = 0; j < primitiveTerms.size()-1; j++){
				PrimitiveTermToPrimitiveTerm primTermToTerm =
						new PrimitiveTermToPrimitiveTerm(primitiveTerms.get(i),primitiveTerms.get(j),article);
				primTermToTerms.add(primTermToTerm);
			}
		}

		fpts: for (int i = 0; i < primitiveTerms.size() ; i++)
		{
			final PrimitiveTerm primitiveTerm = primitiveTerms.get(i);
			final String primitiveTermValue = primitiveTerm.getValue();
			Synset[] synsets = database.getSynsets(primitiveTermValue, SynsetType.NOUN);
			//получаем массив групп синонимов существительных для текущего примитивного термина
			for (int j = 0; j < terms.size(); j++)
			{
				if (!primitiveTermValue.equals(terms.get(j).getValue()))
					for (int k = 0; k < synsets.length; k++)
					{
						final NounSynset nounSynset=(NounSynset)synsets[k];
						if (terms.get(j).getValue().equals(nounSynset.getWordForms()[0]))
						//сравниваем основное значение из каждой группы синонимов со значениями уже существуюших терминов
						{
							terms.get(j).incCounter();
							System.out.printf("COUNTER INCREASED "+terms.get(j).getValue()+"%n");
							continue fpts;
						}
					}
				else {
					terms.get(j).incCounter();
					System.out.printf("COUNTER INCREASED "+terms.get(j).getValue()+"%n");
					continue fpts;
				}
			}
			Term newTerm = new Term(primitiveTerm);
			terms.add(newTerm);
			System.out.printf("TERMS SIZE %d%n",terms.size());
		}

		try
		{
			primitiveAuthors = databaseApi.primitiveAuthor().addAuthors(primitiveAuthors);

			final List<PrimitiveAuthorToAuthor> primitiveAuthorToAuthors = databaseApi.primitiveAuthor().addCoauthors(primitiveAuthors);

			System.out.println(Arrays.toString(primitiveAuthorToAuthors.toArray(new PrimitiveAuthorToAuthor[primitiveAuthorToAuthors.size()])));

			primitiveTerms = databaseApi.primterm().addTerms(primitiveTerms);

			primTermToTerms = databaseApi.primTermToTerm().addTerms(primTermToTerms);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}


		//TODO add authors and coauthors to api impl

		return true;
	}

	private boolean check(PubmedCard pubmedCard)
	{
		if (Checks.isEmpty(pubmedCard.getPmid()))
		{
			System.out.println("no PMID");
			return false;
		}
		if (Checks.isEmpty(pubmedCard.getOrganizations()))
		{
			System.out.println(pubmedCard.getPmid() + " no organization found");
			return false;
		}

		if (Checks.isEmpty(pubmedCard.getAU()))
		{
			System.out.println(pubmedCard.getPmid() + " no authors  found");
			return false;
		}

		if (Checks.isEmpty(pubmedCard.getKeyOt()))
		{
			System.out.println(pubmedCard.getPmid() + " no terms  found");
			return false;
		}

		return true;
	}
	public void addTerms()
	{
		final IDatabaseApi databaseApi = Application.getDatabaseApi();
		try
		{
			terms = databaseApi.term().addTerms(terms);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
