import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Мария on 11.05.2016.
 */
public class Map
{
	private List<MapElem> map;

	Map(){ map = new ArrayList<>(); }

	public int getCountOfCluster(){
		int max = -1;
		for(MapElem elem : map){
			if(elem.getClusterNumber() > max)
				max = elem.getClusterNumber();
		}
		return max;
	}

	public int getMapSize(){ return map.size();}

	public MapElem getElem(int i){ return  map.get(i); }

	public void getEvolution(int currYear, int futureYear, Map newMap)
	{
		try
		{
			FileWriter evol = new FileWriter("evolution" + currYear + "_" + futureYear + ".txt", false);

			int futureCountOfCluster = newMap.getCountOfCluster();
			int currCountOfCluster = getCountOfCluster();

			for (int i = 1; i <= currCountOfCluster; i++)
			{
				evol.write("Cluster " + i + " ("  + getClusterWeight(i) + ") year have common weight with\n");
				for (int j = 1; j <= futureCountOfCluster; j++)
				{
					double partInNewCluster = getCommonWeight(i, j, newMap);
					evol.write("\t" + j + ": " + partInNewCluster + "\n");
				}
				evol.write("\n\n");
			}
			evol.flush();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private double getCommonWeight(int currClusterNumber, int futureClusterNumber, Map newMap)
	{
		double weight = 0.0;

		for(int i = 0; i < getMapSize(); i++)
		{
			for(int j = 0; j < newMap.getMapSize(); j++)
			{
				if(getElem(i).getClusterNumber() == currClusterNumber
						&& newMap.getElem(j).getClusterNumber() == futureClusterNumber
						&&
						(getElem(i).getName().equals(newMap.getElem(j).getName()) ||
								getElem(i).getName().equals(newMap.getElem(j).getName().substring(0, newMap.getElem(j).getName().length()-1)) ||
								getElem(i).getName().substring(0, getElem(i).getName().length()-1).equals(newMap.getElem(j).getName()))
				)

					weight += newMap.getElem(j).getWeight();
			}
		}
		return weight;
	}

	private double getClusterWeight(int clusterNumber)
	{
		double weight = 0.0;
		for(int i = 0; i < getMapSize(); i++)
		{
			if(getElem(i).getClusterNumber() == clusterNumber)
				weight += getElem(i).getWeight();
		}
		return weight;
	}

}
