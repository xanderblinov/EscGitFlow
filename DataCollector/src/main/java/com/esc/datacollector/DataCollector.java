package com.esc.datacollector;

/**
 * Date: 4/16/2015
 * Time: 9:05 PM
 *
 * @author xanderblinov
 */
public class DataCollector
{
	private static final String PUBMED_START_LINE_REGEX = "(....)- (.*)";

	public static void main(String[] args)
	{
/*
		DatabaseApi api = DatabaseApiFactory.getDatabaseApi(DatabaseApiFactory.DatabaseType.Sqlite, Config.Database.TEST, false);

		api.onStart();
		for (Article article_id : api.article_id().getAllArticles())
		{
			System.out.println(article_id.toString());
		}

		api.onStop();
*/
		/*MedlineSource medlineSource = new MedlineSource();

		medlineSource.addField("PMID- ","24d1221");
		medlineSource.addField("b","bb");
		medlineSource.addField("b","ba");
		medlineSource.addField("c","cc");
		medlineSource.addField("c","dd");
		PubmedCard pubmedCard = Medliner.readMedline(medlineSource, PubmedCard.class);

		String string = "PMID- 23656783";
		String pattern  = "....- (.*)";
		if(string.matches(pattern)){
			System.out.println(pubmedCard.getPmid());
		}

		String resultString = "PMID- qqwqwdqwd".replaceAll(PUBMED_START_LINE_REGEX, "$1");
		System.out.println(resultString);
		resultString = "PMID- qqwqwdqwd".replaceAll(PUBMED_START_LINE_REGEX, "$2").trim();
		System.out.println(resultString);


		new PubMedParser("./test_pubmed.htm").parseFile();*/

/*
		DatabaseApi api = new SqliteApi(Config.Database.TEST, false);

		api.onStart();

		AuthorImpl author1 = new AuthorImpl("1","1","1") ;
		ArticleImpl article1= new ArticleImpl("1","1",1,1);
		CompanyImpl company = new CompanyImpl("323");

		api.company().create(company);
		api.author().create(author1);
		api.article().create(article1);
		AuthorToArticleImpl authorToArticle1 = new AuthorToArticleImpl(author1,article1);
		api.authorArticle().create(authorToArticle1);

		AuthorToCompanyImpl authorToCompany = new AuthorToCompanyImpl(author1,company);

		api.authorCompany().create(authorToCompany);
		List<AuthorImpl> list= api.author().findAuthorsForCompany(company);

		System.out.println(list == null ? " null " : list.size() + " " + list.get(0));*/

	}
}
