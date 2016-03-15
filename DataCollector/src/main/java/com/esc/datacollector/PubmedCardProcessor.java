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
import net.inference.sqlite.dto.TermToTerm;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.smu.tspell.wordnet.NounSynset;
import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.SynsetType;
import edu.smu.tspell.wordnet.WordNetDatabase;

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
		final boolean hasAb = !Checks.isEmpty(pubmedCard.getAB());

		final boolean singleOrganyzation = pubmedCard.getAU().length != pubmedCard.getDP().length();

		List<PrimitiveAuthor> primitiveAuthors = new ArrayList<>();
		List<PrimitiveTerm> primitiveTerms = new ArrayList<>();
		List<PrimitiveTermToPrimitiveTerm> primTermToTerms = new ArrayList<>();
		List<TermToTerm> termToTerms = new ArrayList<>();


		//List<Integer> list = new ArrayList<Integer>();
		//File file = new File("x./2of12inf.tt");

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

			//System.out.println(primitiveAuthor.toString());
		}

		final int year = pubmedCard.getYear();

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
				String annotation = pubmedCard.getAB();
				annotation = annotation.replaceAll("\\(|\\)|\\[|\\]|\\.|,|;|\\*|\"|~|:| - |'s|'|=|%|<|>", "");
				String [ ] word_array = annotation.split(" ");

				for(int j = 0; j < word_array.length; j++){
					if(!(word_array[j].length() == 0) && !word_array[j].matches("\\d+")){
						if(!common_words.contains(word_array[j].toLowerCase()) && !common_words.contains(word_array[j].toLowerCase().replace("-", ""))){
							PrimitiveTerm abstractTerm = new PrimitiveTerm(word_array[j],"AB",year, article);
							primitiveTerms.add(abstractTerm);
						}
					}
				}
			}
		}
		catch (IOException e)
		{
		}


		for(int i = 0; i < primitiveTerms.size(); i++)
		{
			for(int j = 0; j < primitiveTerms.size()-1; j++){
				if(i != j){
					PrimitiveTermToPrimitiveTerm primTermToTerm =
						new PrimitiveTermToPrimitiveTerm(primitiveTerms.get(i),primitiveTerms.get(j),article);
					primTermToTerms.add(primTermToTerm);
				}
			}
		}

		System.setProperty("wordnet.database.dir", "D:\\Programs\\WordNet2.1\\dict\\");
		fpts: for (int i = 0; i < primitiveTerms.size() ; i++)
		{
			final PrimitiveTerm primitiveTerm = primitiveTerms.get(i);
			final String primitiveTermValue = primitiveTerm.getValue();
			Synset[] synsets = database.getSynsets(primitiveTermValue, SynsetType.NOUN);
			//we are getting all synsets from WordNet for our primitive term
			for (int j = 0; j < terms.size(); j++)
			{
				final Term term=terms.get(j);
				final String termValue=term.getValue();

				if (primitiveTerm.getYear() == term.getYear()){
					if ( !(primitiveTermValue.intern() == termValue.intern()))
						for (int k = 0; k < synsets.length; k++)
						{
							final NounSynset nounSynset=(NounSynset)synsets[k];
							for (int l=0; l < nounSynset.getWordForms().length;l++)
								if (termValue.intern() == nounSynset.getWordForms()[l].intern())
								//comparing meanings from synset with term
								{
									term.incCounter();
									primitiveTerm.setTerm(term);
									continue fpts;
								}
						}
					else {
							term.incCounter();
							primitiveTerm.setTerm(term);
							continue fpts;
						 }
				}
			}
			Term newTerm = new Term(terms.size()+1,primitiveTerm);
			terms.add(newTerm);
			primitiveTerm.setTerm(newTerm);
		}

		//module for term to term
		for(int k = 0; k < primTermToTerms.size(); k++){
			PrimitiveTerm from = primTermToTerms.get(k).getFrom(), to = primTermToTerms.get(k).getTo();
			Term A = from.getTerm(), B = to.getTerm();
			TermToTerm newPair = new TermToTerm(A,B);
			if(newPair.getFrom() != newPair.getTo())
				if (!has(termToTerms, newPair))
					termToTerms.add(newPair);
		}


		/*alternative way for termToTerm using database, very slow way

		for(int k = 0; k < primTermToTerms.size(); k++){
			PrimitiveTerm from = primTermToTerms.get(k).getFrom(), to = primTermToTerms.get(k).getTo();
			Term A = from.getTerm(), B = to.getTerm();
			TermToTerm newPair = new TermToTerm(A,B);

			if(newPair.getFrom() != newPair.getTo()){
				try{
					if(!databaseApi.termToTerm().exists(newPair))
						databaseApi.termToTerm().addTerm(newPair);
					else{
						TermToTerm elem = databaseApi.termToTerm().findByProperties(TermToTerm.Column.from, A.getId(), TermToTerm.Column.to, B.getId()).get(0);
						databaseApi.termToTerm().changeProperty(elem.getId(), "count", elem.getCount() + 1);
					}
				}
				catch (SQLException e){

				}
			}
		}*/



		try
		{
			primitiveAuthors = databaseApi.primitiveAuthor().addAuthors(primitiveAuthors);

			final List<PrimitiveAuthorToAuthor> primitiveAuthorToAuthors = databaseApi.primitiveAuthor().addCoauthors(primitiveAuthors);

			//System.out.println(Arrays.toString(primitiveAuthorToAuthors.toArray(new PrimitiveAuthorToAuthor[primitiveAuthorToAuthors.size()])));

			primitiveTerms = databaseApi.primterm().addTerms(primitiveTerms);

			primTermToTerms = databaseApi.primTermToTerm().addTerms(primTermToTerms);

			termToTerms = databaseApi.termToTerm().addTerms(termToTerms);




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

	public boolean has (List<TermToTerm> pairList, TermToTerm elem){
		for(int i = 0; i < pairList.size(); i++)
			if( pairList.get(i).equals(elem) ){//.getFrom().getValue().equals(elem.getFrom().getValue()) && pairList.get(i).getTo().getValue().equals(elem.getTo().getValue()) ){
				pairList.get(i).incCount();
				return true;
			}
		return false;

	}
}
