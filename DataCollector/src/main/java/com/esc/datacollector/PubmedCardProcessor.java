package com.esc.datacollector;

import com.esc.common.util.Checks;
import com.esc.datacollector.app.Application;
import com.esc.datacollector.data.PubmedCard;
import edu.smu.tspell.wordnet.NounSynset;
import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.SynsetType;
import edu.smu.tspell.wordnet.WordNetDatabase;
import net.inference.database.IDatabaseApi;
import net.inference.sqlite.dto.*;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Date: 13-Sep-15
 * Time: 9:49 PM
 *
 * @author xanderblinov
 */
public class PubmedCardProcessor implements IPubmedCardProcessor
{
	List<Term> terms = new ArrayList<>();
	List<TermYear> termsYear = new ArrayList<>();

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
		final boolean hasAb = !Checks.isEmpty(pubmedCard.getAB());

		if(!hasAb)
			return false;

		final boolean singleOrganyzation = pubmedCard.getAU().length != pubmedCard.getDP().length();

		List<PrimitiveAuthor> primitiveAuthors = new ArrayList<>();
		List<PrimitiveTerm> primitiveTerms = new ArrayList<>();
		List<PrimitiveTermToPrimitiveTerm> primTermToTerms = new ArrayList<>();
		List<TermToTerm> termToTerms = new ArrayList<>();



		final int year = pubmedCard.getYearDp();
		Article article = new Article(pubmedCard.getTitle(), pubmedCard.getPmid(), year, ArticleSource.PUBMED);






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

		/*for (int i = 0; i < pubmedCard.getFAU().length; i++)
		{
			final String au = pubmedCard.getAU()[i];
			final String fau = pubmedCard.getFAU()[i];
			PrimitiveAuthor primitiveAuthor = new PrimitiveAuthor(au, fau, article);
			//TODO calculate encoding
			primitiveAuthor.setEncoding(fau);

			primitiveAuthors.add(primitiveAuthor);

			//System.out.println(primitiveAuthor.toString());
		}*/



		/*if (hasKeyOt)
			for (int i = 0; i < pubmedCard.getKeyOt().length ; i++)
			{
				final String keyWords = pubmedCard.getKeyOt()[i];
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
				//System.out.println(primitiveTerm.toString());
			}*/

		/*if (hasKeyMh)
			for (int i = 0; i < pubmedCard.getKeyMh().length ; i++)
			{
				final String keyWords = pubmedCard.getKeyMh()[i];
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
				//System.out.println(primitiveTerm.toString());
			}*/


		//open the dict with common words
		File file = new File("DataCollector/src/main/resources/2of12inf.txt");
		Scanner scanner;


		int legalDiff;
		List<String> mirnaList = Arrays.asList("mirna", "mi-rna", "microrna", "micro-rna", "mirnas", "mi-rnas", "micrornas", "micro-rnas");


		try{
			scanner = new Scanner(file);
			List<String> common_words = new ArrayList<>();
			while (scanner.hasNext()) {
				if (scanner.hasNext()) {
					common_words.add(scanner.nextLine());
				} else {
					scanner.next();
				}
			}



			if(hasAb)
			{
				String annotation = pubmedCard.getAB().toLowerCase();
				annotation = annotation.replaceAll("\\+|#|&|$|\\{|\\}|\\||\\(|\\)|\\[|\\]|\\.|,|;|\\*|\"|~|:| - |'s|'|=|%|<|>|\\?", "");
				annotation = annotation.replaceAll("/"," ");
				String [ ] word_array = annotation.split(" ");

				for(int j = 0; j < word_array.length; j++){
					//word_array[j] = word_array[j].toLowerCase();
					boolean exist = false;
					String word = word_array[j];

					if((word.length() == 0) || word.matches("-?(\\d+)-?(\\d+)?"))
					{
						continue;
					}

					if (common_words.contains(word) && common_words.contains(word.replace("-", "")))
					{
						continue;
					}

					for (PrimitiveTerm primTerm : primitiveTerms)
					{
						if (word.length() <= 2 || primTerm.getValue().length() <= 2)
							legalDiff = 1;
						else
							legalDiff = 2;

						if ((LevenshteinDist(word, primTerm.getValue())) < legalDiff ||
								mirnaList.contains(word) && mirnaList.contains(primTerm.getValue()) )
						{
							if(word.length() < primTerm.getValue().length())
								primTerm.setValue(word);
							exist = true;
							continue;
						}
					}

					if (exist)
						continue;
					PrimitiveTerm abstractTerm = new PrimitiveTerm(word, "AB", year, article);
					primitiveTerms.add(abstractTerm);
				}
			}
		}
		catch (IOException e)
		{
		}


		File worldNetDir = new File("DataCollector/src/main/resources/world_net/dict");
		System.setProperty("wordnet.database.dir", worldNetDir.getAbsolutePath());



		fpts:
		for (final PrimitiveTerm primitiveTerm : primitiveTerms)
		{
			final String primitiveTermValue = primitiveTerm.getValue();

			Synset[] synsets;
			try
			{
				synsets = database.getSynsets(primitiveTermValue, SynsetType.NOUN);
			}
			catch (Exception e)
			{
				synsets = new Synset[0];
			}

			//we are getting all synsets from WordNet for our primitive term
			for (final Term term : terms)
			{
				final String termValue = term.getValue();



				if (primitiveTermValue.length() <= 2 || termValue.length() <= 2)
					legalDiff = 1;
				else
					legalDiff = 2;

				if (LevenshteinDist(primitiveTermValue, termValue) < legalDiff)
				{
					if(termValue.length() < primitiveTermValue.length())
						primitiveTerm.setValue(termValue);

					TermYear termYear = new TermYear(term, primitiveTerm.getYear());
					boolean ex = false;

					for (TermYear aTermsYear : termsYear)
					{
						if (aTermsYear.getYear() == termYear.getYear() && aTermsYear.getTerm().getId() == term.getId())
						{
							ex = true;
							aTermsYear.incCounter();
							break;
						}
					}
					if (!ex)
						termsYear.add(termYear);


					term.addCount(1.0 / (double)primitiveTerms.size());
					primitiveTerm.setTerm(term);
					continue fpts;
				}
				else
				{
					for (Synset synset : synsets)
					{
						final NounSynset nounSynset = (NounSynset) synset;
						for (int l = 0; l < nounSynset.getWordForms().length; l++)
							if (termValue.intern().equals(nounSynset.getWordForms()[l]))
							//comparing meanings from synset with term
							{
								TermYear termYear = new TermYear(term, primitiveTerm.getYear());
								boolean ex = false;
								for (TermYear aTermsYear : termsYear)
								{
									if (aTermsYear.getYear() == termYear.getYear() && aTermsYear.getTerm().getId() == term.getId())
									{
										ex = true;
										aTermsYear.incCounter();
										break;
									}
								}
								if (!ex)
									termsYear.add(termYear);

								//вес термина считаем не как количество научных публикаций, а как сумма по всем публикациям чисел, обратных к числу терминов в аннотации статьи
								term.addCount(1.0 / (double)primitiveTerms.size());
								primitiveTerm.setTerm(term);
								continue fpts;
							}

					}
					if (mirnaList.contains(termValue) && mirnaList.contains(primitiveTermValue))
					{
						if(termValue.length() < primitiveTermValue.length())
							primitiveTerm.setValue(termValue);
						TermYear termYear = new TermYear(term, primitiveTerm.getYear());
						boolean ex = false;
						for (TermYear aTermsYear : termsYear)
						{
							if (aTermsYear.getYear() == termYear.getYear() && aTermsYear.getTerm().getId() == term.getId())
							{
								ex = true;
								aTermsYear.incCounter();
								break;
							}
						}
						if (!ex)
							termsYear.add(termYear);


						term.addCount(1.0 / (double)primitiveTerms.size());
						primitiveTerm.setTerm(term);
						continue fpts;
					}
				}

			}
			Term newTerm = new Term(terms.size() + 1, primitiveTerm);
			newTerm.addCount(1.0 / (double)primitiveTerms.size());
			terms.add(newTerm);
			primitiveTerm.setTerm(newTerm);

			TermYear termYear = new TermYear(newTerm, primitiveTerm.getYear());
			termsYear.add(termYear);
		}




		try
		{
			primitiveAuthors = databaseApi.primitiveAuthor().addAuthors(primitiveAuthors);

			final List<PrimitiveAuthorToAuthor> primitiveAuthorToAuthors = databaseApi.primitiveAuthor().addCoauthors(primitiveAuthors);

			//System.out.println(Arrays.toString(primitiveAuthorToAuthors.toArray(new PrimitiveAuthorToAuthor[primitiveAuthorToAuthors.size()])));

			primitiveTerms = databaseApi.primterm().addTerms(primitiveTerms);


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
			//return false;
		}

		if (Checks.isEmpty(pubmedCard.getAU()))
		{
			System.out.println(pubmedCard.getPmid() + " no authors  found");
			return false;
		}

		if (Checks.isEmpty(pubmedCard.getKeyOt()) && Checks.isEmpty(pubmedCard.getAB()) && Checks.isEmpty(pubmedCard.getKeyMh()))
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

	public void addTermsYear()
	{
		final IDatabaseApi databaseApi = Application.getDatabaseApi();
		try
		{
			termsYear = databaseApi.termYear().addTerms(termsYear);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}


	//сравнение терминов по расстоянию Левенштейна
	private static int LevenshteinDist(String S1, String S2)
	{

		//костыль
		if(S1.matches("dnas?") && S2.matches("rnas?") || S2.matches("dnas?") && S1.matches("rnas?"))
			return 10;

		S1 = S1.toLowerCase().replace("-","");
		S2 = S2.toLowerCase().replace("-","");
		if(S1.equals(S2))
			return 0;
		if(S1.length() <=2 || S2.length() <= 2) //убрать потом
			return 10;

		int m = S1.length(), n = S2.length();
		int[] D1;
		int[] D2 = new int[n + 1];
		int add=0;
		for (int i = 0; i <= n; i++)
			D2[i] = i;


		Pattern pat=Pattern.compile("[-]?[0-9]+(.[0-9]+)?");

		Matcher matcher1 = pat.matcher(S1);
		Matcher matcher2 = pat.matcher(S2);

		if(matcher1.find() && !matcher2.find()  ||  !matcher1.find() && matcher2.find())
			add = add + 5;


		for (int i = 1; i <= m; i++)
		{
			D1 = D2;
			D2 = new int[n + 1];
			for (int j = 0; j <= n; j++)
			{
				if (j == 0) D2[j] = i;
				else
				{
					int cost = (S1.charAt(i - 1) != S2.charAt(j - 1)) ? 1 : 0;
					if( (S1.charAt(i - 1) > 47  && S1.charAt(i - 1) < 58) && (S2.charAt(j - 1) > 47 && S2.charAt(j - 1) < 58) )
					{
						add = add + Fine(S1, i-1, S2, j-1);
						if(add > 3)
							return 3;
					}
					if (D2[j - 1] < D1[j] && D2[j - 1] < D1[j - 1] + cost)
						D2[j] = D2[j - 1] + 1;
					else if (D1[j] < D1[j - 1] + cost)
						D2[j] = D1[j] + 1;
					else
						D2[j] = D1[j - 1] + cost;
				}
			}
		}
		return D2[n]+add;
	}

	//штрафная функция: чтобы при различии двух слов в одну цифру, они считались точно разными словами
	private static int Fine(String s1, int k, String s2, int l){
		int fine = 0, i;

		if(k > 0 && s1.charAt(k-1) > 47 && s1.charAt(k-1) < 58)
			return 0;
		if(l > 0 && s2.charAt(l-1) > 47 && s2.charAt(l-1) < 58)
			return 0;
		/*if(s1.charAt(k) < 48 || s1.charAt(k) > 57 || s2.charAt(l) < 48 || s2.charAt(l) > 57)
			return 0;*/

		int a = Character.getNumericValue(s1.charAt(k));
		int b = Character.getNumericValue(s2.charAt(l));


		for(i = k+1; i < s1.length(); i++)
		{
			if(s1.charAt(i) > 47 && s1.charAt(i) < 58)
				a = a*10 + Character.getNumericValue(s1.charAt(i));
			else
				break;
		}
		for(i = l+1; i < s2.length(); i++)
		{
			if(s2.charAt(i) > 47 && s2.charAt(i) < 58)
				a = a*10 + Character.getNumericValue(s2.charAt(i));
			else
				break;
		}

		if(a != b)
			fine = 5;
		return fine;
	}
}
