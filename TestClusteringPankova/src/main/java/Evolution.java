import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

/**
 * Created by Мария on 11.05.2016.
 */

//тут мы считаем, сколько суммарного веса терминов от текущего кластера перешло в каждый из кластеров следующего года
public class Evolution
{
	public static void main(String [] args)
	{
		TimeBorder time = new TimeBorder();
		try
		{
			for (int i = time.getStart(); i < time.getFinish(); i++)
			{
				BufferedReader currMap = new BufferedReader(new InputStreamReader(new FileInputStream("map" + time.getStart() + "_" + i + ".txt")));
				BufferedReader futureMap = new BufferedReader(new InputStreamReader(new FileInputStream("map" + time.getStart() + "_" + (i + 1) + ".txt")));

				Map currPeriod = new Map();
				Map futurePeriod = new Map();

				String line;

				createMap(currMap, currPeriod);
				createMap(futureMap,futurePeriod);

				currPeriod.getEvolution(i, i+1, futurePeriod);

				currMap.close();
				futureMap.close();

			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void createMap(BufferedReader file, Map map)
	{
		try
		{
			String line = file.readLine();

			if (!line.equals("id\tcluster\tweight"))
			{
				System.out.println("It is not a map file");
				return;
			}

			while ((line = file.readLine()) != null)
			{
				MapElem elem = new MapElem(line.split("\t")[0], Integer.parseInt(line.split("\t")[1]), Double.parseDouble(line.split("\t")[2]));
				map.addEkem(elem);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
