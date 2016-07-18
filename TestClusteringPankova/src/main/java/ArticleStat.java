import java.util.ArrayList;
import java.util.List;


/**
 * Created by Мария on 28.04.2016.
 */


//это короче класс для всякого статистического анализа научных публикаций
public class ArticleStat
{
	private long Id;
	private double[] probCluster = new double[3]; //число кластеров, к сожалению, задаем ручками
	private List<TermStat> articleTerms;
	private int Cluster = -1;

	ArticleStat(int id)
	{
		Id = id;
		articleTerms = new ArrayList<>();
	}

	public long getId(){ return Id; }

	public int getCluster(){ return Cluster; }

	public void add(TermStat term){ articleTerms.add(term);}

	//для каждой публикации получаем массив вероятностей ее отнесения к тому или иному кластеру
	public void setProbCluster()
	{
		if(articleTerms.size() == 0)
			return;
		for(int i = 0; i < articleTerms.size(); i++)
		{
			int num = articleTerms.get(i).getCluster();
			if(probCluster.length < num)
			{
				throw new IndexOutOfBoundsException();
			}
			probCluster[num]++;
		}
		for(double count : probCluster)
			count = count / (double)articleTerms.size();

	}

	public void setCluster()
	{
		if(articleTerms.size() == 0)
			return;
		double max = 0.0;
		for(int i = 0; i < probCluster.length; i++){
			if (probCluster[i] > max){
				max = probCluster[i];
				Cluster = i;
			}
		}
	}

}
