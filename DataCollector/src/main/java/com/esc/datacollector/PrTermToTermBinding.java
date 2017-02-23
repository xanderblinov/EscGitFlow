package com.esc.datacollector;

import java.io.File;
import java.util.Iterator;

import net.inference.Config;
import net.inference.sqlite.DatabaseApi;
import net.inference.sqlite.dto.PrimitiveTerm;
import net.inference.sqlite.dto.Term;

import edu.smu.tspell.wordnet.NounSynset;
import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.SynsetType;
import edu.smu.tspell.wordnet.WordNetDatabase;

/**
 * Created by palen on 22.02.2017.
 */
public class PrTermToTermBinding
{
	private static final String NO_TERM_FLAG = "mNoTerm";

	public static void bind()
	{
		DatabaseApi api = new DatabaseApi(Config.Database.TEST, false);
		api.onStart();
		wordNetDatabaseConfig();

		WordNetDatabase database = WordNetDatabase.getFileInstance();

		Iterator<PrimitiveTerm> iterator = api.primTerm().getIteratorByProperty(NO_TERM_FLAG, true);
		while (iterator.hasNext())
		{
			PrimitiveTerm primitiveTerm = iterator.next();
			Synset[] synsets = database.getSynsets(primitiveTerm.getValue(), SynsetType.NOUN);
			if (synsets.length != 0)
			{
				NounSynset nounSynset = (NounSynset) synsets[0];
				final Term term = new Term(nounSynset.getWordForms()[0]);
				api.term().addTerm(term);
				for (String word : nounSynset.getWordForms())
				{
					findPrTermByValueAndSetItsTerm(api, word.toLowerCase(), term);
				}
			}
			else
			{
				final Term term = new Term(primitiveTerm);
				api.term().addTerm(term);
				findPrTermByValueAndSetItsTerm(api, primitiveTerm.getValue(), term);
			}
			iterator = api.primTerm().getIteratorByProperty(NO_TERM_FLAG, true);
		}
	}

	private static void findPrTermByValueAndSetItsTerm(DatabaseApi api, String primitiveTermValue, Term term)
	{
		api.primTerm().findByPropertyAndModifiedProperty("value", primitiveTermValue, "s").forEach(primTerm ->
		{
			api.primTerm().changeProperty(api.primTerm().id(primTerm), "term", term);
			api.primTerm().changeProperty(api.primTerm().id(primTerm), NO_TERM_FLAG, false);
		});
	}

	private static void wordNetDatabaseConfig()
	{
		File worldNetDir = new File("DataCollector/src/main/resources/WordNet/dict/");
		System.setProperty("wordnet.database.dir", worldNetDir.getAbsolutePath());
	}
}
