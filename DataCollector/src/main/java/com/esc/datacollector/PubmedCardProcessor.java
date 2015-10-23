package com.esc.datacollector;

import com.esc.common.util.Checks;
import com.esc.datacollector.app.Application;
import com.esc.datacollector.data.PubmedCard;

import net.inference.database.IDatabaseApi;
import net.inference.sqlite.dto.Article;
import net.inference.sqlite.dto.ArticleSource;
import net.inference.sqlite.dto.PrimitiveAuthor;
import net.inference.sqlite.dto.PrimitiveAuthorToAuthor;
import net.inference.sqlite.dto.Term;

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

		final boolean hasKeywords = !Checks.isEmpty(pubmedCard.getKeyWorlds());
		final boolean singleOrganyzation = pubmedCard.getAU().length != pubmedCard.getDP().length();

		List<PrimitiveAuthor> primitiveAuthors = new ArrayList<>();
		List<Term> newTerms = new ArrayList<>();////mari

		Article article = new Article(pubmedCard.getTitle(), pubmedCard.getPmid(), pubmedCard.getYear(), ArticleSource.PUBMED);

		System.out.println("Keys: " + Arrays.toString(pubmedCard.getKey()));////
		System.out.println("KeyWorlds: " + Arrays.toString(pubmedCard.getKeyWorlds()));////



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

		if(hasKeywords)
		{
			for (int j = 0; j < pubmedCard.getKeyWorlds().length; j++){
				final String term = pubmedCard.getKeyWorlds()[j];
				Term newTerm = new Term(term, pubmedCard.getTitle().toString(), pubmedCard.getKeyWorlds());
				if (newTerm.getValue().contains(",")){
					ArrayList<String> termArr = newTerm.setEncoding();
					for(int i = 0; i < termArr.size(); i++){
						Term subTerm = new Term(termArr.get(i), pubmedCard.getTitle().toString(), pubmedCard.getKeyWorlds());
						newTerms.add(subTerm);
					}
				}
				else
					newTerms.add(newTerm);
				System.out.println(newTerm.toString());
			}
		}

		try
		{
			primitiveAuthors = databaseApi.primitiveAuthor().addAuthors(primitiveAuthors);
			newTerms = databaseApi.term().addTerms(newTerms);

			final List<PrimitiveAuthorToAuthor> primitiveAuthorToAuthors = databaseApi.primitiveAuthor().addCoauthors(primitiveAuthors);

			System.out.println(Arrays.toString(primitiveAuthorToAuthors.toArray(new PrimitiveAuthorToAuthor[primitiveAuthorToAuthors.size()])));
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

		if (Checks.isEmpty(pubmedCard.getKeyWorlds()))
		{
			System.out.println(pubmedCard.getPmid() + " no terms  found");
			return false;
		}

		return true;
	}
}
