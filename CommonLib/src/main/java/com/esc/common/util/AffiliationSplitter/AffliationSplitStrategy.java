package com.esc.common.util.AffiliationSplitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import com.esc.common.util.AffiliationSplitter.Core.Interfaces.IAffiliationSplitStrategy;

/*import Core.Interfaces.ILocationService;
import Core.Models.City;
import Core.Models.Country;
*/


/**
 * Created by afirsov on 7/9/2016.
 */
public class AffliationSplitStrategy implements IAffiliationSplitStrategy
{

	private ArrayList<String> indicators = new ArrayList<String>();

//	private ILocationService locationService = new LocationService();

	public static void main(String[] args) throws IOException
	{
		AffliationSplitStrategy strat = new AffliationSplitStrategy();
		long startTime = System.currentTimeMillis();

		{strat.Split("Institute of Cytology and Genetics and Institute of Organic Chemistry, SiberianDivision of the Academy of Sciences of the USSR, Novosibirsk, USSR");}
//время затраченное на выполнение кода
		long time = System.currentTimeMillis() - startTime;
		System.out.println("TIME="+time);
	}

	public AffliationSplitStrategy() throws IOException
	{
		indicators.add("university");
		indicators.add("institute");
		indicators.add("centre");
		indicators.add("center");
		indicators.add(";");
	}

	@Override
	public ArrayList<String> Split(String affiliationName) throws IOException
	{

		//todo: ASAP fix
		int count = 0;
	//	if (affiliationName.contains(";"))

		//affiliationName = affiliationName.replaceAll("[^a-zA-Z 0-9]+", "");
		affiliationName = affiliationName.toLowerCase();
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<String> Array = new ArrayList<>();
		ArrayList<String> Combine = new ArrayList<>();

		for (int i = 0; i < indicators.size(); i++)
		{
			if (affiliationName.contains(indicators.get(i)))
			{

				count++;
			}
		}
		String[] arr = affiliationName.split(",|;");

		for (int i=0;i<arr.length;i++)
		{
			if (arr[i].contains("@"))
				arr[i]="";
		}
		if (count > 1)
		{


			String[] Newarr = new String[30];

			Collections.addAll(Array, arr);
			for (int i = 0; i < arr.length; i++)
			{
				arr[i].trim();
				for (int j = 0; j < indicators.size(); j++)
				{
					if (arr[i].contains(indicators.get(j).toLowerCase()) && (arr[i].contains("and")))
					{


						if ((-1 != arr[i].indexOf(indicators.get(j))))
						{

							if ((arr[i].indexOf("and")) < (arr[i].indexOf(indicators.get(j))))
							{
								//System.out.println(arr[i].indexOf("and"));
								Array.remove(i);
								Newarr = ((arr[i].split("and")));
								Array.add(i, Newarr[0]);
								Array.add(i + 1, Newarr[1]);
							}
						}
					}

				}
			}

			result.addAll(Array);

			for (int i = 0; i < Array.size(); i++)
			{
				for (int j = 0; j < indicators.size(); j++)
				{
					if (Array.get(i).contains(indicators.get(j).toLowerCase()))
					{
						Array.set(i, " ");

					}
				}
			}

			if (((Array.lastIndexOf(" ")) - Array.indexOf(" ")) == 1)
			{
				int number = Array.indexOf(" ");
				Array.remove(" ");
				Array.remove(" ");
				result.addAll(number + 1, Array);

			}
			if (Array.lastIndexOf(" ") + 1 == Array.size())
			{
				int number = Array.lastIndexOf(" ");
				Array.remove(" ");
				Array.remove(" ");
				result.addAll(number + 1, Array);

			}
			//Склеить названия
			Array.clear();
			Array.addAll(result);

			int index = 1;
			for(int j=0;j<=count+10;j++)
			Combine.add("");



			boolean foundIndicator;
	next:		for (String res : result)
			{ foundIndicator = false;
				for (String ind : indicators)
				{
					contains:
					if (res.contains(ind))
					{
						foundIndicator = true;

					}

				}

				if (foundIndicator)
				{
					index++;
					Combine.set(index,res);

				}
				else
				{
					//System.out.println("INDEX!!!!!!!!! "+index);
					Combine.set(index, Combine.get(index) + res);
				}

			}
			Combine.trimToSize();
		//	System.out.println(Combine.size());
			result.clear();
			result.addAll(Combine);
		}
		else
		{
			for (int i=1;i<=arr.length-1;i++ )
			{arr[0]=arr[0]+" " +arr[i];

			}
			result.add(arr[0]);
		}


		//System.out.println(result+" size="+result.size());

		System.out.println(result);
		return result;

	}

	private ArrayList<Integer> LocateCollisions(Map<Integer, String> first, Map<Integer, String> second)
	{
		ArrayList<Integer> result = new ArrayList<Integer>();

		Set<Integer> keysFirst = first.keySet();
		for (Integer key : keysFirst)
		{
			if (second.containsKey(key))
			{
				result.add(key);
			}
		}

		return result;
	}

}



