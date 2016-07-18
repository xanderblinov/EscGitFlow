/**
 * Created by Мария on 16.04.2016.
 */

public class Frequency
{
	private int pubYear;
	private int yearCount;

	Frequency(int year, int count)
	{
		pubYear = year;
		yearCount = count;
	}

	int getCount(){ return yearCount; }

	int getYear(){ return pubYear; }

	@Override
	public String toString(){
		return "("+ pubYear + "; " + yearCount + ")";
	}
}
