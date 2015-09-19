package com.esc.datacollector;

import com.esc.common.util.Checks;
import com.esc.datacollector.app.Application;
import com.esc.datacollector.data.PubmedCard;

import net.inference.database.DatabaseApi;
import net.inference.sqlite.dto.ArticleImpl;
import net.inference.sqlite.dto.PrimitiveAuthorImpl;
import net.inference.sqlite.dto.PrimitiveCoAuthorshipImpl;

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

		final DatabaseApi databaseApi = Application.getDatabaseApi();

		final boolean hasKeywords = !Checks.isEmpty(pubmedCard.getKeyWorlds());
		final boolean singleOrganyzation = pubmedCard.getAU().length != pubmedCard.getDP().length();

		List<PrimitiveAuthorImpl> primitiveAuthors = new ArrayList<>();

		ArticleImpl article = new ArticleImpl(pubmedCard.getPmid(), pubmedCard.getPmid(), 0, 0);

		try
		{
			if (databaseApi.article().exists(article))
			{
				return false;
			}
			databaseApi.article().addArticle(article);
		}
		catch (SQLException e)
		{
			return false;
		}

		for (int i = 0; i < pubmedCard.getAU().length; i++)
		{
			final String au = pubmedCard.getAU()[i];
			final String fau = pubmedCard.getFAU()[i];
			PrimitiveAuthorImpl primitiveAuthor = new PrimitiveAuthorImpl(au, fau, article);

			primitiveAuthors.add(primitiveAuthor);

			System.out.println(primitiveAuthor.toString());
		}

		try
		{
			primitiveAuthors = databaseApi.primitiveAuthor().addAuthors(primitiveAuthors);

			final List<PrimitiveCoAuthorshipImpl> coAuthorshipList = databaseApi.primitiveAuthor().addCoauthors(primitiveAuthors);

			System.out.println(Arrays.toString(coAuthorshipList.toArray(new PrimitiveCoAuthorshipImpl[coAuthorshipList.size()])));
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

		return true;
	}
}
