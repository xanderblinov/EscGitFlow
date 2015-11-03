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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Date: 13-Sep-15
 * Time: 9:49 PM
 *
 * @author xanderblinov
 */
public class PubmedCardProcessor implements IPubmedCardProcessor
{

	@Override
	public boolean execute(PubmedCard pubmedCard)
	{
		if (!check(pubmedCard))
		{
			return false;
		}

		final IDatabaseApi databaseApi = Application.getDatabaseApi();

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
				PrimitiveTerm primitiveTerm = new PrimitiveTerm(keyWords,"OT", article);
				if(primitiveTerm.getValue().contains(",")){
					ArrayList<String> primitiveTermsArray=primitiveTerm.separatePrimitiveTerms();
					for (int j = 0; j < primitiveTermsArray.size() ; j++)
					{
						primitiveTerms.add((new PrimitiveTerm(primitiveTermsArray.get(j).trim(),"OT",article)));
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
				PrimitiveTerm primitiveTerm = new PrimitiveTerm(keyWords,"MH", article);
				if(primitiveTerm.getValue().contains(",")){
					ArrayList<String> primitiveTermsArray=primitiveTerm.separatePrimitiveTerms();
					for (int j = 0; j < primitiveTermsArray.size() ; j++)
					{
						primitiveTerms.add((new PrimitiveTerm(primitiveTermsArray.get(j).trim(),"MH",article)));
					}
				}
				else
					primitiveTerms.add(primitiveTerm);
				System.out.println(primitiveTerm.toString());
			}

		for(int i = 0; i < primitiveTerms.size(); i++)
		{
			for(int j = 0; j < primitiveTerms.size()-1; j++){
				PrimitiveTermToPrimitiveTerm primTermToTerm = new PrimitiveTermToPrimitiveTerm(primitiveTerms.get(i),primitiveTerms.get(j),article);
				primTermToTerms.add(primTermToTerm);
			}
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
}
