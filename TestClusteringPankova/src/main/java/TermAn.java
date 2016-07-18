import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.inference.Config;
import net.inference.sqlite.DatabaseApi;
import net.inference.sqlite.dto.Article;
import net.inference.sqlite.dto.PrimitiveTerm;
import net.inference.sqlite.dto.Term;
import net.inference.sqlite.dto.TermYear;

/**
 * Created by Мария on 16.04.2016.
 */
public class TermAn
{
	public static void main(String[] args)
	{

		List<TermStat> anTermList = new ArrayList<>();

		DatabaseApi api = new DatabaseApi(Config.Database.TEST, false);

		api.onStart();

		TimeBorder time = new TimeBorder();


		final List<TermYear> termYearList = api.termYear().findAll();
		final List<Term> termList = api.term().findAll();
		final List<Article> articleList = api.article().findAll();
		final List<PrimitiveTerm> primTerms = api.primterm().findAll();

		api.onStop();

		//count article in year
		int[] countArticleInYear = new int[time.getFinish() - time.getStart() + 1];

		for (Article article : articleList)
		{
			int year = article.getYear();
			if (year >= time.getStart() && year <= time.getFinish())
				countArticleInYear[year - time.getStart()]++;
		}

		for (int count : countArticleInYear)
			System.out.print(count + " ");
		System.out.println();


		File id_link = new File("id_link" + time.getStart() + "_" + time.getFinish() + ".txt");
		File cluster = new File("ou" + time.getFinish() + ".txt");


		String idLine, clLine;

		try
		{
			FileWriter map = new FileWriter("map" + time.getStart() + "_" + time.getFinish() + ".txt", false);
			map.write("id\tcluster\tweight\n");

			BufferedReader id = new BufferedReader(new InputStreamReader(new FileInputStream(id_link)));
			BufferedReader cl = new BufferedReader(new InputStreamReader(new FileInputStream(cluster)));
			while ((idLine = id.readLine()) != null)
			{
				clLine = cl.readLine();
				int termId = Integer.parseInt(idLine.split(" ")[1]);
				anTermList.add(new TermStat(termId, termList.get(termId - 1).getValue(), Integer.parseInt(clLine),
						termList.get(termId - 1).getCount()));

				map.write(termList.get(termId - 1).getValue() + "\t" + (Integer.parseInt(clLine) + 1) + "\t"
						+ termList.get(termId - 1).getCount() + "\n");
			}

			id.close();
			cl.close();

			map.flush();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}


		for (TermStat term : anTermList)
		{
			int termId = term.getId();
			for (TermYear termYear : termYearList)
			{
				if (termId == termYear.getTerm().getId() && termYear.getYear() >= time.getStart()
						&& termYear.getYear() <= time.getFinish())
					term.addUsing(new Frequency(termYear.getYear(), termYear.getCount()));
			}
		}

		for (TermStat term : anTermList)
		{
			term.setProb(time.getStart(), time.getFinish(), countArticleInYear);
			term.setLnSeq(time.getStart());
		}

		Collections.sort(anTermList, compTermStat);
		for (TermStat anAnList : anTermList)
		{
			if (anAnList.getWeight() > 0.0)
				System.out.print(anAnList);
		}



		//считаем вес каждого кластера
		int countOfClusters = 2;

		for (int i = 0; i < countOfClusters; i++){
			System.out.println("Cluster " + i + ": " + getCountInClusterTerm(anTermList, i));
			System.out.println(getWeightCluster(anTermList, i) + "\n");
		}


		//count correlation
		int[] hist = new int [20];
		int i = 0; int j = 0;
		double[][] corrMatrix = new double[anTermList .size()][anTermList .size()];
		for(i = 140; i < anTermList .size(); i++)
		{
			for(j = i + 1; j < anTermList .size(); j++)
			{
				double corr = (new Correlation(anTermList .get(i).getProb(), anTermList .get(j).getProb())).getCorrelation();
				corrMatrix[i][j] = corr;
				//дерьмокод :(
				if(corr < -0.9)
					hist[0]++;
				if(corr < -0.8 && corr >= -0.9)
					hist[1]++;
				if(corr < -0.7 && corr >= -0.8)
					hist[2]++;
				if(corr < -0.6 && corr >= -0.7)
					hist[3]++;
				if(corr < -0.5 && corr >= -0.6)
					hist[4]++;
				if(corr < -0.4 && corr >= -0.5)
					hist[5]++;
				if(corr < -0.3 && corr >= -0.4)
					hist[6]++;
				if(corr < -0.2 && corr >= -0.3)
					hist[7]++;
				if(corr < -0.1 && corr >= -0.2)
					hist[8]++;
				if(corr < 0.0 && corr >= -0.1)
					hist[9]++;
				if(corr < 0.1 && corr >= 0.0)
					hist[10]++;

				if(corr < 0.2 && corr >= 0.1)
					hist[11]++;
				if(corr < 0.3 && corr >= 0.2)
					hist[12]++;
				if(corr < 0.4 && corr >= 0.3)
					hist[13]++;
				if(corr < 0.5 && corr >= 0.4)
					hist[14]++;
				if(corr < 0.6 && corr >= 0.5)
					hist[15]++;
				if(corr < 0.7 && corr >= 0.6)
					hist[16]++;
				if(corr < 0.8 && corr >= 0.7)
					hist[17]++;
				if(corr < 0.9 && corr >= 0.8)
					hist[18]++;
				if(corr < 1.0 && corr >= 0.9)
					hist[19]++;

				if(corr > 0.95)
					System.out.println(anTermList .get(i).getVal() + ", " + anTermList .get(j).getVal() + " " + corr);
			}
		}

		System.out.println("Data for histogram: " + anTermList.size());
		for(i = 0; i < hist.length; i++)
			System.out.println(hist[i]);

		System.out.println("Count of terms: " + anTermList.size() * (anTermList.size()-1) / 2);

		/*List<ArticleStat> anArtList = new ArrayList<>();
		for(int i = 0; i < articleList.size(); i++)
			anArtList.add(new ArticleStat(i));

		for(PrimitiveTerm primTerm: primTerms){
			for(TermStat anTerm : anTermList){
				if(primTerm.getTerm().getId() == anTerm.getId())
				{
					long artNum = primTerm.getPublication().getId();
					anArtList.get((int)artNum - 1).add(anTerm);
				}
			}
		}*/

		/*for(ArticleStat anArt : anArtList){
			anArt.setProbCluster();
			anArt.setCluster();
		}
		Collections.sort(anArtList, compAnArt);

		int countOfClusters = 3;

		for(int i = 0; i < countOfClusters; i++)
			System.out.println( getCountInClusterArt(anArtList,i));

		for(ArticleStat anArt : anArtList)
			System.out.println(anArt.getId()+1 + " - " + anArt.getCluster());*/



		/*//считаем среднюю частоту для каждого кластера
		System.out.println("\nЭто говнокод");
		double[][] middleLnForEveryCluster = new double[countOfClusters][time.getFinish() - time.getStart() + 1];
		for(i = 0; i < countOfClusters; i++)
		{
			int count = getCountInCluster(anTermList, i);
			System.out.println("\nCluster " + i + ": " + count);
			for(TermStat term : anTermList)
			{
				if(term.getCluster() == i)
				{
					for(int k = 0; k < time.getFinish() - time.getStart() + 1; k++)
						middleLnForEveryCluster[i][k] += term.getLnSeq()[k];
				}
			}
			int year = time.getStart();
			for(double freq : middleLnForEveryCluster[i])
			{
				freq = freq / count;
				System.out.println(freq);//("(" + year++ + ";" + freq + ")");
			}
			System.out.println();
		}*/



	}


	private static Comparator<TermStat> compTermStat = new Comparator<TermStat>()
	{
		@Override
		public int compare(TermStat o1, TermStat o2)
		{
			return Double.compare(o1.getWeight(),o2.getWeight());
		}
	};


	private static Comparator<ArticleStat> compAnArt = new Comparator<ArticleStat>()
	{
		@Override
		public int compare(ArticleStat o1, ArticleStat o2)
		{
			return Integer.compare(o1.getCluster(),o2.getCluster());
		}
	};


	public static int getCountInClusterTerm(List<TermStat> list, int i)
	{
		int count = 0;
		for(TermStat term : list)
		{
			if(term.getCluster() == i)
				count++;
		}
		return count;
	}

	public static double getWeightCluster(List<TermStat> list, int i)
	{
		double count = 0;
		for(TermStat term : list)
		{
			if(term.getCluster() == i)
				count+= term.getWeight();
		}
		return count;
	}

	public static int getCountInClusterArt(List<ArticleStat> list, int i)
	{
		int count = 0;
		for(ArticleStat art : list)
		{
			if(art.getCluster() == i)
				count++;
		}
		return count;
	}

}
