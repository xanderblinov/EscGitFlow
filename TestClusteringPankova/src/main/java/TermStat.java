import java.util.ArrayList;
import java.util.List;

/**
 * Created by Мария on 16.04.2016.
 */

public class TermStat
{
	private int mId;
	private String mVal;
	private int mCluster;
	private double mWeight;
	private List<Frequency> timeSeq;
	private double[] Probabilities;
	private double[] lnSeq;

	TermStat(int id, String val,int cluster, double weight)
	{
		mId = id;
		mVal = val;
		mCluster = cluster;
		mWeight = weight;
		timeSeq = new ArrayList<>();
	}

	public int getId(){ return mId; }

	public int getCluster(){ return mCluster; }

	public double getWeight(){ return mWeight; }

	public String toString()
	{
		return mId + " " + mVal + " " + mCluster + "\n"
				+ "TimeSeq: " + timeSeqToString() + "\n"
				+ "Prob: " + probToString() + "\n"
				+ "LnCount: " + lnSeqToString()
				+ "SumCount: " + Integer.toString(getSumCount()) + "\n"
				+ "Weight: " + getWeight() + "\n\n";
	}

	public String getVal(){ return mVal; }

	public void addUsing(Frequency freq)
	{
		timeSeq.add(freq);
	}

	public int getYearCount(int year)
	{
		for(Frequency freq: timeSeq)
		{
			if(year == freq.getYear())
				return freq.getCount();
		}
		return 0;
	}

	public int getSumCount()
	{
		int count = 0;
		for(Frequency freq : timeSeq)
			count = count + freq.getCount();
		return count;
	}

	public void setProb(int startYear, int finishYear, int[] countArtInYear)
	{
		if(finishYear - startYear + 1 != countArtInYear.length)
		{
			System.out.print("Temporal boundaries are incorrect.\n");
			return;
		}

		Probabilities = new double[finishYear - startYear + 1];
		for(int i = 0; i < Probabilities.length; i++)
		{
			if(countArtInYear[i] == 0)
			{
				Probabilities[i] = 0;
				continue;
			}
			Probabilities[i] = (double)getYearCount(startYear + i) / (double)countArtInYear[i];
		}
	}

	public double[] getProb(){ return Probabilities; }

	public void  setLnSeq(int startYear)
	{
		if(getProb().length == 0)
		{
			System.out.println("Take care of frequncy\n");
			return;
		}
		lnSeq = new double[getProb().length];

		for(int i = 0; i < getProb().length; i++)
		{
			if((double)getYearCount(startYear + i) != 0)
				lnSeq[i] = Math.log(getProb()[i]);
		}
	}

	public double[] getLnSeq(){ return lnSeq; }

	public String lnSeqToString()
	{
		TimeBorder time = new TimeBorder();
		int i = time.getStart();
		String str = "";
		for(Double ln : lnSeq)
		{
			str = str.concat("(" + i + ";" + Double.toString(ln) + ")");
			i++;
		}
		return str + "\n";
	}

	public String timeSeqToString()
	{
		String str = "";
		for(Frequency freq : timeSeq)
			str = str.concat("(" + freq.getYear() + ";" + freq.getCount() + ")");
		return str;
	}

	public String probToString()
	{
		TimeBorder time = new TimeBorder();
		int i = time.getStart();
		String str = "";
		for(Double prob : Probabilities)
		{
			str = str.concat("(" + i + ";" + Double.toString(prob) + ")");
			i++;
		}
		return str;
	}
}
